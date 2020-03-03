package com.umair.archiTemplate2.ui.accessPoint

import android.content.Context
import android.net.wifi.ScanResult
import androidx.lifecycle.ViewModel
import com.umair.archiTemplate2.data.repositories.accessPoint.AccessPointRepository
import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.room.Room
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

    private fun saveAccessPoints(accessPoints: List<AccessPoint>) {
        repository.saveAccessPoints(accessPoints)
    }

    private fun addAccessPointInfo(res: ScanResult) {
        accessPoints.add(
            AccessPoint(
                ssid = res.BSSID,
                name = res.SSID,
                strength = res.level,
                scanTime = res.timestamp
            )
        )
    }

    fun addAccessPointsToList(results: List<ScanResult>): List<AccessPoint> {

        results.forEach { res ->
            //PLog.logThis(TAG, res.toString())
            addAccessPointInfo(res)
        }

        //Send list to RecyclerView
        val sorted = accessPoints.sortedByDescending {
            it.strength
        }

        saveAccessPoints(sorted)

        return sorted
    }
}
