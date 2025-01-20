package com.andef.crosszero.data.repository

import com.andef.crosszero.data.datasource.FieldData
import com.andef.crosszero.domain.entities.CellSign
import com.andef.crosszero.domain.entities.Player
import com.andef.crosszero.domain.entities.PlayerSign
import com.andef.crosszero.domain.repository.GameRepository

object GameRepositoryImpl: GameRepository {
    private var field = FieldData.newClearField.cells

    private fun getCellSignByPlayerSign(currentPlayerSign: PlayerSign): CellSign {
        if (currentPlayerSign.title == PlayerSign.ZERO.title) {
            return CellSign.ZERO
        }
        return CellSign.CROSS
    }

    private fun isWinnerCombination(
        row0: Int,
        col0: Int,
        row1: Int,
        col1: Int,
        row2: Int,
        col2: Int
    ): Boolean {
        return field[row0][col0] == field[row1][col1] && field[row1][col1] == field[row2][col2]
                && field[row0][col0] != CellSign.EMPTY && field[row1][col1] != CellSign.EMPTY
                && field[row2][col2] != CellSign.EMPTY
    }

    override fun putCell(row: Int, col: Int, currentPlayerSign: PlayerSign): Boolean {
        if (field[row][col].title == CellSign.EMPTY.title) {
            field[row][col] = getCellSignByPlayerSign(currentPlayerSign)
            return true
        }
        return false
    }

    override fun checkWinner(): List<Int> {
        if (isWinnerCombination(0,0,0,1,0,2)) { //1
            return listOf(0,0,0,1,0,2)
        } else if (isWinnerCombination(0,0,1,0,2,0)) { //2
            return listOf(0,0,1,0,2,0)
        } else if (isWinnerCombination(0,0,1,1,2,2)) { //3
            return listOf(0,0,1,1,2,2)
        } else if (isWinnerCombination(0,1,1,1,2,1)) { //4
            return listOf(0,1,1,1,2,1)
        } else if (isWinnerCombination(0,2,1,2,2,2)) { //5
            return listOf(0,2,1,2,2,2)
        } else if (isWinnerCombination(2,0,1,1,0,2)) { //6
            return listOf(2,0,1,1,0,2)
        } else if (isWinnerCombination(1,0,1,1,1,2)) { //7
            return listOf(1,0,1,1,1,2)
        } else if (isWinnerCombination(2,0,2,1,2,2)) { //8
            return listOf(2,0,2,1,2,2)
        }
        return listOf()
    }

    override fun clearField() {
        field = FieldData.newClearField.cells
    }
}