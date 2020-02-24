package com.cubivue.inlogic.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cubivue.inlogic.R
import com.cubivue.inlogic.components.AlarmHelper
import com.cubivue.inlogic.utils.WiFiScannerHelper
import com.cubivue.inlogic.utils.logs.LogsHelper
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST = 1
    private val STORAGE_PERMISSIONS_REQUEST = 10

    private lateinit var wiFiScannerHelper: WiFiScannerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getStoragePermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    STORAGE_PERMISSIONS_REQUEST
                )
            } else {
                LogsHelper.setUpPLogger(this)
                getWifiPermissions()
            }
        }
    }

    private fun getWifiPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST
                )

            } else {
                setUpWifiScanner()
            }

        } else {
            setUpWifiScanner()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        if (requestCode == MY_PERMISSIONS_REQUEST) {

            if(grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpWifiScanner()
                } else {
                    return
                }
            }
        }else if (requestCode == STORAGE_PERMISSIONS_REQUEST) {

            if(grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogsHelper.setUpPLogger(this)
                    getWifiPermissions()
                } else {
                    return
                }
            }
        }
    }

    override fun onPause() {
        wiFiScannerHelper.unregisterReceiver(this)
        super.onPause()
    }

    private fun setUpWifiScanner(){
        AlarmHelper.startSchedulerAlarm(this)
        wiFiScannerHelper = WiFiScannerHelper(::doOnResults, lifecycle)
        wiFiScannerHelper.setupWifiManager(applicationContext!!, this)
        wiFiScannerHelper.startScanner()
    }

    private fun doOnResults(results: List<ScanResult>) {

    }

    override fun onResume() {
        super.onResume()
        getStoragePermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        AlarmHelper.stopSchedulerAlarm(this)
    }
}
