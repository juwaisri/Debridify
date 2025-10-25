package com.debridify.app.data.api

import com.debridify.app.data.model.RealDebridTorrent
import com.debridify.app.data.model.RealDebridUser
import retrofit2.Response
import retrofit2.http.*

interface RealDebridApi {
    
    @GET("user")
    suspend fun getUser(): Response<RealDebridUser>
    
    @GET("torrents")
    suspend fun getTorrents(
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("filter") filter: String? = null
    ): Response<List<RealDebridTorrent>>
    
    @GET("torrents/info/{id}")
    suspend fun getTorrentInfo(@Path("id") id: String): Response<RealDebridTorrent>
    
    @POST("torrents/addMagnet")
    @FormUrlEncoded
    suspend fun addMagnet(
        @Field("magnet") magnet: String,
        @Field("host") host: String? = null
    ): Response<AddMagnetResponse>
    
    @POST("torrents/selectFiles/{id}")
    @FormUrlEncoded
    suspend fun selectFiles(
        @Path("id") id: String,
        @Field("files") files: String
    ): Response<Unit>
    
    @DELETE("torrents/delete/{id}")
    suspend fun deleteTorrent(@Path("id") id: String): Response<Unit>
    
    @GET("unrestrict/link")
    suspend fun unrestrictLink(@Query("link") link: String): Response<UnrestrictLinkResponse>
    
    @GET("downloads")
    suspend fun getDownloads(
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): Response<List<Download>>
    
    @DELETE("downloads/delete/{id}")
    suspend fun deleteDownload(@Path("id") id: String): Response<Unit>
}

data class AddMagnetResponse(
    val id: String,
    val uri: String
)

data class UnrestrictLinkResponse(
    val id: String,
    val filename: String,
    val mimeType: String,
    val filesize: Long,
    val link: String,
    val host: String,
    val chunks: Int,
    val download: String
)

data class Download(
    val id: String,
    val filename: String,
    val mimeType: String,
    val filesize: Long,
    val link: String,
    val host: String,
    val chunks: Int,
    val download: String,
    val generated: String
)
