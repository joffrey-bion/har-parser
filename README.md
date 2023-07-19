# har-parser

[![Maven central version](https://img.shields.io/maven-central/v/org.hildan.har/har-parser.svg)](https://search.maven.org/artifact/org.hildan.har/har-parser)
[![Github Build](https://img.shields.io/github/actions/workflow/status/joffrey-bion/har-parser/build.yml?branch=main&logo=github)](https://github.com/joffrey-bion/har-parser/actions/workflows/build.yml)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/joffrey-bion/har-parser/blob/main/LICENSE)

A Kotlin library to parse HAR (HTTP Archive) files

## Setup

Add the dependency:

```kotlin
dependencies {
    implementation("org.hildan.har:har-parser:$version")
}
```

## Usage

On the JVM, you can use `Path` extensions to parse a HAR file:

```kotlin
import kotlin.io.path.*

val harPath = Path("./my-recording.har")
val har = harPath.parseHar()

har.log.entries.forEach {
    println("${it.request.method} ${it.response.status} ${it.request.url}")
}
```

On other platforms, you can get the data as text first, and then parse it using:

```kotlin
val harText = TODO("get some textual HAR-encoded data")
val har = Har.parse(harText)
```
