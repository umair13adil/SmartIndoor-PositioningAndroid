package com.cubivue.inlogic.data.repositories.room

import androidx.lifecycle.LiveData
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.model.room.RoomMapperActivityData

interface RoomDataSource {
    fun getRoomsData(): RoomMapperActivityData?

    fun addRoom(room: Room)

    fun getAccessPoints(): List<AccessPoint>

    fun addAccessPoints(accessPoints: List<AccessPoint>)
}
