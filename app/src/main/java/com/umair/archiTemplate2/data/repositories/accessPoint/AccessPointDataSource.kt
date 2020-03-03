package com.umair.archiTemplate2.data.repositories.accessPoint

import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.room.Room

interface AccessPointDataSource {

    fun addRoom(room: Room)

    fun addAccessPoint(accessPoint: AccessPoint)

    fun addAccessPoints(accessPoints: List<AccessPoint>)
}
