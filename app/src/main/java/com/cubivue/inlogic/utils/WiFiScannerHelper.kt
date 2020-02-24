package com.cubivue.inlogic.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Handler
import androidx.lifecycle.Lifecycle
import com.embrace.plog.pLogs.PLog

class WiFiScannerHelper(
    val doOnResults: (results: List<ScanResult>) -> Unit,
    val lifecycle: Lifecycle
) {

    private val TAG = "WiFiScannerHelper"

    private lateinit var wifiManager: WifiManager

    private val wifiScanReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getBooleanExtra(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION, false)?.let {
                if (it) {
                    scanSuccess(context)
                } else {
                    scanFailure(context)
                }
            }
        }
    }

    fun setupWifiManager(applicationContext: Context, context: Context) {
        PLog.logThis(TAG, "setupWifiManager", "Wi-Fi Scanner is started.")
        wifiManager =
            applicationContext.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {

            val intentFilter = IntentFilter()
            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
            context.registerReceiver(wifiScanReceiver, intentFilter)
        }
    }

    fun unregisterReceiver(context: Context?) {
        context?.unregisterReceiver(wifiScanReceiver)
    }

    private fun scanSuccess(context: Context?) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) {
            PLog.logThis(TAG, "scanSuccess", "Activity is Destroyed.")
            unregisterReceiver(context)
        } else {
            PLog.logThis(TAG, "scanSuccess", "Success: SCAN_RESULTS_AVAILABLE_ACTION")
            val results = wifiManager.scanResults
            doOnResults.invoke(results)
        }
    }

    private fun scanFailure(context: Context?) {

        if (lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) {
            PLog.logThis(TAG, "scanFailure", "Activity is Destroyed.")
            unregisterReceiver(context)
        } else {
            // handle failure: new scan did NOT succeed
            // consider using old scan results: these are the OLD results!
            val results = wifiManager.scanResults
            doOnResults.invoke(results)
        }
    }

    fun startScanner(attachHandler: Boolean = true) {
        if (attachHandler) {
            // Create the Handler object (on the main thread by default)
            val handler = Handler()
            val runnableCode = object : Runnable {
                override fun run() {
                    wifiManager.startScan()
                    handler.postDelayed(this, 5000)
                }
            }
            handler.post(runnableCode)
        } else {
            wifiManager.startScan()
        }
    }
}