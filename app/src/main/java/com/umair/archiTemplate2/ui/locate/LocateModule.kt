package com.umair.archiTemplate2.ui.locate

import androidx.lifecycle.ViewModel
import com.umair.archiTemplate2.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class LocateModule {

    @Binds
    @IntoMap
    @ViewModelKey(LocateViewModel::class)
    internal abstract fun bindViewModel(viewModel: LocateViewModel): ViewModel
}
