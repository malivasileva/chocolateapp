// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val nav_version = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }

    extra.apply {
        set("lifecycle_version", "2.6.2")
        set("room_version", "2.6.1")
        set("nav_version", "2.7.7")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.jetbrains.kotlin.android) apply false

    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
}