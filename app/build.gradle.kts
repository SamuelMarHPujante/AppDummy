plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version libs.versions.kotlin apply false
}

android {
    namespace = "edu.marhuendasamuel.appdummy"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "edu.marhuendasamuel.appdummy"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // ViewModel y Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")

    // Corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // Testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")

    // Navigation Compose (API tipada disponible desde 2.8.0)
    implementation("androidx.navigation:navigation-compose:2.9.8")

    // Kotlin Serialization — imprescindible para las rutas @Serializable
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")

    // Material Icons Extended
    implementation("io.coil-kt.coil3:coil-compose:3.5.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.5.0")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:x.y.z")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}