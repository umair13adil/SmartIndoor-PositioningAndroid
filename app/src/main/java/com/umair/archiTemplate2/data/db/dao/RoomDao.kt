package com.umair.archiTemplate2.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.umair.archiTemplate2.model.room.Room
import com.umair.archiTemplate2.model.room.RoomAccessPoints

/**
 * The Data Access Object for the [Room] class.
 */
@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rooms: List<Room>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rooms: Room)

    @Transaction
    @Query("SELECT * FROM room")
    fun getRoomsAndAccessPoints(): List<RoomAccessPoints>

    @Query("SELECT * FROM room")
    fun getRooms(): LiveData<List<Room>>

    @Query("SELECT * FROM room")
    fun getSavedRooms(): List<Room>

    @Query("SELECT * FROM room WHERE roomId LIKE :roomId LIMIT 1")
    fun findRoomById(roomId: String): Room

    @Query("DELETE FROM room WHERE roomId = :roomId")
    fun delete(roomId: String)
}