plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.hildan.kotlin-publish") version "1.1.1"
    id("ru.vyarus.github-info") version "1.5.0"
}

group = "org.hildan.har"
description = "A Kotlin parser for HAR (HTTP Archives) files"

github {
    user = "joffrey-bion"
    license = "MIT"
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    api("com.squareup.okio:okio:3.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}

nexusPublishing {
    packageGroup.set("org.hildan")
    this.repositories {
        sonatype()
    }
}

publishing {
    // configureEach reacts on new publications being registered and configures them too
    publications.configureEach {
        if (this is MavenPublication) {
            pom {
                developers {
                    developer {
                        id.set("joffrey-bion")
                        name.set("Joffrey Bion")
                        email.set("joffrey.bion@gmail.com")
                    }
                }
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(extensions.getByType<PublishingExtension>().publications)
}
