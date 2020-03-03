package com.umair.archiTemplate2.data.repositories.accessPoint

import com.umair.archiTemplate2.data.db.AppDatabase
import com.umair.archiTemplate2.data.db.AppExecutors
import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.room.Room
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

            appDatabase.accessPointDao().insertAll(accessPoints)

            AccessPointActivityDataSource.addAccessPoints(
                accessPoints
            )
        }
    }
}