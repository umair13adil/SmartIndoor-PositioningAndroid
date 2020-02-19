package com.cubivue.inlogic.data.repositories.room

import android.util.Log
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
    private val appDatabase: AppDatabase
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

    fun getAccessPointsList(): List<AccessPoint>? {
        dataSource.getAccessPoints()?.let {
            Log.i(TAG,"getAccessPointsList: Return list ${it.size}")
            return it
        } ?: AppExecutors.instance?.diskIO()?.execute {
            Log.i(TAG,"getAccessPointsList: Fetch from DB..")
            appDatabase.accessPointDao().getAllAccessPoints()
        }

        return null
    }
}