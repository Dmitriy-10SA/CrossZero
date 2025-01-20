package com.andef.crosszero.presentation.state

import com.andef.crosszero.domain.entities.Player

data class GameState(
    val crossPlayer: Player,
    val zeroPlayer: Player,
    var currentPlayer: Player,
    var isGameOver: Boolean
)
