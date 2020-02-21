package com.cubivue.inlogic.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.embrace.plog.pLogs.PLog
import com.embrace.plog.pLogs.models.LogLevel

class AlarmReceiver : BroadcastReceiver() {

    private val TAG = "AlarmReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        PLog.logThis(TAG, "onReceive", "Scheduler Running", LogLevel.INFO)

        if (intent.action != null) {
            if (intent.action == alarmIntent) {
                context.startService(Intent(context, ScannerService::class.java))
            }
        }
    }

    companion object {

        val alarmIntent = "com.cubivue.inlogic.alarmIntent"
        val alarmIntentCode = 54310637
    }
}
