package com.debridify.app.data.model

import com.google.gson.annotations.SerializedName

// Download History Entry
data class DownloadHistoryEntry(
    val id: String,
    val name: String,
    val size: Long,
    val downloadedDate: String,
    val provider: DebridProvider,
    val magnetLink: String? = null,
    val hash: String
)

// Link Unrestrict Request/Response
data class UnrestrictRequest(
    val link: String,
    val provider: DebridProvider
)

data class UnrestrictedLink(
    val originalLink: String,
    val unrestrictedLink: String,
    val filename: String,
    val filesize: Long,
    val provider: DebridProvider,
    val host: String
)

// Real-Debrid Unrestrict Response
data class RealDebridUnrestrictResponse(
    @SerializedName("id") val id: String,
    @SerializedName("filename") val filename: String,
    @SerializedName("mimeType") val mimeType: String?,
    @SerializedName("filesize") val filesize: Long,
    @SerializedName("link") val link: String,
    @SerializedName("host") val host: String,
    @SerializedName("chunks") val chunks: Int,
    @SerializedName("crc") val crc: Int,
    @SerializedName("download") val download: String,
    @SerializedName("streamable") val streamable: Int
)

// TorBox Unrestrict Response
data class TorBoxUnrestrictResponse(
    @SerializedName("data") val data: TorBoxUnrestrictData
)

data class TorBoxUnrestrictData(
    @SerializedName("name") val name: String,
    @SerializedName("size") val size: Long,
    @SerializedName("url") val url: String
)

// AllDebrid Unrestrict Response  
data class AllDebridUnrestrictResponse(
    @SerializedName("data") val data: AllDebridUnrestrictData
)

data class AllDebridUnrestrictData(
    @SerializedName("link") val link: String,
    @SerializedName("filename") val filename: String,
    @SerializedName("filesize") val filesize: Long,
    @SerializedName("host") val host: String
)

// Converter functions
fun RealDebridUnrestrictResponse.toUnrestrictedLink(originalLink: String) = UnrestrictedLink(
    originalLink = originalLink,
    unrestrictedLink = download,
    filename = filename,
    filesize = filesize,
    provider = DebridProvider.REAL_DEBRID,
    host = host
)

fun TorBoxUnrestrictResponse.toUnrestrictedLink(originalLink: String) = UnrestrictedLink(
    originalLink = originalLink,
    unrestrictedLink = data.url,
    filename = data.name,
    filesize = data.size,
    provider = DebridProvider.TORBOX,
    host = "TorBox"
)

fun AllDebridUnrestrictResponse.toUnrestrictedLink(originalLink: String) = UnrestrictedLink(
    originalLink = originalLink,
    unrestrictedLink = data.link,
    filename = data.filename,
    filesize = data.filesize,
    provider = DebridProvider.ALLDEBRID,
    host = data.host
)
