package com.umair.archiTemplate2.ui.roomMapper

import androidx.lifecycle.ViewModel
import com.umair.archiTemplate2.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class RoomMapperModule {

    @Binds
    @IntoMap
    @ViewModelKey(RoomMapperViewModel::class)
    internal abstract fun bindViewModel(viewModel: RoomMapperViewModel): ViewModel
}
