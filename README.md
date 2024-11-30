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
    id("com.google.gms.google-services")    // if you will work with firebase
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
    id("com.google.gms.google-services") version "4.4.2" apply false     // if you will work with firebase
}

 dependencies {
        classpath("com.google.gms:google-services:4.4.2")   // if you will work with firebase
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


 // if you will work with firebase

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


5. **Examples :

```jsx



class MainActivity : AppCompatActivity() {


    // 1 init the notification sender with your activity or fragment or compose activity
    private val notificationSender by lazy { NotificationSender(this) } // required


    // 2 init fcm view model if you will use the remote fcm   -> Optional

    private val fcmViewModel: FcmViewModel by lazy {
        val apiService = RetrofitInstance.apiService
        val fcmRepository = FcmRepositoryImpl(apiService)
        val sendFcmNotificationUseCase = SendFcmNotificationUseCase(fcmRepository)
        FcmViewModel(sendFcmNotificationUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 3 Required
        checkNotificationPermission()
       // RequestNotificationPermission()  // with Jetpack Compose Call this line only

        // 4 set your channel id before create a notification channel
        NotificationConstants.CHANNEL_ID = "default_channel_id"


        // 5 createNotificationChannel  // required
        notificationSender.createNotificationChannel(
            channelName = NotificationConstants.CHANNEL_NAME,  // you can change it as you need
            channelDescription = NotificationConstants.CHANNEL_DESCRIPTION // // you can change it as you need
        )


        //  Example 1 Send a basic notification
        notificationSender.sendNotification(
            notificationId = 1,
            title = "Exclusive Discount From Salem!",  // optional
            content = "Buffalo Burger: Enjoy 50% off on all burgers today!", // optional
            icon = R.drawable.ic_launcher_foreground,  // optional
            bigIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_user), // optional
            intent = Intent(this, MainActivity::class.java)  // optional
        )


    }


    private fun checkNotificationPermission() {
        requestNotificationPermission { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "generated", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "not generated", Toast.LENGTH_SHORT).show()
            }
        }
    }


}


```


7. **Send a basic notification :

```jsx

    notificationSender.sendNotification(
            notificationId = 1 ,
            title   =   "Exclusive Discount From Salem!",  // optional
            content =   "Buffalo Burger: Enjoy 50% off on all burgers today!", // optional
            icon    =    R.drawable.ic_launcher_foreground,  // optional
            bigIcon =    BitmapFactory.decodeResource(resources, R.drawable.ic_user), // optional
            intent  =    Intent(this , MainActivity::class.java)  // optional
        )

```




8. **Send a Expandable Notification with xml from local:

```jsx
        notificationSender.showExpandableNotificationXml(
            context = this,
            title = "Expand Notification From Salem",
            content = "Buffalo Burger: Enjoy 50% off on all burgers today!",
            smallIcon = R.drawable.ic_launcher_foreground,
            expandImage  = R.drawable.buffalo_burger_disccount_img,
            intent = Intent(this , MainActivity::class.java),
            notificationId = 1
        )

```



9. **Send a  Expandable Notification with xml and expand img from url:
    
```jsx
         notificationSender.showExpandableNotificationXml(
            context   = this,
            title     = "\uD83D\uDD25 Exclusive Discount! \uD83D\uDD25",
            content   = "Buffalo Burger: Enjoy 40% off on all burgers today!",
            expandImage    = "https://buffalonlineorderingprod.s3.eu-west-1.amazonaws.com/offers/7d9d03be48a42c120831705d40646f23.png",
            smallIcon = R.drawable.ic_launcher_foreground ,
            notificationId = 1,
            intent = intent
        )
```
10. **Update your notification with the same notification id :

```jsx
        notificationSender.updateNotificationXml(
            notificationId = 1 ,
            title   =   "Exclusive Discount From Salem!",  // optional
            content =   "Buffalo Burger: Enjoy 50% off on all burgers today!", // optional
            icon    =    R.drawable.ic_launcher_foreground,  // optional
            bigIcon =    BitmapFactory.decodeResource(resources, R.drawable.ic_user), // optional
            intent  =    Intent(this , MainActivity::class.java)  // optional
        )
```


11. **Cancel your notification with the same notification id you have created notification with :

```jsx

    notificationSender.cancelNotification(1)
```

12. **Cancel all your notifications:

```jsx
   notificationSender.cancelAllNotifications()
```


13. ** Get Device Token:

```jsx
var deviceToken : String ? = null
 private fun getTheDeviceToken() {
        getDeviceToken(
            deviceToken = { token ->
                Log.e("testAccessToken", "device token  $token")
                deviceToken = token
            },
            onFailure = {
                Log.e("testAccessToken", "Error  token  $it")
            }
        )
    }
```

14. ** Get Access Token:

```jsx
   private fun getAccessToken() {

        val scopes = listOf(
            "https://www.googleapis.com/auth/firebase.messaging",
            "https://www.googleapis.com/auth/cloud-platform"
        )

        fetchAccessToken(
            this,
            scopes,
            notificationServiceAccountJsonFile = R.raw.notification_service_account, // get a file service account from firebase console -- project setting --  service accounts   -> choose java   and click on generate a new private key
            onSuccess = { accessToken ->

                // by getting access token you can send a FCM remote notification to some topic or to user
                Log.e("TestFcm", "Access token $accessToken")
            },
            onFailure = {
                Log.e("TestFcm", it.toString())
                Toast.makeText(this, "Failed to get Access token ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            },
        )

    }

```


15. ** Send Remote FCM To User:

```jsx


   private val fcmViewModel: FcmViewModel by lazy {
        val apiService = RetrofitInstance.apiService
        val fcmRepository = FcmRepositoryImpl(apiService)
        val sendFcmNotificationUseCase = SendFcmNotificationUseCase(fcmRepository)
        FcmViewModel(sendFcmNotificationUseCase)
    }


        sendNotificationToUser(
            accessToken = "get your access token",
            deviceToken = "get your device token",
            data =  mapOf(
                "logo_image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXMSWIWAlex9VThDh8XjpVd0noYacIuPgCmQ&s",
                "expanded_image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3OItiHanoLSPt_j9cMj7U1t_4Ft4_Zun2Kg&s"
            ),
        )


     private fun sendNotificationToUser(
        accessToken: String,
        deviceToken: String,
        notificationTitle: String? = null,  // optional
        notificationBody: String? = null,  // optional
        data: Map<String, String> = emptyMap(),
    ) {

        val message = Message(
            token = deviceToken,
            data = data,
//            notification = Notification(notificationTitle , notificationBody) // optional
        )

        val notificationRequest = FcmNotificationRequest(message = message)

        fcmViewModel.sendFcmNotification(
            accessToken = accessToken,
            projectId = "android-notifications-f597d", // Id of project get it from firebase console
            fcmNotificationRequest = notificationRequest
        )
    }

```


16. ** Send Remote FCM To User:

```jsx


   private val fcmViewModel: FcmViewModel by lazy {
        val apiService = RetrofitInstance.apiService
        val fcmRepository = FcmRepositoryImpl(apiService)
        val sendFcmNotificationUseCase = SendFcmNotificationUseCase(fcmRepository)
        FcmViewModel(sendFcmNotificationUseCase)
    }

          sendNotificationToUser(
                    accessToken  = "get your access token" ,
                    deviceToken  = "get your device token" ?: "",
                    data = mapOf(
                        "logo_image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3OItiHanoLSPt_j9cMj7U1t_4Ft4_Zun2Kg&s",
                        "expanded_image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3OItiHanoLSPt_j9cMj7U1t_4Ft4_Zun2Kg&s"
                    )
                )


    private fun sendNotificationToTopic(
        accessToken: String,
        topic: String,
        notificationTitle: String? = null, // optional
        notificationBody: String? = null,  // optional
        data: Map<String, String> = emptyMap(),
    ) {

        val message = Message(
            topic = topic,
            data = data,
//            notification = Notification(notificationTitle , notificationBody) // optional
        )


        val notificationRequest =
            FcmNotificationRequest(message = message)

        fcmViewModel.sendFcmNotification(
            accessToken = accessToken,
            projectId = "android-notifications-f597d",   // Id of project get it from firebase console
            fcmNotificationRequest = notificationRequest
        )

    }

```



17. ** Refresh Token:

```jsx

    private fun refreshTheDeviceToken() {
        refreshDeviceToken(
            refreshedToken = {
                Log.e("testAccessToken", "device token  $it")
            },
            onFailure = {
                Log.e("testAccessToken", "Error refresh Token  ${it.message}")
            }
        )
    }

```


18. ** Refresh Device Token:

```jsx

  private fun deleteTheDeviceToken() {
        deleteDeviceToken(
            onSuccess = {
                Log.e("testAccessToken", "Device token deleted successfully")
            },
            onFailure = {
                Log.e(
                    "testAccessToken",
                    "Something went wrong with delete device token  ${it.message}"
                )
            }
        )
    }

```


19. ** Subscribe The Topic :

```jsx

 subscribeTheTopic("Weather")

 private fun subscribeTheTopic(topic: String) {
        subscribeTopic(
            topic, // Topic Name
            onSuccess = {
                Toast.makeText(this, "Subscribed", Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(this, "Subscribed failed ${it.message}", Toast.LENGTH_SHORT).show()
            })
    }


```



20. ** Un Subscribe The Topic :

```jsx


unSubscribeTheTopic("Weather")

private fun unSubscribeTheTopic(topic: String) {
        unSubscribeTopic(
            topic,
            onSuccess = {
                Toast.makeText(this, "Un Subscribed", Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(this, "Un Subscribed failed ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            })
    }

```

20. ** Fetch The Google Credentials :

```jsx


    private fun fetchTheGoogleCredentials() {
        val scopes = listOf(
            "https://www.googleapis.com/auth/firebase.messaging",
            "https://www.googleapis.com/auth/cloud-platform"
        )

        fetchGoogleCredentials(
            context = this,
            scopes = scopes,
            notificationServiceAccountJsonFile = R.raw.notification_service_account, // get a file service account from firebase console -- project setting --  service accounts   -> choose java   and click on generate a new private key
            onSuccess = { credentials ->
                // Use the GoogleCredentials object as needed
                Log.e("TestFCM", "credentials $credentials")
            },
            onFailure = { exception ->
                // Handle the failure
                Log.e("TestFCM", "error $exception")
                println("Failed to retrieve GoogleCredentials: ${exception.message}")
            }
        )
    }

```

21. ** Observe Loading Remote Fcm:

```jsx

  private fun observeLoadingRemoteFcm() {
        fcmViewModel.fcmLoading.onEach { loading ->
            if (loading) {
                Log.e("TestFcm", "Send Fcm Notification is loading")
            } else {
                Log.e("TestFcm", "Send Fcm Notification is stopped")
            }
        }.launchIn(lifecycleScope)  // with fragment .launchIn(viewLifecycleOwner.lifecycleScope)

    }


```


22. ** Observe Success Remote Fcm:

```jsx

     private fun observeSuccessRemoteFcm() {
        fcmViewModel.fcmResponse.onEach { fcmResponse ->
            fcmResponse?.let {
                Toast.makeText(
                    this,
                    "Notification sent successfully: ${it.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.launchIn(lifecycleScope)  // with fragment .launchIn(viewLifecycleOwner.lifecycleScope)
    }


```


23. ** Observe Error Remote Fcm:

```jsx

  private fun observeErrorRemoteFcm() {
        fcmViewModel.error.onEach { fcmError ->
            fcmError?.let {
                Log.e("TestFcm", "Error to send a fcm notification $fcmError")
            }
        }.launchIn(lifecycleScope) // with fragment .launchIn(viewLifecycleOwner.lifecycleScope)
    }

```



24. ** Firebase Messaging Service:

```jsx

 class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        // 1-  Get the FCM Send by the notification
        remoteMessage.notification?.let {

            val notificationTitle = it.title
            val notificationBody = it.body


        }

        // 2-  Get the FCM Send by the Data
        remoteMessage.data.isNotEmpty().let {
            val logoImage = remoteMessage.data["logo_image"] ?: "empty logo image"
            val expandedImage = remoteMessage.data["expanded_image"] ?: "empty expanded image"

            CustomNotificationClass(this ).sendCustomNotification(
                logoImage = logoImage,
                expandImage = expandedImage
            )

            Log.e("testFCM", "Remote logo image is $logoImage")
            Log.e("testFCM", "Remote expanded image is $expandedImage")


        }
    }
}

```




24. ** Create a custom notifications work with both xml and jetpack compose:

```jsx


1-  create a main layout

    Go to res -> add a new layout named main_notification_layout

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center_vertical"
    >



    <ImageView
        android:id="@+id/custom_notification_icon"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:src="@drawable/ic_launcher_foreground"
        />


    <TextView
        android:id="@+id/title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toEndOf="@id/custom_notification_icon"
        android:layout_marginStart="10dp"
        android:text="Custom Title"
        android:textSize="16sp"
        android:textStyle="bold" />

    <!-- Message Text -->
    <TextView
        android:id="@+id/message"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/title"
        android:layout_toEndOf="@id/custom_notification_icon"
        android:layout_marginStart="10dp"
        android:text="Custom Content"
        />


</RelativeLayout>



```

 ** Create a second layout ( Expanded layout )   named expanded_notification_layout

```jsx

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    >


    <ImageView
        android:id="@+id/custom_notification_icon"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:src="@drawable/ic_launcher_foreground"
        android:contentDescription="@null"
        />

    <TextView
        android:id="@+id/title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toEndOf="@id/custom_notification_icon"
        android:layout_marginStart="10dp"
        android:text="Custom Title"
        android:textSize="16sp"
        android:textStyle="bold" />


    <TextView
        android:id="@+id/message"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/title"
        android:layout_toEndOf="@id/custom_notification_icon"
        android:layout_marginStart="10dp"
        android:text="Custom Content" />



    <ImageView
        android:id="@+id/img_expand"
        android:layout_width="40dp"
        android:layout_height="150dp"
        android:src="@drawable/food_logo"
        android:contentDescription="@null"
        android:layout_below="@+id/message"
        android:layout_alignParentStart="true"
        android:layout_alignParentEnd="true"
        android:scaleType="centerCrop"
        android:layout_marginTop="10dp"

        />

    
</RelativeLayout>

```



 ** Create a Custom Notification Class

```jsx
class CustomNotificationClass( private val context: Context ) {

    fun sendCustomNotification( logoImage: String , expandImage: String ) {
        CoroutineScope(Dispatchers.IO).launch {

            val bitmapLogoImage       = async {   loadImageFromUrl(logoImage)    }.await()   // loadImageFromUrl fun  will convert it to BitMap
            val bitmapExpandedImage   = async {   loadImageFromUrl(expandImage)  }.await()

            val customView = RemoteViews( context.packageName, R.layout.main_notification_layout ).apply {
                setTextViewText(R.id.title, "\uD83D\uDD25 Exclusive Discount!")
                setTextViewText(R.id.message, "Buffalo Burger: Enjoy 50% off on all burgers today!")
                setImageViewBitmap(R.id.custom_notification_icon, bitmapLogoImage)
            }

            val notificationLayoutExpanded =
                RemoteViews(context.packageName, R.layout.expanded_notification_layout).apply {
                    setTextViewText(R.id.title, "\uD83C\uDF54 Buffalo Burger Discount!")
                    setTextViewText(
                        R.id.message,
                        "Treat yourself to a delicious meal! Get 50% off all burgers today only. Don't miss this mouth-watering deal!"
                    )
                    setImageViewBitmap(
                        R.id.custom_notification_icon,
                        bitmapLogoImage
                    )
                    setImageViewBitmap(R.id.img_expand, bitmapExpandedImage)
                }


            val icon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                R.drawable.ic_launcher_foreground
            } else {
                R.mipmap.ic_launcher
            }

            Log.e("testCustom" , "Called")

            NotificationSender(context).sendNotification(
                notificationId = 1,
                icon = icon,
                setPriority = NotificationCompat.PRIORITY_HIGH,
                setCustomContentView = customView,
                setCustomBigContentView = notificationLayoutExpanded
            )
        }
    }

}

```

 ** Push your custom notification

```jsx

 CustomNotificationClass(this ).sendCustomNotification(
                logoImage = "https://scontent.fcai21-4.fna.fbcdn.net/v/t1.6435-9/110249313_10158045681834681_5826574394868309860_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=a5f93a&_nc_eui2=AeHa1Tms6cyru9CWYZe_AyXx4DX-FVvfQFXgNf4VW99AVbo92PGk5XgNqZ26W5vE4FMzzyVgVy2Ep24VJDVlWbVW&_nc_ohc=cRfLgxTAB6IQ7kNvgEeYqnM&_nc_zt=23&_nc_ht=scontent.fcai21-4.fna&_nc_gid=AbVCH5NIkcqRdNcXUvdp9iG&oh=00_AYCPmL3XrPJpKs_52MRgsH6u2EJwY2juGp3lnQBDQuDksA&oe=67728069",
                expandImage = "https://images.pexels.com/photos/1448730/pexels-photo-1448730.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
            )
```










