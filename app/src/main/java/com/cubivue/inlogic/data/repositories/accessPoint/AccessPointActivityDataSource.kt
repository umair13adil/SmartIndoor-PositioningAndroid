package com.cubivue.inlogic.data.repositories.accessPoint

import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.room.Room


object AccessPointActivityDataSource :
    AccessPointDataSource {

    private var listOfRooms = arrayListOf<Room>()
    private var listOfAccessPoints = arrayListOf<AccessPoint>()

    override fun addRoom(room: Room) {
        listOfRooms.add(room)
    }

    override fun addAccessPoint(accessPoint: AccessPoint) {
        listOfAccessPoints.add(accessPoint)
    }

    override fun addAccessPoints(accessPoints: List<AccessPoint>) {
        listOfAccessPoints.addAll(accessPoints)
    }
}