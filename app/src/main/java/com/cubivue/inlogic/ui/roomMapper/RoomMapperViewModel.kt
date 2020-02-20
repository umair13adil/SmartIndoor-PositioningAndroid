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

    private val listOfAccessPoints = MutableLiveData<List<AccessPoint>>()

    val accessPoints: LiveData<List<AccessPoint>>
        get() = listOfAccessPoints

    fun addRoom(room: Room) {
        repository.saveRoom(room)
    }

    fun addAccessPoints(accessPoints: List<AccessPoint>) {
        repository.saveAccessPoints(accessPoints)
    }

    fun getAccessPoints() {
        val savedAccessPoints = repository.getAccessPointsList()

        if (savedAccessPoints.isNullOrEmpty()) {
            Log.i(TAG, "getAccessPointsList: Fetching from db")
            repository.appDatabase.accessPointDao().getAllAccessPoints().observeForever {
                Log.i(TAG, "getAccessPointsList: Fetched from db: ${it.size}")
                repository.saveAccessPoints(it)
                listOfAccessPoints.postValue(it)
            }
        } else {
            listOfAccessPoints.postValue(savedAccessPoints)
        }
    }

    fun saveRoomInfo(room: Room){
        repository.saveRoom(room)
    }
}
