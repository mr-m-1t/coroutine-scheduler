plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    explicitApi()

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js {
        nodejs()
        // doesn't see firefox installed as a snap
        browser()
    }
    jvm()
    linuxArm64()
    linuxX64()
    macosArm64()
    macosX64()
    mingwX64()
    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()
    watchosArm32()
    watchosArm64()
    watchosX64()

    sourceSets {
        commonMain {
            dependencies {
                api(libs.coroutines.core)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.coroutines.test)
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "io.github.mrm1t.coroutineScheduler"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
