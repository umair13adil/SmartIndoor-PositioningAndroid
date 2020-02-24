package com.cubivue.inlogic.ui.main

import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindViewModel(viewModel: MainViewModel): ViewModel
}
