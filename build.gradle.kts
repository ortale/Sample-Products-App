// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
    }
}
extra.apply {
    set("FLAVOR_DIMENSION", "mode")
    set("DEV_API_URL", "https://real-time-amazon-data.p.rapidapi.com")
    set("DEV_APP_LABEL", "Sample Products App-Dev")
    set("DEV_APP_ICON", "@mipmap/ic_launcher")

    set("PROD_API_URL", "https://real-time-amazon-data.p.rapidapi.com")
    set("PROD_APP_LABEL", "Sample Products App")
    set("PROD_APP_ICON", "@mipmap/ic_launcher")
}