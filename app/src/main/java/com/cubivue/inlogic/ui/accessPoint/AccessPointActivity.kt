package com.cubivue.inlogic.ui.accessPoint

import android.content.Intent
import android.net.wifi.ScanResult
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_access_points.*
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cubivue.inlogic.R
import com.cubivue.inlogic.ui.roomMapper.RoomMapperActivity
import com.cubivue.inlogic.ui.rooms.RoomsActivity
import com.cubivue.inlogic.utils.WiFiScannerHelper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class AccessPointActivity() : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AccessPointViewModel

    private val TAG = "AccessPointActivity"
    private val MY_PERMISSIONS_REQUEST = 1

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

        wiFiScannerHelper = WiFiScannerHelper(::doOnResults)
        wiFiScannerHelper.setupWifiManager(applicationContext, this)

        getWifiPermissions()

        btn_room_mapper.setOnClickListener {
            startActivity(Intent(this, RoomMapperActivity::class.java))
        }

        btn_show_rooms.setOnClickListener {
            startActivity(Intent(this, RoomsActivity::class.java))
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
                wiFiScannerHelper.startScanner()
            }

        } else {
            wiFiScannerHelper.startScanner()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        if (requestCode == MY_PERMISSIONS_REQUEST) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                wiFiScannerHelper.startScanner()
            } else {
                return
            }
        }
    }


}
