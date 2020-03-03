package com.umair.archiTemplate2.model.accessPoint

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "accessPoint", indices = [Index(value = ["ssid"], unique = true)])
data class AccessPoint(

    @PrimaryKey
    var ssid: String = "",
    var name: String = "",
    var strength: Int = 0,
    var distance: String = "",
    var scanTime: Long = 0L
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ssid)
        parcel.writeString(name)
        parcel.writeInt(strength)
        parcel.writeString(distance)
        parcel.writeLong(scanTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccessPoint> {
        override fun createFromParcel(parcel: Parcel): AccessPoint {
            return AccessPoint(parcel)
        }

        override fun newArray(size: Int): Array<AccessPoint?> {
            return arrayOfNulls(size)
        }
    }

}