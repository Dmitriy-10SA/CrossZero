package com.andef.crosszero.domain.repository

import com.andef.crosszero.domain.entities.PlayerSign

interface GameRepository {
    fun putCell(row: Int, col: Int, currentPlayerSign: PlayerSign): Boolean
    fun checkWinner(): List<Int>
    fun clearField()
    fun checkEmptyOnes(): Boolean
}