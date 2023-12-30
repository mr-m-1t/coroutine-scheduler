@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.spotless)
}

// TODO: setup CI using GitHub actions
// TODO: Add dokka
// TODO: publish to maven central
// TODO: write documentation (integration, usage, examples)
// TODO: publish docs

allprojects {
    repositories {
        mavenCentral()
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("*/src/**/*.kt")
        ktlint("1.1.0")
    }
}
