package org.hildan.har

import kotlinx.serialization.json.*
import kotlinx.serialization.*
import okio.*
import java.nio.file.Path
import kotlin.io.path.*

// ignoreUnknownKeys necessary for ignoring the 'opcode' in web socket messages.
// It's also useful if some HAR files have extra stuff we don't know about.
private val json = Json { ignoreUnknownKeys = true }

/**
 * Parses this HAR file into a [Har] object.
 */
@OptIn(ExperimentalSerializationApi::class)
fun Path.parseHar(): Har = inputStream().use { json.decodeFromStream<Har>(it) }
