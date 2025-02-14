package com.andef.crosszero.di

import com.andef.crosszero.data.repository.GameRepositoryImpl
import com.andef.crosszero.domain.repository.GameRepository
import dagger.Binds
import dagger.Module

@Module
interface GameRepositoryModule {
    @Binds
    fun bindGameRepository(impl: GameRepositoryImpl): GameRepository
}