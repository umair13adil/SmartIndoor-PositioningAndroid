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

    fun getSavedRooms(): LiveData<List<Room>> {
        return repository.appDatabase.roomDao().getRooms()
    }

    fun deleteRoom(roomId: String) {
        repository.deleteRoom(roomId)
        getSavedRooms()
    }
}
