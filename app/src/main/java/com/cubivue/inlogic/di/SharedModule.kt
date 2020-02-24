package com.cubivue.inlogic.di

import android.content.Context
import com.cubivue.inlogic.data.db.AppDatabase
import com.cubivue.inlogic.data.repositories.accessPoint.AccessPointActivityDataSource
import com.cubivue.inlogic.data.repositories.accessPoint.AccessPointRepository
import com.cubivue.inlogic.data.repositories.room.RoomMapperActivityDataSource
import com.cubivue.inlogic.data.repositories.room.RoomRepository
import com.cubivue.inlogic.utils.InDoorLocationHelper
import com.cubivue.inlogic.utils.TTSHelper
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class SharedModule {

    @Singleton
    @Provides
    @Named("accessPointDatasource")
    fun provideAccessPointSource(): AccessPointActivityDataSource {
        return AccessPointActivityDataSource
    }

    @Singleton
    @Provides
    fun provideAccessPointRepository(
        @Named("accessPointDatasource") dataSource: AccessPointActivityDataSource,
        appDatabase: AppDatabase
    ): AccessPointRepository {
        return AccessPointRepository(
            dataSource,
            appDatabase
        )
    }

    @Singleton
    @Provides
    @Named("roomDatasource")
    fun provideRoomSource(): RoomMapperActivityDataSource {
        return RoomMapperActivityDataSource
    }

    @Singleton
    @Provides
    fun provideRoomRepository(
        @Named("roomDatasource") dataSource: RoomMapperActivityDataSource,
        appDatabase: AppDatabase
    ): RoomRepository {
        return RoomRepository(
            dataSource,
            appDatabase
        )
    }

    @Singleton
    @Provides
    fun provideTTSHelper(context: Context): TTSHelper {
        return TTSHelper(context)
    }

    @Singleton
    @Provides
    fun provideInDoorLocationHelper(appDatabase: AppDatabase, textToSpeech: TTSHelper): InDoorLocationHelper {
        return InDoorLocationHelper(appDatabase, textToSpeech)
    }
}