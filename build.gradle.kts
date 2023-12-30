@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.spotless)
    // this is necessary to avoid the plugins to be loaded multiple times in each subproject's classloader
    alias(libs.plugins.android.library) apply false
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
