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

package com.cubivue.inlogic

import com.cubivue.inlogic.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Initialization of libraries.
 */
class MainApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * Tell Dagger which [AndroidInjector] to use - in our case
     * [com.google.samples.apps.iosched.di.AppComponent]. `DaggerAppComponent`
     * is a class generated by Dagger based on the `AppComponent` class.
     */
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}