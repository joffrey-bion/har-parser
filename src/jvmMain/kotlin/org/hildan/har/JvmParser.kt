package org.hildan.har

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.nio.file.Path
import kotlin.io.path.*

/**
 * Reads this HAR file as a [Har] object, using the provided [json].
 *
 * A custom [Json] can be given, such as `Json { ignoreUnknownProperties = false }`, so that potential
 * new or non-standard properties in the HAR file don't break the JSON decoding.
 */
@OptIn(ExperimentalSerializationApi::class)
fun Path.readHar(json: Json = Json): Har {
    return inputStream().use { json.decodeFromStream(Har.serializer(), it) }
}

/**
 * Writes the given [har] content to this file.
 */
@OptIn(ExperimentalSerializationApi::class)
fun Path.writeHar(har: Har, json: Json = Json) {
    outputStream().use { json.encodeToStream(Har.serializer(), har, it) }
}
