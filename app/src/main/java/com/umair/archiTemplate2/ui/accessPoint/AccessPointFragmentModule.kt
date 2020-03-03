package com.umair.archiTemplate2.ui.accessPoint

import androidx.lifecycle.ViewModel
import com.umair.archiTemplate2.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class AccessPointFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccessPointViewModel::class)
    internal abstract fun bindViewModel(viewModel: AccessPointViewModel): ViewModel
}
