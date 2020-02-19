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

package com.cubivue.inlogic.di

import com.cubivue.inlogic.ui.accessPoint.AccessPointActivity
import com.cubivue.inlogic.ui.accessPoint.AccessPointActivityModule
import com.cubivue.inlogic.ui.room.RoomMapperActivity
import com.cubivue.inlogic.ui.room.RoomMapperActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module
 * ActivityBindingModule is on, in our case that will be [AppComponent]. You never
 * need to tell [AppComponent] that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that [AppComponent] exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the
 * specified modules and be aware of a scope annotation [@ActivityScoped].
 * When Dagger.Android annotation processor runs it will create 2 subcomponents for us.
 */
@Module
@Suppress("UNUSED")
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // activity
            AccessPointActivityModule::class
        ]
    )
    internal abstract fun mainActivity(): AccessPointActivity

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // activity
            RoomMapperActivityModule::class
        ]
    )
    internal abstract fun roomMapperActivity(): RoomMapperActivity
}
