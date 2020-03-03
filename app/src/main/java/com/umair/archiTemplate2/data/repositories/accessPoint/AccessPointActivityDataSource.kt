package com.umair.archiTemplate2.data.repositories.accessPoint

import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.room.Room


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