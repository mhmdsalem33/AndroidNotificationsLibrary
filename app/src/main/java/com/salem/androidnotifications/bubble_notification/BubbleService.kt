package com.salem.androidnotifications.bubble_notification

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.salem.androidnotifications.R
import com.salem.androidnotifications.presentation.ui.activites.ShortCutActivity

class BubbleService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var bubbleView: View

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Inflate the bubble layout
        bubbleView = LayoutInflater.from(this).inflate(R.layout.layout_floating_bubble, null)

        // Define bubble layout parameters
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 100

        // Add bubble view to the window
        windowManager.addView(bubbleView, params)

        // Handle drag and click events
        bubbleView.setOnTouchListener(object : View.OnTouchListener {
            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0f
            private var initialTouchY: Float = 0f

            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager.updateViewLayout(bubbleView, params)
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        // Handle bubble click
                        if (event.rawX == initialTouchX && event.rawY == initialTouchY) {
                            openChatActivity()
                        }
                        return true
                    }
                }
                return false
            }
        })
    }

    private fun openChatActivity() {
        val intent = Intent(this, ShortCutActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


//    private fun openChatActivity() {
//        if (!isActivityRunning(ShortCutActivity::class.java)) {
//            val intent = Intent(this, ShortCutActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        } else {
//            Log.d("ShortCutActivity", "Activity is already running!")
//        }
//    }



    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(bubbleView)
    }

    private fun isActivityRunning(activityClass: Class<*>): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = activityManager.getRunningTasks(Int.MAX_VALUE)
        for (task in tasks) {
            if (activityClass.name == task.topActivity?.className) {
                return true
            }
        }
        return false
    }

}
