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

    fun getSavedRooms() {
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

    fun getAccessPoints(): LiveData<List<AccessPoint>> {
        return repository.appDatabase.accessPointDao().getAllAccessPoints()
    }

    fun locateMe() {

    }
}
