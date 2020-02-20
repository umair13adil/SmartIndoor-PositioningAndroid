package com.cubivue.inlogic.data.repositories.accessPoint

import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.room.Room

interface AccessPointDataSource {

    fun addRoom(room: Room)

    fun addAccessPoint(accessPoint: AccessPoint)

    fun addAccessPoints(accessPoints: List<AccessPoint>)
}
