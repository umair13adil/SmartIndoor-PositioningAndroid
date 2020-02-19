package com.cubivue.inlogic.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class Room(

    @PrimaryKey
    var roomId: String,
    var roomName: String
)