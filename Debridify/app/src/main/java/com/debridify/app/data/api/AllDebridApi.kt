package com.debridify.app.data.api

import com.debridify.app.data.model.AllDebridMagnet
import com.debridify.app.data.model.AllDebridUser
import retrofit2.Response
import retrofit2.http.*

interface AllDebridApi {
    
    @GET("user")
    suspend fun getUser(): Response<AllDebridUserResponse>
    
    @GET("magnet/status")
    suspend fun getMagnets(
        @Query("id") id: Int? = null
    ): Response<AllDebridMagnetsResponse>
    
    @POST("magnet/upload")
    @FormUrlEncoded
    suspend fun addMagnet(
        @Field("magnets[]") magnets: List<String>
    ): Response<AllDebridUploadResponse>
    
    @POST("magnet/delete")
    @FormUrlEncoded
    suspend fun deleteMagnet(
        @Field("id") id: Int
    ): Response<AllDebridDeleteResponse>
    
    @POST("magnet/restart")
    @FormUrlEncoded
    suspend fun restartMagnet(
        @Field("id") id: Int
    ): Response<AllDebridRestartResponse>
    
    @GET("link/unlock")
    suspend fun unlockLink(
        @Query("link") link: String
    ): Response<AllDebridUnlockResponse>
    
    @GET("user/links")
    suspend fun getLinks(): Response<AllDebridLinksResponse>
    
    @POST("user/links/delete")
    @FormUrlEncoded
    suspend fun deleteLink(
        @Field("link") link: String
    ): Response<AllDebridDeleteLinkResponse>
    
    @GET("hosts")
    suspend fun getHosts(): Response<AllDebridHostsResponse>
}

data class AllDebridUserResponse(
    val status: String,
    val data: AllDebridUserData?
)

data class AllDebridUserData(
    val user: AllDebridUser
)

data class AllDebridMagnetsResponse(
    val status: String,
    val data: AllDebridMagnetsData?
)

data class AllDebridMagnetsData(
    val magnets: List<AllDebridMagnet>
)

data class AllDebridUploadResponse(
    val status: String,
    val data: AllDebridUploadData?
)

data class AllDebridUploadData(
    val magnets: List<AllDebridUploadMagnet>
)

data class AllDebridUploadMagnet(
    val magnet: Int,
    val hash: String,
    val name: String,
    val size: Long,
    val ready: Boolean,
    val id: Int
)

data class AllDebridDeleteResponse(
    val status: String,
    val message: String?
)

data class AllDebridRestartResponse(
    val status: String,
    val message: String?
)

data class AllDebridUnlockResponse(
    val status: String,
    val data: AllDebridUnlockData?
)

data class AllDebridUnlockData(
    val link: String,
    val host: String,
    val filename: String,
    val streaming: List<AllDebridStreamingLink>?,
    val paws: Boolean,
    val filesize: Long,
    val id: String
)

data class AllDebridStreamingLink(
    val id: String,
    val ext: String,
    val quality: String,
    val filesize: Long,
    val proto: String,
    val name: String,
    val tb: Int
)

data class AllDebridLinksResponse(
    val status: String,
    val data: AllDebridLinksData?
)

data class AllDebridLinksData(
    val links: List<AllDebridSavedLink>
)

data class AllDebridSavedLink(
    val link: String,
    val filename: String,
    val host: String,
    val date: Long,
    val size: Long
)

data class AllDebridDeleteLinkResponse(
    val status: String,
    val message: String?
)

data class AllDebridHostsResponse(
    val status: String,
    val data: AllDebridHostsData?
)

data class AllDebridHostsData(
    val hosts: Map<String, AllDebridHost>
)

data class AllDebridHost(
    val name: String,
    val domains: List<String>,
    val status: Int
)
