package com.cubivue.inlogic.data.repositories.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.cubivue.inlogic.data.db.AppDatabase
import com.cubivue.inlogic.data.db.AppExecutors
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.model.room.RoomMapperActivityData
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
open class RoomRepository @Inject constructor(
    @Named("roomDatasource") private val dataSource: RoomMapperActivityDataSource,
    val appDatabase: AppDatabase
) {

    private val TAG = "RoomRepository"

    fun saveRoom(room: Room) {
        AppExecutors.instance?.diskIO()?.execute {
            appDatabase.roomDao().insert(room)
            dataSource.addRoom(room)
        }
    }

    fun getRoomsData(): RoomMapperActivityData? {
        return dataSource.getRoomsData()
    }

    fun saveAccessPoints(accessPoints: List<AccessPoint>) {
        dataSource.addAccessPoints(accessPoints)
    }

    fun getAccessPointsList(): List<AccessPoint> {
        Log.i(TAG, "getAccessPointsList: Fetching from cache")
        return dataSource.getAccessPoints()
    }
}