package com.safety.womensafetyapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.safety.womensafetyapp.R

object AlertsManager {
    private const val CHANNEL_ID = "safety_alerts"
    private const val CHANNEL_NAME = "Safety Alerts"
    private const val CHANNEL_DESC = "Notifications about safe and unsafe zones"
    private const val NOTIF_ID = 1001

    fun showSummary(context: Context) {
        try {
            createChannel(context)

            // Static Mumbai safety summary (no database)
            val hospitals = 3
            val police = 3
            val shelters = 0
            val unsafeRecent = 0

            val title = "Mumbai Safety Summary"
            val content = "Hospitals: $hospitals • Police: $police • Shelters: $shelters • Unsafe (30d): $unsafeRecent"

            val notif = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context).notify(NOTIF_ID, notif)
        } catch (_: Exception) {
            // No-op: never crash the app due to notifications
        }
    }

    private fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.description = CHANNEL_DESC
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }
}
