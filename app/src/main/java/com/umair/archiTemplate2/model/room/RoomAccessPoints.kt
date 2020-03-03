package com.umair.archiTemplate2.model.room

import androidx.room.Embedded
import androidx.room.Relation
import com.umair.archiTemplate2.model.accessPoint.AccessPoint

data class RoomAccessPoints(
    @Embedded val room: Room,
    @Relation(
        parentColumn = "roomId",
        entityColumn = "ssid"
    )
    val accessPoints: List<AccessPoint>
)