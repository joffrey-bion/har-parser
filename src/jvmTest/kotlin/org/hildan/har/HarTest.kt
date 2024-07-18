package org.hildan.har

import kotlin.test.*

class HarTest {

    @Test
    fun test() {
        val harResourceStream = HarTest::class.java.getResourceAsStream("/session-with-websockets.har")
            ?: error("test HAR resource not found")
        val harText = harResourceStream.use { it.readAllBytes().decodeToString() }
        val har = Har.parse(harText)

        val expectedHar = Har(
            log = HarLog(
                version = "1.2",
                creator = HarLog.Creator("WebInspector", "537.36"),
                pages = listOf(
                    HarPage(
                        startedDateTime = "2023-07-21T00:00:55.087Z",
                        id = "page_4",
                        title = "https://vipbelote.fr/play/?affiliateId=wpReg",
                        pageTimings = HarPage.Timings(
                            onContentLoad = 174.4509999989532,
                            onLoad = 292.4079999793321,
                        ),
                    )
                ),
                entries = listOf(),
            )
        )

        assertEquals(expectedHar.log.version, har.log.version)
        assertEquals(expectedHar.log.creator, har.log.creator)
        assertEquals(expectedHar.log.pages, har.log.pages)
        assertEquals(1, har.log.entries.size)
    }
}