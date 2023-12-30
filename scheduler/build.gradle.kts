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
