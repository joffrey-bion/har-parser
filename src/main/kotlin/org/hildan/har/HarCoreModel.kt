package org.hildan.har

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class Har(
    val log: HarLog,
)

@Serializable
data class HarLog(
    val version: String,
    val creator: Creator,
    val pages: List<HarPage>,
    val entries: List<HarEntry>,
) {
    @Serializable
    data class Creator(
        val name: String,
        val version: String,
    )
}

@Serializable
data class HarPage(
    val startedDateTime: String,
    val id: String,
    val title: String,
    val pageTimings: Timings,
) {
    @Serializable
    data class Timings(
        val onContentLoad: Double,
        val onLoad: Double,
    )
}

@Serializable
data class HarEntry(
    @SerialName("_fromCache")
    val fromCache: String? = null,
    @SerialName("_initiator")
    val initiator: Initiator,
    @SerialName("_priority")
    val priority: String?,
    @SerialName("_resourceType")
    val resourceType: String,
    val cache: JsonObject,
    val connection: String? = null,
    val pageref: String,
    val request: HarRequest,
    val response: HarResponse,
    val serverIPAddress: String,
    val startedDateTime: String,
    val time: Double,
    val timings: Timings,
    @SerialName("_webSocketMessages")
    val webSocketMessages: List<HarWebSocketMessage>? = null,
) {
    @Serializable
    data class Initiator(
        val type: String,
        val stack: Stack? = null,
        val url: String? = null,
        val lineNumber: Int? = null,
        val requestId: String? = null,
    ) {
        @Serializable
        data class Stack(
            val callFrames: List<Frame>,
            val description: String? = null,
            val parent: Stack? = null,
        ) {
            @Serializable
            data class Frame(
                val functionName: String,
                val scriptId: String,
                val url: String,
                val lineNumber: Int,
                val columnNumber: Int,
            )
        }
    }

    @Serializable
    data class Timings(
        val blocked: Double,
        val dns: Double,
        val ssl: Double,
        val connect: Double,
        val send: Double,
        val wait: Double,
        val receive: Double,
        @SerialName("_blocked_queueing")
        val blockedQueueing: Double,
    )
}

@Serializable
data class HarRequest(
    val method: String,
    val url: String,
    val httpVersion: String,
    val headers: List<Header>,
    val queryString: List<QueryParam>,
    val cookies: List<Cookie>,
    val headersSize: Long,
    val bodySize: Long,
    val postData: PostData? = null,
) {
    @Serializable
    data class QueryParam(val name: String, val value: String)

    @Serializable
    data class PostData(
        val mimeType: String,
        val text: String,
    )
}

@Serializable
data class HarResponse(
    val status: Long,
    val statusText: String,
    val httpVersion: String,
    val headers: List<Header>,
    val cookies: List<Cookie>,
    val content: Content,
    val redirectURL: String,
    val headersSize: Long,
    val bodySize: Long,
    @SerialName("_transferSize")
    val transferSize: Long,
    @SerialName("_error")
    val error: String? = null,
) {
    @Serializable
    data class Content(
        val size: Long,
        val mimeType: String,
        val compression: Int? = null,
        val text: String? = null,
        val encoding: String? = null,
    )
}

@Serializable
data class Header(val name: String, val value: String)

@Serializable
data class Cookie(
    val name: String,
    val value: String,
    val path: String,
    val domain: String,
    val expires: String,
    val httpOnly: Boolean,
    val secure: Boolean,
    val sameSite: String? = null,
)
