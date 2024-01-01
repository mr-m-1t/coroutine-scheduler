plugins {
    alias(libs.plugins.spotless)
    // this is necessary to avoid the plugins to be loaded multiple times in each subproject's classloader
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}

// TODO: setup CI using GitHub actions
// TODO: Add & publish dokka?
// TODO: publish to maven central
//  https://madhead.me/posts/no-bullshit-maven-publish/
//  https://docs.github.com/en/actions/publishing-packages/publishing-java-packages-with-gradle
// TODO: setup semantic releases
// TODO: rename module to `coroutine-scheduler`, also packages

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

subprojects {
    // make linting a prerequisite for building, so we don't forget it
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.BaseKotlinCompile> {
        dependsOn(":spotlessApply")
    }
}
