package com.cubivue.inlogic.ui.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.data.repositories.room.RoomRepository
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.model.room.RoomMapperActivityData
import javax.inject.Inject

class RoomMapperViewModel @Inject constructor(
    val context: Context,
    val repository: RoomRepository
) : ViewModel() {

    private val listOfAccessPoints = MutableLiveData<List<AccessPoint>>()

    val accessPoints: LiveData<List<AccessPoint>>
        get() = listOfAccessPoints

    fun addRoom(room: Room) {
        repository.saveRoom(room)
    }

    fun getRoomData(): RoomMapperActivityData? {
        return repository.getRoomsData()
    }

    fun getAccessPoints() {
        repository.getAccessPointsList()?.let {
            listOfAccessPoints.postValue(it)
        }
    }
}
