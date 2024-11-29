# Android Notifications Library 

This notification library simplifies managing and sending notifications in Android apps, offering powerful features with minimal effort. From easy notification channel creation to personalized and topic-based messaging with Firebase Cloud Messaging (FCM), the library streamlines the entire notification process. With support for permission handling, expandable and custom notifications, device token management, and more, it ensures developers can deliver engaging and dynamic user experiences with ease. 🚀

## Example


## Features
- **Simple Notification Sending: Easily send notifications with a single method call. 🚀

- **Easy Permission Requests: Effortlessly request notification permissions using Fragment, Activities, or Jetpack Compose. ✅

- **Quick Channel Creation: Simplify the creation of notification channels with customizable options. 🛠️

- **Expandable Notifications: Easily display expandable notifications using XML layouts. 🖼️

- **Update Notifications: Update existing notifications seamlessly with new content or actions. 🔄

- **Cancel Individual Notifications: Remove specific notifications from the notification tray. ❌

- **Cancel All Notifications: Clear all active notifications with one call. 🧹

- **Delete Notification Channels: Manage and delete unused notification channels dynamically. 🗑️

- **Device Token Management:

- **Fetch the device's unique notification token. 🔑
- **Refresh the device token to keep it updated. ♻️
- **Delete the device token when no longer needed. 🗝️
- **Access Token Handling: Retrieve and manage access tokens for secure communication. 🔐

- **User-Specific Notifications: Send personalized remote FCM (Firebase Cloud Messaging) notifications to individual users. 👤✉️

- **Topic-Based Messaging:

- **Send remote FCM notifications to a subscribed topic. 📢
- **Subscribe or unsubscribe users from specific notification topics. 🔔
- **Google Credentials Management: Fetch and manage Google credentials required for FCM communication. 🌐

- **Custom Notifications:

- **Easily set and manage custom notifications for unique user experiences. 🎨



## Installation

To install and use Android Carousel View Library, follow these step-by-step instructions:
### Step 1:  Add jitpack in **settings.gradle.kts**

```jsx

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" ) }

    }
}
```

2. Add this dependency in **build.gradle** :

```jsx


Please note if you are looking to work with firebase and send FCM Remote just  install firebase dependencies if you will not use not import it 

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")  // optional for firebase
    id("kotlin-kapt")
}

android {
    packaging {    // add packging 
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
        }
    }
}


dependencies {


    // Replace 'latest-version' with the actual latest version number
    implementation("com.github.mhmdsalem33:AndroidNotificationsLibrary:latest-version")


     // optional  if you will work with  firebase only import it
     // Firebase dependencies
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging:23.0.3")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.3.0")


}
```


3. Add this dependency in **build.gradle**  Project:

```jsx
plugins {
    id("com.google.gms.google-services") version "4.4.2" apply false
}

 dependencies {
        classpath("com.google.gms:google-services:4.4.2") 
    }

```

4. ** Android Manifest File:

```jsx

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />    



   <application
        android:name="com.salem.notifications.core.NotificationApp"  // Add App Class  You can add your own app class
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AndroidNotifications"
        tools:targetApi="31">



        <service
            android:name=".firebase.MyFirebaseMessagingService"
            android:exported="false">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>


class NotificationApp  : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)    // Init Firebase 
    }
}



```






