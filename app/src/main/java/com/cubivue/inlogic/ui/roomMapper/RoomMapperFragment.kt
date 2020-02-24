package com.cubivue.inlogic.ui.roomMapper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cubivue.inlogic.R
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.enums.AccessPointPosition
import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.utils.showToast
import com.embrace.plog.pLogs.PLog
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_room_mapper.*
import java.util.*
import javax.inject.Inject

class RoomMapperFragment : DaggerFragment(),
    AccessPointSelectionDialog.AccessPointSelectionListener {

    private val TAG = "RoomMapperFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RoomMapperViewModel
    private var listOfAccessPoints = arrayListOf<AccessPoint>()
    private val room = Room(roomId = UUID.randomUUID().toString())

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is AccessPointSelectionDialog) {
            fragment.setOnAccessPointSelectionListener(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_room_mapper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RoomMapperViewModel::class.java)

        viewModel.getAccessPoints().observeForever {
            PLog.logThis(TAG, "getAccessPoints","Fetched: ${it.size}")
            listOfAccessPoints.clear()
            listOfAccessPoints.addAll(it)
        }

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

        btn_save_room_info.setOnClickListener {
            if (edit_room_name.text.isNullOrBlank()) {
                showToast("Enter Room Name!")
            } else {
                room.roomName = edit_room_name.text.toString()
                viewModel.saveRoomInfo(room)
            }
        }
    }

    private fun showAccessPointSelectionDialog(position: AccessPointPosition) {
        try {
            fragmentManager?.beginTransaction().let {
                val dialog =
                    AccessPointSelectionDialog.newInstance(position.value, listOfAccessPoints)
                dialog.isCancelable = true
                dialog.show(it!!, "selection-dialog")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onSelected(position: AccessPointPosition, selectedName: String) {
        PLog.logThis(TAG, "onSelected","${position}, Name: ${selectedName}")

        when (position) {
            AccessPointPosition.ACCESS_POINT_TOP_LEFT -> {
                room.accessPointTopLeft = selectedName
                saveAccessPointInfo(
                    select_ap_1,
                    selectedName
                )
            }
            AccessPointPosition.ACCESS_POINT_TOP_RIGHT -> {
                room.accessPointTopRight = selectedName
                saveAccessPointInfo(
                    select_ap_2,
                    selectedName
                )
            }
            AccessPointPosition.ACCESS_POINT_BOTTOM_LEFT -> {
                room.accessPointBottomLeft = selectedName
                saveAccessPointInfo(
                    select_ap_3,
                    selectedName
                )
            }
            AccessPointPosition.ACCESS_POINT_BOTTOM_RIGHT -> {
                room.accessPointBottomRight = selectedName
                saveAccessPointInfo(
                    select_ap_4,
                    selectedName
                )
            }
        }
    }

    private fun saveAccessPointInfo(textView: AppCompatButton, name: String) {
        textView.text = name
    }
}
