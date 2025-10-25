package com.debridify.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debridify.app.data.model.DebridProvider
import com.debridify.app.data.model.UnifiedTorrent
import com.debridify.app.data.model.UnifiedUser
import com.debridify.app.data.repository.DebridRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DebridRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private val _currentProvider = MutableStateFlow<DebridProvider?>(null)
    val currentProvider: StateFlow<DebridProvider?> = _currentProvider.asStateFlow()
    
    private val _torrents = MutableStateFlow<List<UnifiedTorrent>>(emptyList())
    val torrents: StateFlow<List<UnifiedTorrent>> = _torrents.asStateFlow()
    
    private val _user = MutableStateFlow<UnifiedUser?>(null)
    val user: StateFlow<UnifiedUser?> = _user.asStateFlow()
    
    init {
        loadCurrentProvider()
    }
    
    private fun loadCurrentProvider() {
        viewModelScope.launch {
            val provider = repository.getCurrentProvider()
            _currentProvider.value = provider
            
            if (provider != null) {
                val apiKey = repository.getApiKey(provider)
                if (apiKey != null) {
                    loadUser(provider)
                    loadTorrents(provider)
                } else {
                    _uiState.value = UiState.NeedsLogin
                }
            } else {
                _uiState.value = UiState.NeedsProviderSelection
            }
        }
    }
    
    fun setProvider(provider: DebridProvider) {
        viewModelScope.launch {
            repository.setCurrentProvider(provider)
            _currentProvider.value = provider
            
            val apiKey = repository.getApiKey(provider)
            if (apiKey != null) {
                loadUser(provider)
                loadTorrents(provider)
            } else {
                _uiState.value = UiState.NeedsLogin
            }
        }
    }
    
    fun setApiKey(provider: DebridProvider, apiKey: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.setApiKey(provider, apiKey)
            repository.setCurrentProvider(provider)
            _currentProvider.value = provider
            
            loadUser(provider)
            loadTorrents(provider)
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            _currentProvider.value?.let { provider ->
                repository.clearApiKey(provider)
                _user.value = null
                _torrents.value = emptyList()
                _uiState.value = UiState.NeedsLogin
            }
        }
    }
    
    private fun loadUser(provider: DebridProvider) {
        viewModelScope.launch {
            val result = repository.getUser(provider)
            result.onSuccess { user ->
                _user.value = user
                _uiState.value = UiState.Success
            }.onFailure { error ->
                _uiState.value = UiState.Error(error.message ?: "Unknown error")
            }
        }
    }
    
    fun loadTorrents(provider: DebridProvider) {
        viewModelScope.launch {
            val result = repository.getTorrents(provider)
            result.onSuccess { torrents ->
                _torrents.value = torrents
            }.onFailure { error ->
                _uiState.value = UiState.Error(error.message ?: "Failed to load torrents")
            }
        }
    }
    
    fun refreshTorrents() {
        _currentProvider.value?.let { provider ->
            loadTorrents(provider)
        }
    }
    
    fun addMagnet(magnetLink: String) {
        viewModelScope.launch {
            _currentProvider.value?.let { provider ->
                _uiState.value = UiState.Loading
                val result = repository.addMagnet(provider, magnetLink)
                result.onSuccess {
                    loadTorrents(provider)
                    _uiState.value = UiState.Success
                }.onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Failed to add magnet")
                }
            }
        }
    }
    
    fun deleteTorrent(torrent: UnifiedTorrent) {
        viewModelScope.launch {
            _currentProvider.value?.let { provider ->
                val result = repository.deleteTorrent(provider, torrent.id)
                result.onSuccess {
                    loadTorrents(provider)
                }.onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Failed to delete torrent")
                }
            }
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    object NeedsProviderSelection : UiState()
    object NeedsLogin : UiState()
    data class Error(val message: String) : UiState()
}
