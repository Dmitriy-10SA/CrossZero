package com.andef.crosszero.domain.usecases

import com.andef.crosszero.domain.repository.GameRepository
import javax.inject.Inject

class CheckWinnerUseCase @Inject constructor(
    private val repository: GameRepository
) {
    fun execute(): List<Int> {
        return repository.checkWinner()
    }
}