package com.cubivue.inlogic.data.repositories.room

import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.model.room.RoomMapperActivityData


object RoomMapperActivityDataSource : RoomDataSource {

    private var listOfRooms = arrayListOf<Room>()

    override fun addRoom(room: Room) {
        listOfRooms.add(room)
    }

    override fun getRoomsData(): RoomMapperActivityData? {
        return RoomMapperActivityData(listOfRooms)
    }
}