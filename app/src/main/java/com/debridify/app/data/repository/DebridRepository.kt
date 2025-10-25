package com.debridify.app.data.repository

import com.debridify.app.data.api.*
import com.debridify.app.data.model.*
import com.debridify.app.data.preferences.PreferencesManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebridRepository @Inject constructor(
    private val realDebridApi: RealDebridApi,
    private val torBoxApi: TorBoxApi,
    private val allDebridApi: AllDebridApi,
    private val preferencesManager: PreferencesManager
) {
    
    suspend fun getCurrentProvider(): DebridProvider? {
        return preferencesManager.currentProvider.first()
    }
    
    suspend fun setCurrentProvider(provider: DebridProvider) {
        preferencesManager.setCurrentProvider(provider)
    }
    
    suspend fun getApiKey(provider: DebridProvider): String? {
        return preferencesManager.getApiKey(provider).first()
    }
    
    suspend fun setApiKey(provider: DebridProvider, apiKey: String) {
        preferencesManager.setApiKey(provider, apiKey)
    }
    
    suspend fun clearApiKey(provider: DebridProvider) {
        preferencesManager.clearApiKey(provider)
    }
    
    // User operations
    suspend fun getUser(provider: DebridProvider): Result<UnifiedUser> {
        return try {
            when (provider) {
                DebridProvider.REAL_DEBRID -> {
                    val response = realDebridApi.getUser()
                    if (response.isSuccessful && response.body() != null) {
                        Result.success(response.body()!!.toUnifiedUser())
                    } else {
                        Result.failure(Exception("Failed to get user: ${response.code()}"))
                    }
                }
                DebridProvider.TORBOX -> {
                    val response = torBoxApi.getUser()
                    if (response.isSuccessful && response.body()?.data != null) {
                        Result.success(response.body()!!.data!!.toUnifiedUser())
                    } else {
                        Result.failure(Exception("Failed to get user: ${response.body()?.detail ?: response.code()}"))
                    }
                }
                DebridProvider.ALLDEBRID -> {
                    val response = allDebridApi.getUser()
                    if (response.isSuccessful && response.body()?.data?.user != null) {
                        Result.success(response.body()!!.data!!.user.toUnifiedUser())
                    } else {
                        Result.failure(Exception("Failed to get user: ${response.code()}"))
                    }
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Torrent operations
    suspend fun getTorrents(provider: DebridProvider): Result<List<UnifiedTorrent>> {
        return try {
            when (provider) {
                DebridProvider.REAL_DEBRID -> {
                    val response = realDebridApi.getTorrents(limit = 100)
                    if (response.isSuccessful && response.body() != null) {
                        Result.success(response.body()!!.map { it.toUnifiedTorrent() })
                    } else {
                        Result.failure(Exception("Failed to get torrents: ${response.code()}"))
                    }
                }
                DebridProvider.TORBOX -> {
                    val response = torBoxApi.getTorrents()
                    if (response.isSuccessful && response.body()?.data != null) {
                        Result.success(response.body()!!.data!!.map { it.toUnifiedTorrent() })
                    } else {
                        Result.failure(Exception("Failed to get torrents: ${response.body()?.detail ?: response.code()}"))
                    }
                }
                DebridProvider.ALLDEBRID -> {
                    val response = allDebridApi.getMagnets()
                    if (response.isSuccessful && response.body()?.data?.magnets != null) {
                        Result.success(response.body()!!.data!!.magnets.map { it.toUnifiedTorrent() })
                    } else {
                        Result.failure(Exception("Failed to get torrents: ${response.code()}"))
                    }
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addMagnet(provider: DebridProvider, magnetLink: String): Result<String> {
        return try {
            when (provider) {
                DebridProvider.REAL_DEBRID -> {
                    val response = realDebridApi.addMagnet(magnetLink)
                    if (response.isSuccessful && response.body() != null) {
                        Result.success(response.body()!!.id)
                    } else {
                        Result.failure(Exception("Failed to add magnet: ${response.code()}"))
                    }
                }
                DebridProvider.TORBOX -> {
                    val response = torBoxApi.addMagnet(magnetLink)
                    if (response.isSuccessful && response.body()?.data != null) {
                        Result.success(response.body()!!.data!!.torrent_id.toString())
                    } else {
                        Result.failure(Exception("Failed to add magnet: ${response.body()?.detail ?: response.code()}"))
                    }
                }
                DebridProvider.ALLDEBRID -> {
                    val response = allDebridApi.addMagnet(listOf(magnetLink))
                    if (response.isSuccessful && response.body()?.data?.magnets?.isNotEmpty() == true) {
                        Result.success(response.body()!!.data!!.magnets.first().id.toString())
                    } else {
                        Result.failure(Exception("Failed to add magnet: ${response.code()}"))
                    }
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteTorrent(provider: DebridProvider, id: String): Result<Unit> {
        return try {
            when (provider) {
                DebridProvider.REAL_DEBRID -> {
                    val response = realDebridApi.deleteTorrent(id)
                    if (response.isSuccessful) {
                        Result.success(Unit)
                    } else {
                        Result.failure(Exception("Failed to delete torrent: ${response.code()}"))
                    }
                }
                DebridProvider.TORBOX -> {
                    val response = torBoxApi.controlTorrent(id.toInt(), "delete")
                    if (response.isSuccessful && response.body()?.success == true) {
                        Result.success(Unit)
                    } else {
                        Result.failure(Exception("Failed to delete torrent: ${response.body()?.detail ?: response.code()}"))
                    }
                }
                DebridProvider.ALLDEBRID -> {
                    val response = allDebridApi.deleteMagnet(id.toInt())
                    if (response.isSuccessful) {
                        Result.success(Unit)
                    } else {
                        Result.failure(Exception("Failed to delete torrent: ${response.code()}"))
                    }
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTorrentInfo(provider: DebridProvider, id: String): Result<UnifiedTorrent> {
        return try {
            when (provider) {
                DebridProvider.REAL_DEBRID -> {
                    val response = realDebridApi.getTorrentInfo(id)
                    if (response.isSuccessful && response.body() != null) {
                        Result.success(response.body()!!.toUnifiedTorrent())
                    } else {
                        Result.failure(Exception("Failed to get torrent info: ${response.code()}"))
                    }
                }
                DebridProvider.TORBOX -> {
                    val response = torBoxApi.getTorrents(id = id.toInt())
                    if (response.isSuccessful && response.body()?.data?.isNotEmpty() == true) {
                        Result.success(response.body()!!.data!!.first().toUnifiedTorrent())
                    } else {
                        Result.failure(Exception("Failed to get torrent info: ${response.body()?.detail ?: response.code()}"))
                    }
                }
                DebridProvider.ALLDEBRID -> {
                    val response = allDebridApi.getMagnets(id.toInt())
                    if (response.isSuccessful && response.body()?.data?.magnets?.isNotEmpty() == true) {
                        Result.success(response.body()!!.data!!.magnets.first().toUnifiedTorrent())
                    } else {
                        Result.failure(Exception("Failed to get torrent info: ${response.code()}"))
                    }
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
