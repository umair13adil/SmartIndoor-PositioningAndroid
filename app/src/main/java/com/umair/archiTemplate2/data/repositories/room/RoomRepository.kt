package com.umair.archiTemplate2.data.repositories.room

import com.umair.archiTemplate2.data.db.AppDatabase
import com.umair.archiTemplate2.data.db.AppExecutors
import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.room.Room
import com.blackbox.plog.pLogs.PLog
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
open class RoomRepository @Inject constructor(
    @Named("roomDatasource") private val dataSource: RoomMapperActivityDataSource,
    val appDatabase: AppDatabase
) {

    private val TAG = "RoomRepository"

    fun saveRooms(rooms: List<Room>) {
        dataSource.addRooms(rooms)
    }

    fun saveRoom(room: Room) {
        AppExecutors.instance?.diskIO()?.execute {
            PLog.logThis(TAG, "saveRoom","Saving room.. ${room.toString()}")
            appDatabase.roomDao().insert(room)
            dataSource.addRoom(room)
        }
    }

    fun saveAccessPoints(accessPoints: List<AccessPoint>) {
        dataSource.addAccessPoints(accessPoints)
    }

    fun getAccessPointsList(): List<AccessPoint> {
        PLog.logThis(TAG, "getAccessPointsList","Fetching from cache")
        return dataSource.getAccessPoints()
    }

    fun getSavedRooms(): List<Room>? {
        return dataSource.getRoomsData()
    }

    fun deleteRoom(roomId:String){
        dataSource.deleteRoom(roomId)

        AppExecutors.instance?.diskIO()?.execute {
            PLog.logThis(TAG, "deleteRoom","Deleting room.. $roomId")
            appDatabase.roomDao().delete(roomId)
        }
    }
}