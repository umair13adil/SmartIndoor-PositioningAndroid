package com.cubivue.inlogic.ui.rooms

import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class RoomsActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(RoomsViewModel::class)
    internal abstract fun bindViewModel(viewModel: RoomsViewModel): ViewModel
}
