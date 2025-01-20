package com.andef.crosszero.domain.usecases

import com.andef.crosszero.data.repository.GameRepositoryImpl

object CheckWinner {
    fun execute(): List<Int> {
        return GameRepositoryImpl.checkWinner()
    }
}