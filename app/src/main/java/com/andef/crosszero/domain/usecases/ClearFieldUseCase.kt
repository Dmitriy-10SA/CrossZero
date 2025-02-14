package com.andef.crosszero.domain.usecases

import com.andef.crosszero.domain.repository.GameRepository
import javax.inject.Inject

class ClearFieldUseCase @Inject constructor(
    private val repository: GameRepository
) {
    fun execute() {
        repository.clearField()
    }
}