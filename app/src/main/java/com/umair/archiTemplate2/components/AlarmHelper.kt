package com.umair.archiTemplate2.components

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.blackbox.plog.pLogs.PLog
import com.blackbox.plog.pLogs.models.LogLevel

object AlarmHelper {

    private val TAG = "AlarmHelper"

    fun startSchedulerAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = AlarmReceiver.alarmIntent
        val alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.alarmIntentCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (60000).toLong(), alarmIntent)
        PLog.logThis(TAG, "startSchedulerAlarm", "Scheduler alarm started!", LogLevel.INFO)
    }

    fun stopSchedulerAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = AlarmReceiver.alarmIntent
        val alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.alarmIntentCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager?.cancel(alarmIntent)
        PLog.logThis(TAG, "startSchedulerAlarm", "Scheduler alarm stopped!", LogLevel.INFO)
    }
}