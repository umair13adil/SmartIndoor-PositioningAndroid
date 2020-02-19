package com.cubivue.inlogic.ui.room

import android.content.Context
import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.data.repositories.room.RoomRepository
import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.model.room.RoomMapperActivityData
import javax.inject.Inject

class RoomMapperViewModel @Inject constructor(
    val context: Context,
    val repository: RoomRepository
) : ViewModel() {

    fun addRoom(room: Room) {
        repository.saveRoom(room)
    }

    fun getRoomData(): RoomMapperActivityData? {
        return repository.getRoomsData()
    }
}
