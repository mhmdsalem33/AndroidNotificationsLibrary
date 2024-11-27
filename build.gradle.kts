// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.android.library) apply false
}


buildscript {




    repositories {
        google()
        mavenCentral()
    }

    dependencies {
//        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
        classpath("com.google.gms:google-services:4.4.2") // Check for the latest version

    }
}


