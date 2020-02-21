package com.cubivue.inlogic.ui.accessPoint

import android.Manifest.permission.*
import android.content.Intent
import android.net.wifi.ScanResult
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_access_points.*
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cubivue.inlogic.R
import com.cubivue.inlogic.components.AlarmHelper
import com.cubivue.inlogic.ui.locate.LocateActivity
import com.cubivue.inlogic.ui.roomMapper.RoomMapperActivity
import com.cubivue.inlogic.ui.rooms.RoomsActivity
import com.cubivue.inlogic.utils.WiFiScannerHelper
import com.cubivue.inlogic.utils.logs.LogsHelper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class AccessPointActivity() : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AccessPointViewModel

    private val TAG = "AccessPointActivity"
    private val MY_PERMISSIONS_REQUEST = 1
    private val STORAGE_PERMISSIONS_REQUEST = 10

    private lateinit var wiFiScannerHelper: WiFiScannerHelper

    //List
    private lateinit var adapter: AccessPointsAdapter
    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AccessPointViewModel::class.java)

        setContentView(R.layout.activity_access_points)

        setUpListAdapter()

        btn_room_mapper.setOnClickListener {
            startActivity(Intent(this, RoomMapperActivity::class.java))
        }

        btn_show_rooms.setOnClickListener {
            startActivity(Intent(this, RoomsActivity::class.java))
        }

        btn_locate_me.setOnClickListener {
            startActivity(Intent(this, LocateActivity::class.java))
        }
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

    private fun doOnResults(results: List<ScanResult>) {

        viewModel.accessPoints.clear()
        adapter.submitList(viewModel.accessPoints)

        val sorted = viewModel.addAccessPointsToList(results)

        adapter.submitList(sorted)
        adapter.notifyDataSetChanged()
    }

    private fun getStoragePermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this,
                    WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
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
                    ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(ACCESS_COARSE_LOCATION),
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

    override fun onResume() {
        super.onResume()
        getStoragePermissions()
    }

    override fun onPause() {
        wiFiScannerHelper.unregisterReceiver(this)
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        AlarmHelper.stopSchedulerAlarm(this)
    }

    private fun setUpWifiScanner(){
        AlarmHelper.startSchedulerAlarm(this)
        wiFiScannerHelper = WiFiScannerHelper(::doOnResults)
        wiFiScannerHelper.setupWifiManager(applicationContext, this)
        wiFiScannerHelper.startScanner()
    }
}
