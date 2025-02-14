package com.andef.crosszero.presentation.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.andef.crosszero.domain.entities.CellBackgroundColor
import com.andef.crosszero.domain.entities.CellSign
import com.andef.crosszero.domain.entities.Player
import com.andef.crosszero.domain.entities.PlayerSign
import com.andef.crosszero.domain.usecases.CheckEmptyOnesUseCase
import com.andef.crosszero.domain.usecases.CheckWinnerUseCase
import com.andef.crosszero.domain.usecases.ClearFieldUseCase
import com.andef.crosszero.domain.usecases.MakePutCellUseCase
import com.andef.crosszero.presentation.state.GameState
import javax.inject.Inject

class GameViewModel @Inject constructor(
    application: Application,
    private val checkWinnerUseCase: CheckWinnerUseCase,
    private val checkEmptyOnesUseCase: CheckEmptyOnesUseCase,
    private val makePutCellUseCase: MakePutCellUseCase,
    private val clearFieldUseCase: ClearFieldUseCase
) : AndroidViewModel(application) {
    private val settings = getApplication<Application>()
        .getSharedPreferences(PREFS_FILE_SCORES, Context.MODE_PRIVATE)

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

    val backgroundCell11 = MutableLiveData<CellBackgroundColor>()
    val backgroundCell12 = MutableLiveData<CellBackgroundColor>()
    val backgroundCell13 = MutableLiveData<CellBackgroundColor>()
    val backgroundCell21 = MutableLiveData<CellBackgroundColor>()
    val backgroundCell22 = MutableLiveData<CellBackgroundColor>()
    val backgroundCell23 = MutableLiveData<CellBackgroundColor>()
    val backgroundCell31 = MutableLiveData<CellBackgroundColor>()
    val backgroundCell32 = MutableLiveData<CellBackgroundColor>()
    val backgroundCell33 = MutableLiveData<CellBackgroundColor>()

    val fieldAnimation = MutableLiveData<Boolean>()
    val startButtonAnimation = MutableLiveData<Boolean>()

    private val gameState = getGameState()
    private fun getGameState(): GameState {
        val crossPlayer = Player(PlayerSign.CROSS, settings.getInt(PREF_CROSS_SCORE, 0))
        crossScores.value = crossPlayer.cntWins
        val zeroPlayer = Player(PlayerSign.ZERO, settings.getInt(PREF_ZERO_SCORE, 0))
        zeroScores.value = zeroPlayer.cntWins
        return GameState(crossPlayer, zeroPlayer, crossPlayer, false)
    }

    private fun checkWinner(): List<Int> {
        return checkWinnerUseCase.execute()
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

    private fun notifyWinCells(winCells: List<Int>) {
        when (winCells) {
            listOf(0, 0, 0, 1, 0, 2) -> { //1
                backgroundCell11.value = CellBackgroundColor.GREEN
                backgroundCell12.value = CellBackgroundColor.GREEN
                backgroundCell13.value = CellBackgroundColor.GREEN
            }

            listOf(0, 0, 1, 0, 2, 0) -> { //2
                backgroundCell11.value = CellBackgroundColor.GREEN
                backgroundCell21.value = CellBackgroundColor.GREEN
                backgroundCell31.value = CellBackgroundColor.GREEN
            }

            listOf(0, 0, 1, 1, 2, 2) -> { //3
                backgroundCell11.value = CellBackgroundColor.GREEN
                backgroundCell22.value = CellBackgroundColor.GREEN
                backgroundCell33.value = CellBackgroundColor.GREEN
            }

            listOf(0, 1, 1, 1, 2, 1) -> { //4
                backgroundCell12.value = CellBackgroundColor.GREEN
                backgroundCell22.value = CellBackgroundColor.GREEN
                backgroundCell32.value = CellBackgroundColor.GREEN
            }

            listOf(0, 2, 1, 2, 2, 2) -> { //5
                backgroundCell13.value = CellBackgroundColor.GREEN
                backgroundCell23.value = CellBackgroundColor.GREEN
                backgroundCell33.value = CellBackgroundColor.GREEN
            }

            listOf(2, 0, 1, 1, 0, 2) -> { //6
                backgroundCell31.value = CellBackgroundColor.GREEN
                backgroundCell22.value = CellBackgroundColor.GREEN
                backgroundCell13.value = CellBackgroundColor.GREEN
            }

            listOf(1, 0, 1, 1, 1, 2) -> { //7
                backgroundCell21.value = CellBackgroundColor.GREEN
                backgroundCell22.value = CellBackgroundColor.GREEN
                backgroundCell23.value = CellBackgroundColor.GREEN
            }

            listOf(2, 0, 2, 1, 2, 2) -> { //8
                backgroundCell31.value = CellBackgroundColor.GREEN
                backgroundCell32.value = CellBackgroundColor.GREEN
                backgroundCell33.value = CellBackgroundColor.GREEN
            }
        }
    }

    private fun notifyAllCells(newGame: Boolean) {
        if (newGame) {
            cell11.value = CellSign.EMPTY
            cell12.value = CellSign.EMPTY
            cell13.value = CellSign.EMPTY
            cell21.value = CellSign.EMPTY
            cell22.value = CellSign.EMPTY
            cell23.value = CellSign.EMPTY
            cell31.value = CellSign.EMPTY
            cell32.value = CellSign.EMPTY
            cell33.value = CellSign.EMPTY
            backgroundCell11.value = CellBackgroundColor.WHITE
            backgroundCell12.value = CellBackgroundColor.WHITE
            backgroundCell13.value = CellBackgroundColor.WHITE
            backgroundCell21.value = CellBackgroundColor.WHITE
            backgroundCell22.value = CellBackgroundColor.WHITE
            backgroundCell23.value = CellBackgroundColor.WHITE
            backgroundCell31.value = CellBackgroundColor.WHITE
            backgroundCell32.value = CellBackgroundColor.WHITE
            backgroundCell33.value = CellBackgroundColor.WHITE
        } else {
            backgroundCell11.value = CellBackgroundColor.RED
            backgroundCell12.value = CellBackgroundColor.RED
            backgroundCell13.value = CellBackgroundColor.RED
            backgroundCell21.value = CellBackgroundColor.RED
            backgroundCell22.value = CellBackgroundColor.RED
            backgroundCell23.value = CellBackgroundColor.RED
            backgroundCell31.value = CellBackgroundColor.RED
            backgroundCell32.value = CellBackgroundColor.RED
            backgroundCell33.value = CellBackgroundColor.RED
        }
    }

    private fun addScores() {
        gameState.currentPlayer.cntWins++
        if (gameState.currentPlayer == gameState.zeroPlayer) {
            zeroScores.value = gameState.currentPlayer.cntWins
        } else {
            crossScores.value = gameState.currentPlayer.cntWins
        }
    }

    private fun endGame() {
        gameState.isGameOver = true
        fieldAnimation.value = true
        startButtonAnimation.value = true
    }

    fun exitGame() {
        val prefEditor = settings.edit()
        prefEditor.putInt(PREF_CROSS_SCORE, gameState.crossPlayer.cntWins)
        prefEditor.putInt(PREF_ZERO_SCORE, gameState.zeroPlayer.cntWins)
        prefEditor.apply()
    }

    fun putCell(row: Int, col: Int) {
        if (makePutCellUseCase.execute(row, col, gameState.currentPlayer.playerSign) &&
            !gameState.isGameOver
        ) {
            notifyCell(row, col, getCellSignByPlayerSign(gameState.currentPlayer.playerSign))
            val winCells = checkWinner()
            if (winCells.isNotEmpty()) {
                notifyWinCells(winCells)
                addScores()
                endGame()
            } else if (!checkEmptyOnesUseCase.execute()) {
                notifyAllCells(false)
                endGame()
            }
            changeCurrentPlayer()
        }
    }

    fun newGame() {
        clearFieldUseCase.execute()
        notifyAllCells(true)
        gameState.currentPlayer = gameState.crossPlayer
        gameState.isGameOver = false
    }

    fun clearScores() {
        gameState.crossPlayer.cntWins = 0
        gameState.zeroPlayer.cntWins = 0
        crossScores.value = gameState.crossPlayer.cntWins
        zeroScores.value = gameState.zeroPlayer.cntWins
    }

    init {
        newGame()
    }

    companion object {
        private const val PREFS_FILE_SCORES = "SCORES"
        private const val PREF_CROSS_SCORE = "CROSS_SCORE"
        private const val PREF_ZERO_SCORE = "ZERO_SCORE"
    }
}