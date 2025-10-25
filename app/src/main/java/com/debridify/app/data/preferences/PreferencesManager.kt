package com.debridify.app.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.debridify.app.data.model.DebridProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "debridify_prefs")

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore
    
    companion object {
        private val CURRENT_PROVIDER = stringPreferencesKey("current_provider")
        private val REAL_DEBRID_API_KEY = stringPreferencesKey("real_debrid_api_key")
        private val TORBOX_API_KEY = stringPreferencesKey("torbox_api_key")
        private val ALLDEBRID_API_KEY = stringPreferencesKey("alldebrid_api_key")
    }
    
    val currentProvider: Flow<DebridProvider?> = dataStore.data.map { prefs ->
        prefs[CURRENT_PROVIDER]?.let { DebridProvider.valueOf(it) }
    }
    
    suspend fun setCurrentProvider(provider: DebridProvider) {
        dataStore.edit { prefs ->
            prefs[CURRENT_PROVIDER] = provider.name
        }
    }
    
    fun getApiKey(provider: DebridProvider): Flow<String?> = dataStore.data.map { prefs ->
        when (provider) {
            DebridProvider.REAL_DEBRID -> prefs[REAL_DEBRID_API_KEY]
            DebridProvider.TORBOX -> prefs[TORBOX_API_KEY]
            DebridProvider.ALLDEBRID -> prefs[ALLDEBRID_API_KEY]
        }
    }
    
    suspend fun setApiKey(provider: DebridProvider, apiKey: String) {
        dataStore.edit { prefs ->
            when (provider) {
                DebridProvider.REAL_DEBRID -> prefs[REAL_DEBRID_API_KEY] = apiKey
                DebridProvider.TORBOX -> prefs[TORBOX_API_KEY] = apiKey
                DebridProvider.ALLDEBRID -> prefs[ALLDEBRID_API_KEY] = apiKey
            }
        }
    }
    
    suspend fun clearApiKey(provider: DebridProvider) {
        dataStore.edit { prefs ->
            when (provider) {
                DebridProvider.REAL_DEBRID -> prefs.remove(REAL_DEBRID_API_KEY)
                DebridProvider.TORBOX -> prefs.remove(TORBOX_API_KEY)
                DebridProvider.ALLDEBRID -> prefs.remove(ALLDEBRID_API_KEY)
            }
        }
    }
    
    suspend fun clearAll() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
