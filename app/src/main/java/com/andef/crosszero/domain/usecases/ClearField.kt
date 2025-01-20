package com.andef.crosszero.domain.usecases

import com.andef.crosszero.data.repository.GameRepositoryImpl

object ClearField {
    fun execute() {
        GameRepositoryImpl.clearField()
    }
}