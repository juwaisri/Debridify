package com.debridify.app.data.api

import com.debridify.app.data.model.TorBoxTorrent
import com.debridify.app.data.model.TorBoxUser
import retrofit2.Response
import retrofit2.http.*

interface TorBoxApi {
    
    @GET("user/me")
    suspend fun getUser(): Response<TorBoxUserResponse>
    
    @GET("torrents/mylist")
    suspend fun getTorrents(
        @Query("bypass_cache") bypassCache: Boolean = false,
        @Query("id") id: Int? = null
    ): Response<TorBoxTorrentsResponse>
    
    @POST("torrents/createtorrent")
    @FormUrlEncoded
    suspend fun addMagnet(
        @Field("magnet") magnet: String,
        @Field("seed") seed: Int = 1,
        @Field("allow_zip") allowZip: Boolean = false,
        @Field("name") name: String? = null
    ): Response<TorBoxAddResponse>
    
    @POST("torrents/controltorrent")
    @FormUrlEncoded
    suspend fun controlTorrent(
        @Field("torrent_id") torrentId: Int,
        @Field("operation") operation: String // "pause", "resume", "delete"
    ): Response<TorBoxControlResponse>
    
    @GET("torrents/requestdl")
    suspend fun requestDownload(
        @Query("token") token: String,
        @Query("torrent_id") torrentId: Int,
        @Query("file_id") fileId: Int? = null,
        @Query("zip_link") zipLink: Boolean = false
    ): Response<TorBoxDownloadResponse>
    
    @GET("torrents/checkcached")
    suspend fun checkCached(
        @Query("hash") hash: String,
        @Query("format") format: String = "object",
        @Query("list_files") listFiles: Boolean = false
    ): Response<TorBoxCachedResponse>
}

data class TorBoxUserResponse(
    val success: Boolean,
    val detail: String?,
    val data: TorBoxUser?
)

data class TorBoxTorrentsResponse(
    val success: Boolean,
    val detail: String?,
    val data: List<TorBoxTorrent>?
)

data class TorBoxAddResponse(
    val success: Boolean,
    val detail: String?,
    val data: TorBoxAddData?
)

data class TorBoxAddData(
    val torrent_id: Int,
    val name: String,
    val hash: String
)

data class TorBoxControlResponse(
    val success: Boolean,
    val detail: String?
)

data class TorBoxDownloadResponse(
    val success: Boolean,
    val detail: String?,
    val data: String? // Download URL
)

data class TorBoxCachedResponse(
    val success: Boolean,
    val detail: String?,
    val data: Map<String, TorBoxCachedItem>?
)

data class TorBoxCachedItem(
    val name: String?,
    val size: Long?,
    val hash: String,
    val files: List<TorBoxCachedFile>?
)

data class TorBoxCachedFile(
    val name: String,
    val size: Long
)
