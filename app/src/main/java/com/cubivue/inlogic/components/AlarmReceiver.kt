package com.cubivue.inlogic.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.embrace.plog.pLogs.PLog
import com.embrace.plog.pLogs.models.LogLevel
import androidx.core.content.ContextCompat.startForegroundService
import android.os.Build


class AlarmReceiver : BroadcastReceiver() {

    private val TAG = "AlarmReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        PLog.logThis(TAG, "onReceive", "Scheduler Running", LogLevel.INFO)

        if (intent.action != null) {
            if (intent.action == alarmIntent) {
                if (Build.VERSION.SDK_INT > 25) {
                    startForegroundService(context, Intent(context, ScannerService::class.java))
                } else {
                    context.startService(Intent(context, ScannerService::class.java))
                }
            }
        }
    }

    companion object {

        val alarmIntent = "com.cubivue.inlogic.alarmIntent"
        val alarmIntentCode = 54310637
    }
}
