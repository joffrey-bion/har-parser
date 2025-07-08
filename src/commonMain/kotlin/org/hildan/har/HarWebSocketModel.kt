package org.hildan.har

import kotlinx.io.bytestring.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import kotlin.io.encoding.*
import kotlin.time.*

/**
 * A web socket message record.
 */
@Serializable(with = WebSocketMessageSerializer::class)
sealed class HarWebSocketMessage {
    /** Which way the message was sent on the web socket. */
    abstract val direction: WebSocketDirection

    /** The instant when the message was sent. */
    abstract val time: Instant

    /** The opcode of the websocket frame (1 for a text frame, 2 for a binary frame). */
    abstract val opcode: Int

    @Serializable
    data class Text(
        @SerialName("type")
        override val direction: WebSocketDirection,
        @Serializable(with = InstantAsDoubleSecondsSerializer::class)
        override val time: Instant,
        override val opcode: Int,
        /** The text payload of this message (UTF-8 decoded). */
        val data: String,
    ) : HarWebSocketMessage()

    @Serializable
    data class Binary(
        @SerialName("type")
        override val direction: WebSocketDirection,
        @Serializable(with = InstantAsDoubleSecondsSerializer::class)
        override val time: Instant,
        override val opcode: Int,
        /** The binary payload of this message. */
        @Serializable(with = ByteStringAsBase64StringSerializer::class)
        val data: ByteString,
    ) : HarWebSocketMessage()
}

@Serializable
enum class WebSocketDirection {
    /** The message was sent from this client. */
    @SerialName("send")
    Send,

    /** The message was received from the server. */
    @SerialName("receive")
    Receive,
}

/**
 * Handles web socket message types polymorphism based on the `opcode` JSON property (1 for text, 2 for binary).
 */
private object WebSocketMessageSerializer : JsonContentPolymorphicSerializer<HarWebSocketMessage>(HarWebSocketMessage::class) {

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<HarWebSocketMessage> {
        val opcodeElement = element.jsonObject["opcode"] ?: error("Missing 'opcode' property in web socket message")
        return when(val opcode = opcodeElement.jsonPrimitive.int) {
            1 -> HarWebSocketMessage.Text.serializer()
            2 -> HarWebSocketMessage.Binary.serializer()
            else -> error("Unknown web socket opcode $opcode")
        }
    }
}

/**
 * Serializes [ByteString]s as a fractional number of epoch seconds (a double in JSON).
 */
@OptIn(ExperimentalEncodingApi::class)
private object ByteStringAsBase64StringSerializer : KSerializer<ByteString> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ByteString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ByteString) {
        val base64String = Base64.encode(value)
        encoder.encodeString(base64String)
    }

    override fun deserialize(decoder: Decoder): ByteString {
        val base64String = decoder.decodeString()
        return Base64.decodeToByteString(base64String)
    }
}

private const val NANOS_IN_SECOND = 1_000_000_000

/**
 * Serializes [Instant]s as a fractional number of epoch seconds (a double in JSON).
 */
private object InstantAsDoubleSecondsSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.DOUBLE)

    override fun serialize(encoder: Encoder, value: Instant) {
        val doubleSeconds = value.epochSeconds.toDouble() + value.nanosecondsOfSecond.toDouble() / NANOS_IN_SECOND
        encoder.encodeDouble(doubleSeconds)
    }

    override fun deserialize(decoder: Decoder): Instant {
        val doubleSeconds = decoder.decodeDouble()
        val fullSeconds = doubleSeconds.toLong()
        val nanos = (doubleSeconds - fullSeconds) * NANOS_IN_SECOND
        return Instant.fromEpochSeconds(fullSeconds, nanos.toInt())
    }
}
