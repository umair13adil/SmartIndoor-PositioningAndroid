package com.cubivue.inlogic.ui.accessPoint

import android.content.Context
import android.net.wifi.ScanResult
import android.util.Log
import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.data.repositories.accessPoint.AccessPointRepository
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.accessPoint.AccessPointActivityData
import com.cubivue.inlogic.model.room.Room
import javax.inject.Inject

class AccessPointViewModel @Inject constructor(
    val context: Context,
    val repository: AccessPointRepository
) : ViewModel() {

    private val TAG = "AccessPointViewModel"

    val accessPoints = arrayListOf<AccessPoint>()

    fun addRoom(room: Room) {
        repository.saveRoom(room)
    }

    fun addAccessPoints(accessPoints: List<AccessPoint>) {
        repository.saveAccessPoints(accessPoints)
    }

    fun getMainData(): AccessPointActivityData? {
        return repository.getMainScreenData()
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

    fun addAccessPointsToList(results: List<ScanResult>): List<AccessPoint> {

        results.forEach { res ->
            Log.i(TAG, res.toString())
            addAccessPointInfo(res)
        }

        //Send list to RecyclerView
        val sorted = accessPoints.sortedByDescending {
            it.strength
        }

        addAccessPoints(sorted)

        return sorted
    }
}
