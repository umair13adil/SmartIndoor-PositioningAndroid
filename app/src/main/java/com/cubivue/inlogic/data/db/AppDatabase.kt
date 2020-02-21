package com.cubivue.inlogic.data.db

import android.content.Context
import androidx.room.Database
import com.cubivue.inlogic.data.db.dao.AccessPointDao
import com.cubivue.inlogic.data.db.dao.RoomDao
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.room.Room

/**
 * The [Room] database for this app.
 */
@Database(
    entities = arrayOf(
        Room::class,
        AccessPoint::class
    ),
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : androidx.room.RoomDatabase() {
    abstract fun roomDao(): RoomDao

    abstract fun accessPointDao(): AccessPointDao

    companion object {
        private const val databaseName = "inlogic-db"

        fun buildDatabase(context: Context): AppDatabase {
            return androidx.room.Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                databaseName
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}