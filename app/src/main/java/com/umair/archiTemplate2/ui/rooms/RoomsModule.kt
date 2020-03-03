package com.umair.archiTemplate2.ui.rooms

import androidx.lifecycle.ViewModel
import com.umair.archiTemplate2.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class RoomsModule {

    @Binds
    @IntoMap
    @ViewModelKey(RoomsViewModel::class)
    internal abstract fun bindViewModel(viewModel: RoomsViewModel): ViewModel
}
