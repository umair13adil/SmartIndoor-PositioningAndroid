package com.cubivue.inlogic.ui.accessPoint

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.os.Build
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cubivue.inlogic.R
import com.cubivue.inlogic.ui.room.RoomMapperActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class AccessPointActivity() : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AccessPointViewModel

    private val TAG = "AccessPointActivity"
    private val MY_PERMISSIONS_REQUEST = 1
    lateinit var wifiManager: WifiManager

    // Create the Handler object (on the main thread by default)
    val handler = Handler()

    //List
    private val accessPoints = arrayListOf<AccessPoint>()
    private lateinit var adapter: AccessPointsAdapter
    private lateinit var layoutManager: LinearLayoutManager

    val wifiScanReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION, false)
            if (success) {
                scanSuccess()
            } else {
                scanFailure()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AccessPointViewModel::class.java)

        setContentView(R.layout.activity_main)

        setUpListAdapter()

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        getWifi()

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        registerReceiver(wifiScanReceiver, intentFilter)

        btn_room_mapper.setOnClickListener {
            startActivity(Intent(this, RoomMapperActivity::class.java))
        }
    }

    private fun scanSuccess() {
        val results = wifiManager.scanResults
        doOnResults(results)
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        doOnResults(results)
    }

    private fun doOnResults(results: List<ScanResult>) {

        accessPoints.clear()
        adapter.submitList(accessPoints)

        results.forEach { res ->
            Log.i(TAG, res.toString())
            addAccessPointInfo(res)
        }

        //Send list to RecyclerView
        val sorted = accessPoints.sortedByDescending {
            it.strength
        }
        adapter.submitList(sorted)
        adapter.notifyDataSetChanged()
    }


    private fun addAccessPointInfo(res: ScanResult) {
        accessPoints.add(
            AccessPoint(
                id = res.BSSID,
                name = res.SSID,
                strength = res.level,
                scanTime = res.timestamp
            )
        )
    }

    /*
     * Setup RecyclerView list adapter.
     */
    private fun setUpListAdapter() {
        adapter = AccessPointsAdapter()

        layoutManager = LinearLayoutManager(this)

        list_access_points.layoutManager = layoutManager
        list_access_points.adapter = adapter
    }

    private fun getWifi() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST
                )

            } else {
                startScanner()
            }

        } else {
            startScanner()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        if (requestCode == MY_PERMISSIONS_REQUEST) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanner()
            } else {
                return
            }
        }
    }

    private fun startScanner() {
        val runnableCode = object : Runnable {
            override fun run() {
                wifiManager.startScan()
                handler.postDelayed(this, 5000)
            }
        }
        handler.post(runnableCode)
    }
}
