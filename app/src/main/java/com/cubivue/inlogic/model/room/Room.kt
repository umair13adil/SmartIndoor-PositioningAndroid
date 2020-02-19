package com.cubivue.inlogic.model.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "room", indices = [Index(value = ["roomId"], unique = true)])
data class Room(

    @PrimaryKey
    var roomId: String,
    var roomName: String
)