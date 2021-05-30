package com.example.geolocationapplication

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class LocationService : Service() {

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra("command")) {
            "start_service" -> LocationHelper.getInstance()
                .startLocationListening(this.applicationContext, sendNotification)
            "stop_service" -> {
                stopService(intent)
                LocationHelper.getInstance().stopLocating()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private val sendNotification: (Location) -> Unit = ::locationNotification

    private fun locationNotification(l: Location) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "id", "My channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "My channel description"
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(false)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, "id")
            .setSmallIcon(R.drawable.ic_btn_speak_now)
            .setContentTitle("Your Location is:")
            .setContentText(l.latitude.toString() + " " + l.longitude.toString())
        val notification = builder.build()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}