package com.debridify.app.data.model

import com.google.gson.annotations.SerializedName

// Real-Debrid Torrent Model
data class RealDebridTorrent(
    @SerializedName("id") val id: String,
    @SerializedName("filename") val filename: String,
    @SerializedName("hash") val hash: String,
    @SerializedName("bytes") val bytes: Long,
    @SerializedName("host") val host: String,
    @SerializedName("split") val split: Int,
    @SerializedName("progress") val progress: Double,
    @SerializedName("status") val status: String,
    @SerializedName("added") val added: String,
    @SerializedName("links") val links: List<String>?,
    @SerializedName("ended") val ended: String?,
    @SerializedName("speed") val speed: Long?,
    @SerializedName("seeders") val seeders: Int?
)

// TorBox Torrent Model
data class TorBoxTorrent(
    @SerializedName("id") val id: Int,
    @SerializedName("hash") val hash: String,
    @SerializedName("name") val name: String,
    @SerializedName("magnet") val magnet: String?,
    @SerializedName("size") val size: Long,
    @SerializedName("progress") val progress: Double,
    @SerializedName("download_speed") val downloadSpeed: Long,
    @SerializedName("upload_speed") val uploadSpeed: Long,
    @SerializedName("download_present") val downloadPresent: Long,
    @SerializedName("upload_present") val uploadPresent: Long,
    @SerializedName("ratio") val ratio: Double,
    @SerializedName("seeds") val seeds: Int,
    @SerializedName("peers") val peers: Int,
    @SerializedName("eta") val eta: Long,
    @SerializedName("download_state") val downloadState: String,
    @SerializedName("torrent_file") val torrentFile: Boolean,
    @SerializedName("expires_at") val expiresAt: String?,
    @SerializedName("download_finished") val downloadFinished: Boolean,
    @SerializedName("active") val active: Boolean,
    @SerializedName("auth_id") val authId: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

// AllDebrid Magnet Model
data class AllDebridMagnet(
    @SerializedName("id") val id: Int,
    @SerializedName("filename") val filename: String,
    @SerializedName("size") val size: Long,
    @SerializedName("hash") val hash: String,
    @SerializedName("ready") val ready: Boolean,
    @SerializedName("status") val status: String,
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("downloaded") val downloaded: Long,
    @SerializedName("uploaded") val uploaded: Long,
    @SerializedName("seeders") val seeders: Int,
    @SerializedName("downloadSpeed") val downloadSpeed: Double,
    @SerializedName("uploadSpeed") val uploadSpeed: Double,
    @SerializedName("uploadDate") val uploadDate: Long,
    @SerializedName("completionDate") val completionDate: Long?,
    @SerializedName("links") val links: List<AllDebridLink>?
)

data class AllDebridLink(
    @SerializedName("link") val link: String,
    @SerializedName("filename") val filename: String,
    @SerializedName("size") val size: Long
)

// Unified Torrent Model
data class UnifiedTorrent(
    val id: String,
    val name: String,
    val hash: String,
    val size: Long,
    val progress: Double,
    val status: String,
    val downloadSpeed: Long,
    val uploadSpeed: Long,
    val seeders: Int,
    val addedDate: String,
    val provider: DebridProvider,
    val links: List<String>? = null,
    val isReady: Boolean = false
)

// Status enum
enum class TorrentStatus {
    DOWNLOADING,
    QUEUED,
    COMPLETED,
    ERROR,
    SEEDING,
    PAUSED,
    UNKNOWN;
    
    companion object {
        fun fromRealDebrid(status: String): TorrentStatus {
            return when (status.lowercase()) {
                "downloading" -> DOWNLOADING
                "queued" -> QUEUED
                "downloaded" -> COMPLETED
                "error" -> ERROR
                "virus", "dead" -> ERROR
                "uploading" -> SEEDING
                else -> UNKNOWN
            }
        }
        
        fun fromTorBox(state: String): TorrentStatus {
            return when (state.lowercase()) {
                "downloading" -> DOWNLOADING
                "completed", "seeding" -> COMPLETED
                "paused" -> PAUSED
                "error" -> ERROR
                "queued" -> QUEUED
                else -> UNKNOWN
            }
        }
        
        fun fromAllDebrid(status: String): TorrentStatus {
            return when (status.lowercase()) {
                "downloading" -> DOWNLOADING
                "ready", "completed" -> COMPLETED
                "error" -> ERROR
                "processing", "queued" -> QUEUED
                else -> UNKNOWN
            }
        }
    }
}

// Converter functions
fun RealDebridTorrent.toUnifiedTorrent() = UnifiedTorrent(
    id = id,
    name = filename,
    hash = hash,
    size = bytes,
    progress = progress,
    status = TorrentStatus.fromRealDebrid(status).name,
    downloadSpeed = speed ?: 0L,
    uploadSpeed = 0L,
    seeders = seeders ?: 0,
    addedDate = added,
    provider = DebridProvider.REAL_DEBRID,
    links = links,
    isReady = status == "downloaded"
)

fun TorBoxTorrent.toUnifiedTorrent() = UnifiedTorrent(
    id = id.toString(),
    name = name,
    hash = hash,
    size = size,
    progress = progress,
    status = TorrentStatus.fromTorBox(downloadState).name,
    downloadSpeed = downloadSpeed,
    uploadSpeed = uploadSpeed,
    seeders = seeds,
    addedDate = createdAt,
    provider = DebridProvider.TORBOX,
    isReady = downloadFinished
)

fun AllDebridMagnet.toUnifiedTorrent() = UnifiedTorrent(
    id = id.toString(),
    name = filename,
    hash = hash,
    size = size,
    progress = if (ready) 100.0 else (downloaded.toDouble() / size.toDouble() * 100),
    status = TorrentStatus.fromAllDebrid(status).name,
    downloadSpeed = downloadSpeed.toLong(),
    uploadSpeed = uploadSpeed.toLong(),
    seeders = seeders,
    addedDate = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date(uploadDate * 1000)),
    provider = DebridProvider.ALLDEBRID,
    links = links?.map { it.link },
    isReady = ready
)
