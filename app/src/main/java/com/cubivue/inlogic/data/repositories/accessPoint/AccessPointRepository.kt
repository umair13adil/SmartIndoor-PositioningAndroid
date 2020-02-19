package com.cubivue.inlogic.data.repositories.accessPoint

import android.util.Log
import com.cubivue.inlogic.data.db.AppDatabase
import com.cubivue.inlogic.data.db.AppExecutors
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.accessPoint.AccessPointActivityData
import com.cubivue.inlogic.model.room.Room
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
open class AccessPointRepository @Inject constructor(
    @Named("accessPointDatasource") private val dataSource: AccessPointActivityDataSource,
    private val appDatabase: AppDatabase
) {

    private val TAG = "AccessPointRepository"

    fun saveRoom(room: Room) {
        AppExecutors.instance?.diskIO()?.execute {
            appDatabase.roomDao().insert(room)
            AccessPointActivityDataSource.addRoom(
                room
            )
        }
    }

    fun saveAccessPoints(accessPoints: List<AccessPoint>) {
        AppExecutors.instance?.diskIO()?.execute {
            Log.i(TAG,"saveAccessPoints: Saving Access Points: ${accessPoints.size}")

            appDatabase.accessPointDao().insertAll(accessPoints)

            AccessPointActivityDataSource.addAccessPoints(
                accessPoints
            )
        }
    }

    fun getMainScreenData(): AccessPointActivityData? {
        return AccessPointActivityDataSource.getAccessPointData()
    }
}