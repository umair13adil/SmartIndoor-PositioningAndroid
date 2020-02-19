package com.cubivue.inlogic.ui.accessPoint

import android.content.Context
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

    fun addRoom(room: Room) {
        repository.saveRoom(room)
    }

    fun addAccessPoints(accessPoints: List<AccessPoint>) {
        repository.saveAccessPoints(accessPoints)
    }

    fun getMainData(): AccessPointActivityData? {
        return repository.getMainScreenData()
    }
}
