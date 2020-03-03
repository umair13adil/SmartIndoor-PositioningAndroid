package com.umair.archiTemplate2.ui.rooms

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umair.archiTemplate2.data.repositories.room.RoomRepository
import com.umair.archiTemplate2.model.room.Room
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
