package com.cubivue.inlogic.ui.accessPoint

import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class AccessPointActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccessPointViewModel::class)
    internal abstract fun bindViewModel(viewModel: AccessPointViewModel): ViewModel
}
