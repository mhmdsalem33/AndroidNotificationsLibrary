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

dependencies {
    // Replace 'latest-version' with the actual latest version number
    implementation("com.github.mhmdsalem33:AndroidNotificationsLibrary:latest-version")
}
```


