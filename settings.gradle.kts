plugins {
    id("com.gradle.enterprise") version "3.17"
}

rootProject.name = "har-parser"

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}
