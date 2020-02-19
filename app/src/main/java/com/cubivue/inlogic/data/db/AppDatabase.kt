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
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : androidx.room.RoomDatabase() {
    abstract fun roomDao(): RoomDao

    abstract fun accessPointDao(): AccessPointDao

    companion object {
        private const val databaseName = "inlogic-db"

        fun buildDatabase(context: Context): AppDatabase {
            // Since Room is only used for FTS, destructive migration is enough because the tables
            // are cleared every time the app launches.
            // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
            return androidx.room.Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}