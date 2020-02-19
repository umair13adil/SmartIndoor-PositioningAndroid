package com.cubivue.inlogic.ui.room

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cubivue.inlogic.R
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.enums.AccessPointPosition
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_room_mapper.*
import javax.inject.Inject

class RoomMapperActivity : DaggerAppCompatActivity(),
    AccessPointSelectionDialog.AccessPointSelectionListener {

    private val TAG = "RoomMapperActivity"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RoomMapperViewModel
    private var listOfAccessPoints = arrayListOf<AccessPoint>()

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is AccessPointSelectionDialog) {
            fragment.setOnAccessPointSelectionListener(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RoomMapperViewModel::class.java)

        setContentView(R.layout.activity_room_mapper)

        viewModel.getAccessPoints()

        viewModel.accessPoints.observe(this, Observer {
            Log.i(TAG, "Fetched: ${it.size}")
            listOfAccessPoints.clear()
            listOfAccessPoints.addAll(it)
        })

        select_ap_1.setOnClickListener {
            showAccessPointSelectionDialog(AccessPointPosition.ACCESS_POINT_TOP_LEFT)
        }

        select_ap_2.setOnClickListener {
            showAccessPointSelectionDialog(AccessPointPosition.ACCESS_POINT_TOP_RIGHT)
        }

        select_ap_3.setOnClickListener {
            showAccessPointSelectionDialog(AccessPointPosition.ACCESS_POINT_BOTTOM_LEFT)
        }

        select_ap_4.setOnClickListener {
            showAccessPointSelectionDialog(AccessPointPosition.ACCESS_POINT_BOTTOM_RIGHT)
        }
    }

    private fun showAccessPointSelectionDialog(position: AccessPointPosition) {
        try {
            supportFragmentManager.beginTransaction().let {
                val dialog =
                    AccessPointSelectionDialog.newInstance(position.value, listOfAccessPoints)
                dialog.isCancelable = true
                dialog.show(it, "selection-dialog")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onSelected(position: AccessPointPosition, selectedName: String) {
        Log.i(TAG, "onSelected: ${position}, Name: ${selectedName}")
    }
}
