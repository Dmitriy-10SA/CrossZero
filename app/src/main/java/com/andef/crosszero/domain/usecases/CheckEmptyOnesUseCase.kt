package com.andef.crosszero.domain.usecases

import com.andef.crosszero.domain.repository.GameRepository
import javax.inject.Inject

class CheckEmptyOnesUseCase @Inject constructor(
    private val repository: GameRepository
) {
    fun execute(): Boolean {
        return repository.checkEmptyOnes()
    }
}