package com.andef.crosszero.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andef.crosszero.domain.entities.CellSign
import com.andef.crosszero.domain.entities.Player
import com.andef.crosszero.domain.entities.PlayerSign
import com.andef.crosszero.domain.usecases.CheckWinner
import com.andef.crosszero.domain.usecases.ClearField
import com.andef.crosszero.domain.usecases.MakePutCell
import com.andef.crosszero.presentation.state.GameState

class GameViewModel : ViewModel() {
    private val gameState = getGameState()
    private fun getGameState(): GameState {
        val crossPlayer = Player(PlayerSign.CROSS, 0)
        val zeroPlayer = Player(PlayerSign.ZERO, 0)
        return GameState(crossPlayer, zeroPlayer, crossPlayer, false)
    }

    private fun checkWinner(): List<Int> {
        return CheckWinner.execute()
    }

    private fun changeCurrentPlayer() {
        if (gameState.currentPlayer == gameState.crossPlayer) {
            gameState.currentPlayer = gameState.zeroPlayer
        } else {
            gameState.currentPlayer = gameState.crossPlayer
        }
    }

    private fun getCellSignByPlayerSign(currentPlayerSign: PlayerSign): CellSign {
        if (currentPlayerSign.title == PlayerSign.ZERO.title) {
            return CellSign.ZERO
        }
        return CellSign.CROSS
    }

    private fun notifyCell(row: Int, col: Int, sign: CellSign) {
        if (row == 0 && col == 0) {
            cell11.value = sign
        } else if (row == 0 && col == 1) {
            cell12.value = sign
        } else if (row == 0 && col == 2) {
            cell13.value = sign
        } else if (row == 1 && col == 0) {
            cell21.value = sign
        } else if (row == 1 && col == 1) {
            cell22.value = sign
        } else if (row == 1 && col == 2) {
            cell23.value = sign
        } else if (row == 2 && col == 0) {
            cell31.value = sign
        } else if (row == 2 && col == 1) {
            cell32.value = sign
        } else {
            cell33.value = sign
        }
    }

    private fun notifyAllCells() {
        cell11.value = CellSign.EMPTY
        cell12.value = CellSign.EMPTY
        cell13.value = CellSign.EMPTY
        cell21.value = CellSign.EMPTY
        cell22.value = CellSign.EMPTY
        cell23.value = CellSign.EMPTY
        cell31.value = CellSign.EMPTY
        cell32.value = CellSign.EMPTY
        cell33.value = CellSign.EMPTY
    }

    private fun addScores() {
        gameState.currentPlayer.cntWins++
        if (gameState.currentPlayer == gameState.zeroPlayer) {
            zeroScores.value = gameState.currentPlayer.cntWins
        } else {
            crossScores.value = gameState.currentPlayer.cntWins
        }
    }

    val crossScores = MutableLiveData<Int>()
    val zeroScores = MutableLiveData<Int>()

    val cell11 = MutableLiveData<CellSign>()
    val cell12 = MutableLiveData<CellSign>()
    val cell13 = MutableLiveData<CellSign>()
    val cell21 = MutableLiveData<CellSign>()
    val cell22 = MutableLiveData<CellSign>()
    val cell23 = MutableLiveData<CellSign>()
    val cell31 = MutableLiveData<CellSign>()
    val cell32 = MutableLiveData<CellSign>()
    val cell33 = MutableLiveData<CellSign>()

    fun putCell(row: Int, col: Int) {
        if (MakePutCell.execute(row, col, gameState.currentPlayer.playerSign) &&
            !gameState.isGameOver
        ) {
            val winCells = checkWinner()
            if (winCells.isNotEmpty()) {
                addScores()
                gameState.isGameOver = true
            }
            notifyCell(row, col, getCellSignByPlayerSign(gameState.currentPlayer.playerSign))
            changeCurrentPlayer()
        }
    }

    fun newGame() {
        ClearField.execute()
        notifyAllCells()
        gameState.currentPlayer = gameState.crossPlayer
        gameState.isGameOver = false
    }

    fun clearScores() {
        gameState.crossPlayer.cntWins = 0
        gameState.zeroPlayer.cntWins = 0
        crossScores.value = 0
        zeroScores.value = 0
    }
}