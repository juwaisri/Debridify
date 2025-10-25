package com.debridify.app.data.model

import com.google.gson.annotations.SerializedName

// File within a torrent
data class TorrentFile(
    val id: String,
    val path: String,
    val bytes: Long,
    val selected: Boolean = true
)

// Real-Debrid File Model
data class RealDebridFile(
    @SerializedName("id") val id: Int,
    @SerializedName("path") val path: String,
    @SerializedName("bytes") val bytes: Long,
    @SerializedName("selected") val selected: Int
)

// TorBox File Model  
data class TorBoxFile(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("size") val size: Long
)

// AllDebrid File Model
data class AllDebridFile(
    @SerializedName("n") val name: String,
    @SerializedName("s") val size: Long,
    @SerializedName("e") val selected: Boolean = true
)

// Unified Torrent Details with Files
data class TorrentDetails(
    val torrent: UnifiedTorrent,
    val files: List<TorrentFile>,
    val canSelectFiles: Boolean
)

// Converter functions
fun RealDebridFile.toTorrentFile() = TorrentFile(
    id = id.toString(),
    path = path,
    bytes = bytes,
    selected = selected == 1
)

fun TorBoxFile.toTorrentFile() = TorrentFile(
    id = id.toString(),
    path = name,
    bytes = size,
    selected = true
)

fun AllDebridFile.toTorrentFile() = TorrentFile(
    id = name.hashCode().toString(),
    path = name,
    bytes = size,
    selected = selected
)
