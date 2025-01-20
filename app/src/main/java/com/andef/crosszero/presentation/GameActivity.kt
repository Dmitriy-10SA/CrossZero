package com.andef.crosszero.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.andef.crosszero.R

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
    }

    private fun initViews() {
        textViewTicScores = findViewById(R.id.textViewTicScores)
        textViewTacScores = findViewById(R.id.textViewTacScores)

        imageViewCell11 = findViewById(R.id.imageViewCell11)
        imageViewCell12 = findViewById(R.id.imageViewCell12)
        imageViewCell13 = findViewById(R.id.imageViewCell13)
        imageViewCell21 = findViewById(R.id.imageViewCell21)
        imageViewCell22 = findViewById(R.id.imageViewCell22)
        imageViewCell23 = findViewById(R.id.imageViewCell23)
        imageViewCell31 = findViewById(R.id.imageViewCell31)
        imageViewCell32 = findViewById(R.id.imageViewCell32)
        imageViewCell33 = findViewById(R.id.imageViewCell33)
        imageViewGameField = findViewById(R.id.imageViewGameField)

        buttonStartNewGame = findViewById(R.id.buttonStartNewGame)
        buttonDeleteScores = findViewById(R.id.buttonDeleteScores)
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