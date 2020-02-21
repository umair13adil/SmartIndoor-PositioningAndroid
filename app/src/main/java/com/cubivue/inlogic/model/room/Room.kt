package com.cubivue.inlogic.model.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "room", indices = [Index(value = ["roomId"], unique = true)])
data class Room(

    @PrimaryKey
    var roomId: String,
    var roomName: String = "",
    var accessPointTopLeft: String = "",
    var accessPointTopRight: String = "",
    var accessPointBottomLeft: String = "",
    var accessPointBottomRight: String = "",
    var inHere: Boolean = false,
    var assessment: String = "",
    var timeArrived: Long = 0L,
    var timeLeft: Long = 0L,
    var noOfArrivals: Int = 0,
    var totalTimeStayed: Long = 0L
)