package com.andef.crosszero.di

import androidx.lifecycle.ViewModel
import com.andef.crosszero.presentation.ui.GameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    @Binds
    fun bindGameViewModel(impl: GameViewModel): ViewModel
}