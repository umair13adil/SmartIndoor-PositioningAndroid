package com.cubivue.inlogic.data.repositories.room

import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.model.room.RoomMapperActivityData

interface RoomDataSource {
    fun getRoomsData(): RoomMapperActivityData?

    fun addRoom(room: Room)
}
