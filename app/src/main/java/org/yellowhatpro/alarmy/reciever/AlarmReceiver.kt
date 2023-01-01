package org.yellowhatpro.alarmy.reciever

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.format.DateFormat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.yellowhatpro.alarmy.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //Do stuff when the alarm is executed
        //In our case, we will build a notification here
        val timeInMillis = intent.getLongExtra("EXTRA_EXACT_ALARM_TIME", 0L)
        createNotificationChannel(
            context,
            timeInMillis
        )
    }
    private fun createNotificationChannel(context: Context, timeInMillis: Long) {

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Alarmy")
            .setContentText("Alarmy Here to wake you up at ${convertDate(timeInMillis)}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Alarmy"
            val descriptionText = "Alarmy"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            with(NotificationManagerCompat.from(context)){
                notify(101, builder.build())
            }
        }
    }

    private fun convertDate(timeInMillis: Long) =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()
}