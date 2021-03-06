package com.umair.archiTemplate2.data.repositories.room

import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.room.Room


object RoomMapperActivityDataSource : RoomDataSource {

    private var listOfAccessPoint = arrayListOf<AccessPoint>()
    private var listOfRooms = arrayListOf<Room>()

    override fun addRoom(room: Room) {
        listOfRooms.add(room)
    }

    override fun getAccessPoints(): List<AccessPoint> {
        return listOfAccessPoint
    }

    override fun addAccessPoints(accessPoints: List<AccessPoint>) {
        listOfAccessPoint.clear()
        listOfAccessPoint.addAll(accessPoints)
    }

    override fun addRooms(rooms: List<Room>) {
        listOfRooms.clear()
        this.listOfRooms.addAll(rooms)
    }

    override fun getRoomsData(): List<Room>? {
        return listOfRooms
    }

    override fun deleteRoom(roomId: String) {
        listOfRooms.filter {
            it.roomId == roomId
        }.let {
            if (it.isNotEmpty()) {
                listOfRooms.removeAll(it)
            }
        }
    }
}