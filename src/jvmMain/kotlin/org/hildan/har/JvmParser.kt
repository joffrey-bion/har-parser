package org.hildan.har

import kotlinx.serialization.json.*
import kotlinx.serialization.*
import okio.*
import java.nio.file.Path
import kotlin.io.path.*

/**
 * Parses this HAR file into a [Har] object.
 */
@OptIn(ExperimentalSerializationApi::class)
fun Path.parseHar(): Har = inputStream().use { HarJsonDecoder.decodeFromStream<Har>(it) }
