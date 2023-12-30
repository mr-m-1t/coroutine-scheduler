@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    // TODO: add other targets?
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js {
        nodejs()
        // doesn't see firefox installed as a snap
//        browser()
    }
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.kotlin.datetime)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.coroutines.test)
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.test.common)
                implementation(libs.kotlin.test.annotations.common)
            }
        }
    }
}
