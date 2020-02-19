package com.cubivue.inlogic.data.repositories.room

import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.accessPoint.AccessPointActivityData
import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.model.room.RoomMapperActivityData


object RoomMapperActivityDataSource : RoomDataSource {

    private var listOfAccessPoint = arrayListOf<AccessPoint>()
    private var listOfRooms = arrayListOf<Room>()

    override fun addRoom(room: Room) {
        listOfRooms.add(room)
    }

    override fun getRoomsData(): RoomMapperActivityData? {
        return RoomMapperActivityData(listOfRooms)
    }

    override fun getAccessPoints(): List<AccessPoint>? {
        if (listOfAccessPoint.isNotEmpty()) {
            return listOfAccessPoint
        } else {
            return null
        }
    }

    override fun addAccessPoints(accessPoints: List<AccessPoint>) {
        listOfAccessPoint.addAll(accessPoints)
    }
}