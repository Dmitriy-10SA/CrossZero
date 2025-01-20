package com.andef.crosszero.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.andef.crosszero.R
import com.andef.crosszero.domain.entities.CellSign

class GameActivity : AppCompatActivity() {
    private lateinit var textViewTicScores: TextView
    private lateinit var textViewTacScores: TextView

    private lateinit var imageViewCell11: ImageView
    private lateinit var imageViewCell12: ImageView
    private lateinit var imageViewCell13: ImageView
    private lateinit var imageViewCell21: ImageView
    private lateinit var imageViewCell22: ImageView
    private lateinit var imageViewCell23: ImageView
    private lateinit var imageViewCell31: ImageView
    private lateinit var imageViewCell32: ImageView
    private lateinit var imageViewCell33: ImageView
    private lateinit var imageViewGameField: ImageView

    private lateinit var buttonStartNewGame: Button
    private lateinit var buttonDeleteScores: Button
    private lateinit var buttonExit: Button

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        initViewModel()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getDrawableForSign(sign: CellSign): Drawable? {
        if (sign.title == CellSign.CROSS.title) {
            return getDrawable(R.drawable.cross)
        } else if (sign.title == CellSign.ZERO.title) {
            return getDrawable(R.drawable.zero)
        }
        return null
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        viewModel.crossScores.observe(this) {
            textViewTicScores.text = "${getString(R.string.tic_score)} $it"
        }
        viewModel.zeroScores.observe(this) {
            textViewTacScores.text = "${getString(R.string.tac_score)} $it"
        }
        viewModel.cell11.observe(this) {
            imageViewCell11.foreground = getDrawableForSign(it)
        }
        viewModel.cell12.observe(this) {
            imageViewCell12.foreground = getDrawableForSign(it)
        }
        viewModel.cell13.observe(this) {
            imageViewCell13.foreground = getDrawableForSign(it)
        }
        viewModel.cell21.observe(this) {
            imageViewCell21.foreground = getDrawableForSign(it)
        }
        viewModel.cell22.observe(this) {
            imageViewCell22.foreground = getDrawableForSign(it)
        }
        viewModel.cell23.observe(this) {
            imageViewCell23.foreground = getDrawableForSign(it)
        }
        viewModel.cell31.observe(this) {
            imageViewCell31.foreground = getDrawableForSign(it)
        }
        viewModel.cell32.observe(this) {
            imageViewCell32.foreground = getDrawableForSign(it)
        }
        viewModel.cell33.observe(this) {
            imageViewCell33.foreground = getDrawableForSign(it)
        }
    }

    private fun initViews() {
        textViewTicScores = findViewById(R.id.textViewTicScores)
        textViewTacScores = findViewById(R.id.textViewTacScores)

        imageViewCell11 = findViewById<ImageView?>(R.id.imageViewCell11).apply {
            setOnClickListener {
                viewModel.putCell(0, 0)
            }
        }
        imageViewCell12 = findViewById<ImageView?>(R.id.imageViewCell12).apply {
            setOnClickListener {
                viewModel.putCell(0, 1)
            }
        }
        imageViewCell13 = findViewById<ImageView?>(R.id.imageViewCell13).apply {
            setOnClickListener {
                viewModel.putCell(0, 2)
            }
        }
        imageViewCell21 = findViewById<ImageView?>(R.id.imageViewCell21).apply {
            setOnClickListener {
                viewModel.putCell(1, 0)
            }
        }
        imageViewCell22 = findViewById<ImageView?>(R.id.imageViewCell22).apply {
            setOnClickListener {
                viewModel.putCell(1, 1)
            }
        }
        imageViewCell23 = findViewById<ImageView?>(R.id.imageViewCell23).apply {
            setOnClickListener {
                viewModel.putCell(1, 2)
            }
        }
        imageViewCell31 = findViewById<ImageView?>(R.id.imageViewCell31).apply {
            setOnClickListener {
                viewModel.putCell(2, 0)
            }
        }
        imageViewCell32 = findViewById<ImageView?>(R.id.imageViewCell32).apply {
            setOnClickListener {
                viewModel.putCell(2, 1)
            }
        }
        imageViewCell33 = findViewById<ImageView?>(R.id.imageViewCell33).apply {
            setOnClickListener {
                viewModel.putCell(2, 2)
            }
        }
        imageViewGameField = findViewById(R.id.imageViewGameField)

        buttonStartNewGame = findViewById<Button?>(R.id.buttonStartNewGame).apply {
            setOnClickListener {
                viewModel.newGame()
            }
        }
        buttonDeleteScores = findViewById<Button?>(R.id.buttonDeleteScores).apply {
            setOnClickListener {
                viewModel.clearScores()
            }
        }
        buttonExit = findViewById<Button?>(R.id.buttonExit).apply {
            setOnClickListener {
                finish()
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }
}