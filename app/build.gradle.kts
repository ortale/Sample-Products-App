import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.ortalesoft.sampleproductsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ortalesoft.sampleproductsapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    android.buildFeatures.buildConfig = true

    flavorDimensions += rootProject.extra["FLAVOR_DIMENSION"] as String
    productFlavors {
        val secretsFile = "secrets.properties"
        val apikeyPropertiesFile = rootProject.file(secretsFile)
        val apikeyProperties = Properties()
        apikeyProperties.load(apikeyPropertiesFile.inputStream())

        create("dev") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "API_KEY", "\"${apikeyProperties.getProperty("API_KEY")}\"")
            buildConfigField("String", "API_URL", "\"${rootProject.extra["DEV_API_URL"]}\"")

            manifestPlaceholders.putAll(
                mutableMapOf(
                    Pair("appLabel", rootProject.extra["DEV_APP_LABEL"] as String),
                    Pair("icon", rootProject.extra["DEV_APP_ICON"] as String)
                )
            )
        }
        create("prod") {
            applicationIdSuffix = ".prod"
            versionNameSuffix = "-prod"
            buildConfigField("String", "API_KEY", "\"${apikeyProperties.getProperty("API_KEY")}\"")
            buildConfigField("String", "API_URL", "\"${rootProject.extra["PROD_API_URL"]}\"")

            manifestPlaceholders.putAll(
                mutableMapOf(
                    Pair("appLabel", rootProject.extra["PROD_APP_LABEL"] as String),
                    Pair("icon", rootProject.extra["PROD_APP_ICON"] as String)
                )
            )
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Timber for logging
    implementation(libs.timber)

    // Jetpack Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Coil for image loading
    implementation(libs.coil.compose)

    // Retrofit
    testImplementation(libs.androidx.core.testing)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}