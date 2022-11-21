plugins {
    alias(libs.plugins.spotless)
}

// TODO: setup CI using GitHub actions
// TODO: Add dokka
// TODO: publish to maven central
// TODO: publish docs
// TODO: Add logging?
// TODO: create DSL?

allprojects {
    repositories {
        mavenCentral()
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("*/src/**/*.kt")
        ktlint("0.47.1")
            .setUseExperimental(true)
            .editorConfigOverride(
                mapOf(
                    "ij_kotlin_allow_trailing_comma" to true
                )
            )
    }
}
