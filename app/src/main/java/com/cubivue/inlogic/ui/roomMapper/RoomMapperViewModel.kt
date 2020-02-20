package com.cubivue.inlogic.ui.roomMapper

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.data.repositories.room.RoomRepository
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.room.Room
import javax.inject.Inject

class RoomMapperViewModel @Inject constructor(
    val context: Context,
    val repository: RoomRepository
) : ViewModel() {

    private val TAG = "RoomMapperViewModel"

    fun addRoom(room: Room) {
        repository.saveRoom(room)
    }

    private fun addAccessPoints(accessPoints: List<AccessPoint>) {
        repository.saveAccessPoints(accessPoints)
    }

    fun getAccessPoints(): LiveData<List<AccessPoint>> {
        return repository.appDatabase.accessPointDao().getAllAccessPoints()
    }

    fun saveRoomInfo(room: Room) {
        repository.saveRoom(room)
    }
}
