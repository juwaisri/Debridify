package com.debridify.app.data.model

import com.google.gson.annotations.SerializedName

// Real-Debrid User Model
data class RealDebridUser(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("points") val points: Int,
    @SerializedName("locale") val locale: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("type") val type: String,
    @SerializedName("premium") val premium: Long,
    @SerializedName("expiration") val expiration: String?
)

// TorBox User Model
data class TorBoxUser(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("plan") val plan: Int,
    @SerializedName("total_downloaded") val totalDownloaded: Long,
    @SerializedName("customer") val customer: String?,
    @SerializedName("server") val server: Int?,
    @SerializedName("isSubscribed") val isSubscribed: Boolean,
    @SerializedName("premium_expires_at") val premiumExpiresAt: String?,
    @SerializedName("cooldown_until") val cooldownUntil: String?
)

// AllDebrid User Model
data class AllDebridUser(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("isPremium") val isPremium: Boolean,
    @SerializedName("isSubscribed") val isSubscribed: Boolean,
    @SerializedName("isTrial") val isTrial: Boolean,
    @SerializedName("premiumUntil") val premiumUntil: Long?,
    @SerializedName("lang") val lang: String,
    @SerializedName("preferedDomain") val preferredDomain: String?,
    @SerializedName("user_id") val userId: Int?
)

// Unified User Model
data class UnifiedUser(
    val id: String,
    val username: String,
    val email: String,
    val isPremium: Boolean,
    val expirationDate: String?,
    val provider: DebridProvider,
    val additionalInfo: Map<String, String> = emptyMap()
)

// Converter functions
fun RealDebridUser.toUnifiedUser() = UnifiedUser(
    id = id.toString(),
    username = username,
    email = email,
    isPremium = premium > 0,
    expirationDate = expiration,
    provider = DebridProvider.REAL_DEBRID,
    additionalInfo = mapOf(
        "points" to points.toString(),
        "type" to type
    )
)

fun TorBoxUser.toUnifiedUser() = UnifiedUser(
    id = id.toString(),
    username = email.substringBefore("@"),
    email = email,
    isPremium = isSubscribed,
    expirationDate = premiumExpiresAt,
    provider = DebridProvider.TORBOX,
    additionalInfo = mapOf(
        "plan" to plan.toString(),
        "totalDownloaded" to totalDownloaded.toString()
    )
)

fun AllDebridUser.toUnifiedUser() = UnifiedUser(
    id = userId?.toString() ?: "unknown",
    username = username,
    email = email,
    isPremium = isPremium,
    expirationDate = premiumUntil?.let { java.text.SimpleDateFormat("yyyy-MM-dd").format(java.util.Date(it * 1000)) },
    provider = DebridProvider.ALLDEBRID,
    additionalInfo = mapOf(
        "isSubscribed" to isSubscribed.toString(),
        "isTrial" to isTrial.toString()
    )
)
