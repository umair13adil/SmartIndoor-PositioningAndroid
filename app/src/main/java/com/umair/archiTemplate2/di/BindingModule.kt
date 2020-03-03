/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.umair.archiTemplate2.di

import com.umair.archiTemplate2.components.ScannerService
import com.umair.archiTemplate2.ui.accessPoint.AccessPointFragment
import com.umair.archiTemplate2.ui.accessPoint.AccessPointFragmentModule
import com.umair.archiTemplate2.ui.locate.LocateFragment
import com.umair.archiTemplate2.ui.locate.LocateModule
import com.umair.archiTemplate2.ui.main.MainActivity
import com.umair.archiTemplate2.ui.main.MainActivityModule
import com.umair.archiTemplate2.ui.roomMapper.RoomMapperFragment
import com.umair.archiTemplate2.ui.roomMapper.RoomMapperModule
import com.umair.archiTemplate2.ui.rooms.RoomsFragment
import com.umair.archiTemplate2.ui.rooms.RoomsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
abstract class BindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity

    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            AccessPointFragmentModule::class
        ]
    )
    internal abstract fun contributeAccessPointFragment(): AccessPointFragment

    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            RoomMapperModule::class
        ]
    )
    internal abstract fun roomMapperActivity(): RoomMapperFragment

    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            RoomsModule::class
        ]
    )
    internal abstract fun roomsActivity(): RoomsFragment

    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            LocateModule::class
        ]
    )
    internal abstract fun locateActivity(): LocateFragment


    @ContributesAndroidInjector
    abstract fun scannerService(): ScannerService
}
