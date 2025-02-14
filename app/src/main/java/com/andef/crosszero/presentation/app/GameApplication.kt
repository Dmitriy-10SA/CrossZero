package com.andef.crosszero.presentation.app

import android.app.Application
import com.andef.crosszero.di.DaggerCrossZeroComponent

class GameApplication : Application() {
    val component by lazy {
        DaggerCrossZeroComponent.factory().create(this)
    }
}