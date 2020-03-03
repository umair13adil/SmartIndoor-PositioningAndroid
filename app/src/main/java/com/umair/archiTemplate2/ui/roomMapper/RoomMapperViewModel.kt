package com.umair.archiTemplate2.ui.roomMapper

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.umair.archiTemplate2.data.repositories.room.RoomRepository
import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.room.Room
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
