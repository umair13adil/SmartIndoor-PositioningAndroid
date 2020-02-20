package com.cubivue.inlogic.data.repositories.room

import androidx.lifecycle.LiveData
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.room.Room

interface RoomDataSource {
    fun getRoomsData(): List<Room>?

    fun addRoom(room: Room)

    fun getAccessPoints(): List<AccessPoint>

    fun addAccessPoints(accessPoints: List<AccessPoint>)

    fun addRooms(rooms: List<Room>)

    fun deleteRoom(roomId:String)
}
