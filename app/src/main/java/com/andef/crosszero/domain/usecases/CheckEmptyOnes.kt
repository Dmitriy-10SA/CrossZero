package com.andef.crosszero.domain.usecases

import com.andef.crosszero.data.repository.GameRepositoryImpl

object CheckEmptyOnes {
    fun execute(): Boolean {
        return GameRepositoryImpl.checkEmptyOnes()
    }
}