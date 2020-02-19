package com.cubivue.inlogic.model.room

import androidx.room.Embedded
import androidx.room.Relation
import com.cubivue.inlogic.model.accessPoint.AccessPoint

data class RoomAccessPoints(
    @Embedded val room: Room,
    @Relation(
        parentColumn = "roomId",
        entityColumn = "id"
    )
    val accessPoints: List<AccessPoint>
)