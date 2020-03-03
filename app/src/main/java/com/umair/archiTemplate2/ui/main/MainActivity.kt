package com.umair.archiTemplate2.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.umair.archiTemplate2.R
import com.umair.archiTemplate2.components.AlarmHelper
import com.umair.archiTemplate2.ui.accessPoint.AccessPointFragment
import com.umair.archiTemplate2.utils.TTSHelper
import com.umair.archiTemplate2.utils.WiFiScannerHelper
import com.umair.archiTemplate2.utils.logs.LogsHelper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    private val TAG = "MainActivity"

    private val MY_PERMISSIONS_REQUEST = 1
    private val STORAGE_PERMISSIONS_REQUEST = 10

    private var wiFiScannerHelper: WiFiScannerHelper? = null

    @Inject
    lateinit var textToSpeech: TTSHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setup Text-to-Speech
        textToSpeech.setUpTextToSpeech()
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

            if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpWifiScanner()
                } else {
                    return
                }
            }
        } else if (requestCode == STORAGE_PERMISSIONS_REQUEST) {

            if (grantResults.isNotEmpty()) {
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
        wiFiScannerHelper?.unregisterReceiver(this)
        super.onPause()
    }

    private fun setUpWifiScanner() {
        AlarmHelper.startSchedulerAlarm(this)
        wiFiScannerHelper = WiFiScannerHelper(::doOnResults, lifecycle)
        wiFiScannerHelper?.setupWifiManager(applicationContext!!, this)
        wiFiScannerHelper?.startScanner()
    }

    private fun doOnResults(results: List<ScanResult>) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navHostFragment?.childFragmentManager?.fragments?.first()?.let {
            if (it is AccessPointFragment) {
                it.doOnResults(results)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getStoragePermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.disposeTextToSpeech()
        AlarmHelper.stopSchedulerAlarm(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            Navigation.findNavController(this, R.id.nav_host_fragment).popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}
