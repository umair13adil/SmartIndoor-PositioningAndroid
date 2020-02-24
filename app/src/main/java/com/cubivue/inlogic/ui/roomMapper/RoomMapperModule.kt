package com.cubivue.inlogic.ui.roomMapper

import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.di.ViewModelKey
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
