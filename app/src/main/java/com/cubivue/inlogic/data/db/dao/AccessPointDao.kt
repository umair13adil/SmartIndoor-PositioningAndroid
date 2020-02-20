package com.cubivue.inlogic.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import io.reactivex.Observable

/**
 * The Data Access Object for the [AccessPoint] class.
 */
@Dao
interface AccessPointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(accessPoint: List<AccessPoint>)

    @Query("SELECT * FROM accessPoint WHERE ssid LIKE :ssid LIMIT 1")
    fun findAccessPointById(ssid: String): AccessPoint

    @Query("SELECT * FROM accessPoint WHERE name LIKE :name LIMIT 1")
    fun findAccessPointByName(name: String): Observable<AccessPoint>

    @Query("SELECT * FROM accessPoint")
    fun getAllAccessPoints(): LiveData<List<AccessPoint>>

    @Delete
    fun delete(ap: AccessPoint)
}