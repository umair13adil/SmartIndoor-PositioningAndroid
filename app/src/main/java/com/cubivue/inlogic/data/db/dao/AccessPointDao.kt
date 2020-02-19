package com.cubivue.inlogic.data.db.dao

import androidx.room.*
import com.cubivue.inlogic.model.accessPoint.AccessPoint

/**
 * The Data Access Object for the [AccessPoint] class.
 */
@Dao
interface AccessPointDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(accessPoint: List<AccessPoint>)

    @Query("SELECT * FROM accessPoint WHERE id LIKE :id LIMIT 1")
    fun findAccessPointById(id: String): AccessPoint

    @Query("SELECT * FROM accessPoint")
    fun getAllAccessPoints(): List<AccessPoint>

    @Delete
    fun delete(ap: AccessPoint)
}