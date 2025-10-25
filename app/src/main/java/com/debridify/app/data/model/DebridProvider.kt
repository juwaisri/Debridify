package com.debridify.app.data.model

enum class DebridProvider {
    REAL_DEBRID,
    TORBOX,
    ALLDEBRID;
    
    val displayName: String
        get() = when (this) {
            REAL_DEBRID -> "Real-Debrid"
            TORBOX -> "TorBox"
            ALLDEBRID -> "AllDebrid"
        }
    
    val description: String
        get() = when (this) {
            REAL_DEBRID -> "Fast and reliable premium downloader"
            TORBOX -> "Unlimited bandwidth and storage"
            ALLDEBRID -> "Multi-hoster premium service"
        }

    val baseUrl: String
        get() = when (this) {
            REAL_DEBRID -> "https://api.real-debrid.com/rest/1.0/"
            TORBOX -> "https://api.torbox.app/v1/api/"
            ALLDEBRID -> "https://api.alldebrid.com/v4/"
        }
}
