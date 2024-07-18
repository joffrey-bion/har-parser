# har-parser

[![Maven central version](https://img.shields.io/maven-central/v/org.hildan.har/har-parser.svg)](https://search.maven.org/artifact/org.hildan.har/har-parser)
[![Github Build](https://img.shields.io/github/actions/workflow/status/joffrey-bion/har-parser/build.yml?branch=main&logo=github)](https://github.com/joffrey-bion/har-parser/actions/workflows/build.yml)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/joffrey-bion/har-parser/blob/main/LICENSE)

A Kotlin multiplatform library to parse HAR (HTTP Archive) files, including support for the web socket traffic format.

## Setup

Add the dependency:

```kotlin
dependencies {
    implementation("org.hildan.har:har-parser:$version")
}
```

## Usage

### Parse HAR text

You can get the data as text first, and then parse it using:

```kotlin
import org.hildan.har.*

val harText: String = TODO("get some textual HAR-encoded data")
val har: Har = Har.parse(harText)
```

### Parse HAR files

On the JVM, you can use the `Path.readHar()` extension to read a HAR file:

```kotlin
import kotlin.io.path.*
import org.hildan.har.*

val harPath = Path("./my-recording.har")
val har: Har = harPath.readHar()

har.log.entries.forEach {
    println("${it.request.method} ${it.response.status} ${it.request.url}")
}
```

You can also write a HAR file using `Path.writeHar(Har)`.
