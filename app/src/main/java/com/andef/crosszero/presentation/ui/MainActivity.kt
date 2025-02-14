package com.andef.crosszero.presentation.ui

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.andef.crosszero.R
import com.andef.crosszero.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.buttonStartGame.setOnClickListener { goToGameScreen() }
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
        binding.buttonStartGame.startAnimation(animation)
    }
}