package com.cubivue.inlogic.data.repositories.room

import com.cubivue.inlogic.data.db.AppDatabase
import com.cubivue.inlogic.data.db.AppExecutors
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

    fun saveRoom(room: Room) {
        AppExecutors.instance?.diskIO()?.execute {
            appDatabase.roomDao().insert(room)
            dataSource.addRoom(room)
        }
    }

    fun getRoomsData(): RoomMapperActivityData? {
        return dataSource.getRoomsData()
    }
}