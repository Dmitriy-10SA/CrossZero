package com.andef.crosszero.presentation.ui

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.andef.crosszero.R

class MainActivity : AppCompatActivity() {
    private lateinit var buttonStartGame: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonStartGame = findViewById<Button?>(R.id.buttonStartGame).apply {
            setOnClickListener {
                goToGameScreen()
            }
        }
    }

    private fun goToGameScreen() {
        val intent = GameActivity.newIntent(this)
        val animation = AnimationUtils.loadAnimation(this, R.anim.start_game_buttom_anim)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(intent)
                finish()
            }
        })
        buttonStartGame.startAnimation(animation)
    }
}