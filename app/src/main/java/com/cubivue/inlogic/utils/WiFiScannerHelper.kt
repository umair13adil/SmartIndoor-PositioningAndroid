package com.cubivue.inlogic.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Handler

class WiFiScannerHelper(val doOnResults: (results: List<ScanResult>) -> Unit) {
    private lateinit var wifiManager: WifiManager

    // Create the Handler object (on the main thread by default)
    private val handler = Handler()

    private val wifiScanReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION, false)
            if (success) {
                scanSuccess()
            } else {
                scanFailure()
            }
        }
    }

    fun setupWifiManager(applicationContext: Context, activity:Activity) {
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        activity.registerReceiver(wifiScanReceiver, intentFilter)
    }

    private fun scanSuccess() {
        val results = wifiManager.scanResults
        doOnResults.invoke(results)
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        doOnResults.invoke(results)
    }

    fun startScanner() {
        val runnableCode = object : Runnable {
            override fun run() {
                wifiManager.startScan()
                handler.postDelayed(this, 5000)
            }
        }
        handler.post(runnableCode)
    }
}