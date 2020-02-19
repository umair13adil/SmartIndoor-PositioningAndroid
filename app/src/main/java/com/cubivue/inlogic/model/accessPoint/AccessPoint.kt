package com.cubivue.inlogic.model.accessPoint

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accessPoint")
data class AccessPoint(

    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var strength: Int = 0,
    var distance: String = "",
    var scanTime: Long = 0L
)