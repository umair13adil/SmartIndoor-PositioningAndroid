package com.cubivue.inlogic.data.db.dao

import androidx.room.*
import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.model.room.RoomAccessPoints

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

    @Query("SELECT * FROM room WHERE roomId LIKE :roomId LIMIT 1")
    fun findRoomById(roomId: String): Room

    @Delete
    fun delete(room: Room)
}