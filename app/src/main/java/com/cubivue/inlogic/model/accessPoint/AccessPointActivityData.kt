package com.cubivue.inlogic.model.accessPoint

import com.cubivue.inlogic.model.room.Room

data class AccessPointActivityData(
    val rooms: ArrayList<Room>,
    val accessPoints: ArrayList<AccessPoint>
)