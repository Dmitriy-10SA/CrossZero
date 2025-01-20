package com.andef.crosszero.domain.usecases

import com.andef.crosszero.data.repository.GameRepositoryImpl
import com.andef.crosszero.domain.entities.PlayerSign

object MakePutCell {
    fun execute(row: Int, col: Int, currentPlayerSign: PlayerSign): Boolean {
        return GameRepositoryImpl.putCell(row, col, currentPlayerSign)
    }
}