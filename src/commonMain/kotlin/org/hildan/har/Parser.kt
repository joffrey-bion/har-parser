package org.hildan.har

import kotlinx.serialization.json.*

/**
 * A [Json] instance configured to (de)serialize HAR files into [Har] objects.
 *
 * `ignoreUnknownKeys` necessary for ignoring the 'opcode' in web socket messages (which is only used to select the
 * message subtypes, but not deserialized as a property).
 * It's also useful for forward-compatibility, if some HAR files have extra stuff we don't know about.
 */
val HarJsonDecoder = Json { ignoreUnknownKeys = true }
