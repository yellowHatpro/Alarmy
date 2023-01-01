package org.yellowhatpro.alarmy

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import org.yellowhatpro.alarmy.reciever.AlarmReceiver

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setExactAlarm(timeInMillis: Long){
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = "ACTION_SET_EXACT_ALARM"
                    putExtra("EXTRA_EXACT_ALARM_TIME", timeInMillis)
                }
            )
        )
    }

    //Will be using this
    fun setRepetitiveAlarm(timeInMillis: Long){
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = "ACTION_SET_REPETITIVE_ALARM"
                    putExtra("EXTRA_EXACT_ALARM_TIME", timeInMillis)
                }
            )
        )
    }

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent){
        alarmManager?.let {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
        }
    }

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntent(intent: Intent) =
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
}