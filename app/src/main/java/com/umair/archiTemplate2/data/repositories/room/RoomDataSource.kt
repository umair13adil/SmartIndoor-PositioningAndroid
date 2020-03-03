package com.umair.archiTemplate2.data.repositories.room

import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.room.Room

interface RoomDataSource {
    fun getRoomsData(): List<Room>?

    fun addRoom(room: Room)

    fun getAccessPoints(): List<AccessPoint>

    fun addAccessPoints(accessPoints: List<AccessPoint>)

    fun addRooms(rooms: List<Room>)

    fun deleteRoom(roomId:String)
}
