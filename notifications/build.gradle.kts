import org.gradle.kotlin.dsl.support.kotlinCompilerOptions

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
    id ("kotlin-kapt")
}

android {
    namespace = "com.salem.notifications"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    viewBinding { enable = true }

    buildFeatures{
        compose = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)



//    implementation("androidx.compose.compiler:compiler:1.5.1")

    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging:23.0.3")
    implementation ("com.google.auth:google-auth-library-oauth2-http:1.3.0")


    // gson
    implementation ("com.google.code.gson:gson:2.9.0")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // okhttp
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")


//    implementation("com.google.firebase:firebase-functions-ktx:21.1.1") // Use the latest version available


    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("androidx.core:core-ktx:1.10.1")

//    implementation(project(":app"))



}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.salem.androidnotifications"
            artifactId = "notifications"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}