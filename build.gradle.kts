plugins {
    val kotlin = "1.9.22"
    kotlin("multiplatform") version kotlin
    kotlin("plugin.serialization") version kotlin
    id("org.jetbrains.dokka") version "1.9.10"
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    id("org.hildan.github.changelog") version "2.0.0"
    id("org.hildan.kotlin-publish") version "1.3.0"
    id("ru.vyarus.github-info") version "1.5.0"
}

group = "org.hildan.har"
description = "A Kotlin parser for HAR (HTTP Archives) files"

github {
    user = "joffrey-bion"
    license = "MIT"
}

changelog {
    githubUser = github.user
    futureVersionTag = project.version.toString()
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(11)

    jvm()
    js {
        browser()
        nodejs()
    }
    mingwX64()
    linuxX64()
    macosX64()
    macosArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
                api("org.jetbrains.kotlinx:kotlinx-io-bytestring:0.3.0")
            }
        }
    }
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
