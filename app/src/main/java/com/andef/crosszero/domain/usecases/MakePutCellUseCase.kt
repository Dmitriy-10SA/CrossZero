package com.andef.crosszero.domain.usecases

import com.andef.crosszero.domain.entities.PlayerSign
import com.andef.crosszero.domain.repository.GameRepository
import javax.inject.Inject

class MakePutCellUseCase @Inject constructor(
    private val repository: GameRepository
) {
    fun execute(row: Int, col: Int, currentPlayerSign: PlayerSign): Boolean {
        return repository.putCell(row, col, currentPlayerSign)
    }
}