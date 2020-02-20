package com.cubivue.inlogic.ui.rooms

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.data.repositories.room.RoomRepository
import com.cubivue.inlogic.model.room.Room
import javax.inject.Inject

class RoomsViewModel @Inject constructor(
    val context: Context,
    val repository: RoomRepository
) : ViewModel() {

    private val TAG = "RoomsViewModel"

    private val listOfRooms = MutableLiveData<List<Room>>()

    val rooms: LiveData<List<Room>>
        get() = listOfRooms

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
}
