package com.andef.crosszero.di

import android.app.Application
import com.andef.crosszero.presentation.ui.GameActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [GameRepositoryModule::class, ViewModelModule::class])
interface CrossZeroComponent {
    fun inject(activity: GameActivity)

    @Component.Factory
    interface CrossZeroFactory {
        fun create(@BindsInstance application: Application): CrossZeroComponent
    }
}