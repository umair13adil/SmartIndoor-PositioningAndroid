package com.umair.archiTemplate2.data.db

import android.content.Context
import androidx.room.Database
import com.umair.archiTemplate2.data.db.dao.AccessPointDao
import com.umair.archiTemplate2.data.db.dao.RoomDao
import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.room.Room

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
        private const val databaseName = "archiTemplate2-db"

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