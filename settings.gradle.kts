plugins {
    id("com.gradle.develocity") version "3.19.2"
}

rootProject.name = "har-parser"

develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/terms-of-service"
        termsOfUseAgree = "yes"
        uploadInBackground = false // bad for CI, and not critical for local runs
    }
}
