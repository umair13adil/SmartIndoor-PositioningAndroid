package com.cubivue.inlogic.data.repositories.room

import android.util.Log
import com.cubivue.inlogic.data.db.AppDatabase
import com.cubivue.inlogic.data.db.AppExecutors
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.room.Room
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
            Log.i(TAG, "saveRoom: Saving room.. ${room.toString()}")
            appDatabase.roomDao().insert(room)
            dataSource.addRoom(room)
        }
    }

    fun saveAccessPoints(accessPoints: List<AccessPoint>) {
        dataSource.addAccessPoints(accessPoints)
    }

    fun getAccessPointsList(): List<AccessPoint> {
        Log.i(TAG, "getAccessPointsList: Fetching from cache")
        return dataSource.getAccessPoints()
    }

    fun getSavedRooms(): List<Room>? {
        return dataSource.getRoomsData()
    }

    fun deleteRoom(roomId:String){
        dataSource.deleteRoom(roomId)

        AppExecutors.instance?.diskIO()?.execute {
            Log.i(TAG, "deleteRoom: Deleting room.. $roomId")
            appDatabase.roomDao().delete(roomId)
        }
    }
}