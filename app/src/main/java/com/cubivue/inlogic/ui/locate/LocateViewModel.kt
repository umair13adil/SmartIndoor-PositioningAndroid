package com.cubivue.inlogic.ui.locate

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.data.repositories.room.RoomRepository
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.room.Room
import javax.inject.Inject

class LocateViewModel @Inject constructor(
    val context: Context,
    val repository: RoomRepository
) : ViewModel() {

    private val TAG = "LocateViewModel"

    private val listOfRooms = MutableLiveData<List<Room>>()

    val rooms: LiveData<List<Room>>
        get() = listOfRooms

    private val listOfAccessPoints = MutableLiveData<List<AccessPoint>>()

    val accessPoints: LiveData<List<AccessPoint>>
        get() = listOfAccessPoints

    fun getSavedRooms(){
        val savedRooms = repository.getSavedRooms()

        if (savedRooms.isNullOrEmpty()) {
            Log.i(TAG, "getSavedRooms: Fetching from db")
            repository.appDatabase.roomDao().getRooms().observeForever {
                Log.i(TAG, "getSavedRooms: Fetched from db: ${it.size}")
                repository.saveRooms(it)
                listOfRooms.postValue(it)
            }
        } else {
            listOfRooms.postValue(savedRooms)
        }
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

    fun locateMe(){

    }
}
