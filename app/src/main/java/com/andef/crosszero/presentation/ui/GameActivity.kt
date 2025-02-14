package com.andef.crosszero.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.andef.crosszero.R
import com.andef.crosszero.databinding.ActivityGameBinding
import com.andef.crosszero.domain.entities.CellBackgroundColor
import com.andef.crosszero.domain.entities.CellSign
import com.andef.crosszero.presentation.app.GameApplication
import com.andef.crosszero.presentation.factory.ViewModelFactory
import javax.inject.Inject

class GameActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }
    private val component by lazy {
        (application as GameApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getBackgroundDrawableForCell(
        cellBackgroundColor: CellBackgroundColor,
        leftTopCorner: Boolean = false,
        leftBottomCorner: Boolean = false,
        rightTopCorner: Boolean = false,
        rightBottomCorner: Boolean = false
    ): Drawable? {
        if (cellBackgroundColor.title == CellBackgroundColor.WHITE.title) {
            if (leftBottomCorner) {
                return getDrawable(R.drawable.white_bottom_left)
            } else if (leftTopCorner) {
                return getDrawable(R.drawable.white_top_left)
            } else if (rightTopCorner) {
                return getDrawable(R.drawable.white_top_right)
            } else if (rightBottomCorner) {
                return getDrawable(R.drawable.white_bottom_right)
            }
            return getDrawable(R.color.white)
        } else if (cellBackgroundColor.title == CellBackgroundColor.RED.title) {
            if (leftBottomCorner) {
                return getDrawable(R.drawable.red_bottom_left)
            } else if (leftTopCorner) {
                return getDrawable(R.drawable.red_top_left)
            } else if (rightTopCorner) {
                return getDrawable(R.drawable.red_top_right)
            } else if (rightBottomCorner) {
                return getDrawable(R.drawable.red_bottom_right)
            }
            return getDrawable(android.R.color.holo_red_light)
        }
        if (leftBottomCorner) {
            return getDrawable(R.drawable.green_bottom_left)
        } else if (leftTopCorner) {
            return getDrawable(R.drawable.green_top_left)
        } else if (rightTopCorner) {
            return getDrawable(R.drawable.green_top_right)
        } else if (rightBottomCorner) {
            return getDrawable(R.drawable.green_bottom_right)
        }
        return getDrawable(android.R.color.holo_green_light)
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        viewModel.crossScores.observe(this) {
            binding.textViewTicScores.text = "${getString(R.string.tic_score)} $it"
        }
        viewModel.zeroScores.observe(this) {
            binding.textViewTacScores.text = "${getString(R.string.tac_score)} $it"
        }
        viewModel.cell11.observe(this) {
            binding.imageViewCell11.foreground = getDrawableForSign(it)
        }
        viewModel.cell12.observe(this) {
            binding.imageViewCell12.foreground = getDrawableForSign(it)
        }
        viewModel.cell13.observe(this) {
            binding.imageViewCell13.foreground = getDrawableForSign(it)
        }
        viewModel.cell21.observe(this) {
            binding.imageViewCell21.foreground = getDrawableForSign(it)
        }
        viewModel.cell22.observe(this) {
            binding.imageViewCell22.foreground = getDrawableForSign(it)
        }
        viewModel.cell23.observe(this) {
            binding.imageViewCell23.foreground = getDrawableForSign(it)
        }
        viewModel.cell31.observe(this) {
            binding.imageViewCell31.foreground = getDrawableForSign(it)
        }
        viewModel.cell32.observe(this) {
            binding.imageViewCell32.foreground = getDrawableForSign(it)
        }
        viewModel.cell33.observe(this) {
            binding.imageViewCell33.foreground = getDrawableForSign(it)
        }
        viewModel.backgroundCell11.observe(this) {
            binding.imageViewCell11.background =
                getBackgroundDrawableForCell(it, leftTopCorner = true)
        }
        viewModel.backgroundCell12.observe(this) {
            binding.imageViewCell12.background = getBackgroundDrawableForCell(it)
        }
        viewModel.backgroundCell13.observe(this) {
            binding.imageViewCell13.background =
                getBackgroundDrawableForCell(it, rightTopCorner = true)
        }
        viewModel.backgroundCell21.observe(this) {
            binding.imageViewCell21.background = getBackgroundDrawableForCell(it)
        }
        viewModel.backgroundCell22.observe(this) {
            binding.imageViewCell22.background = getBackgroundDrawableForCell(it)
        }
        viewModel.backgroundCell23.observe(this) {
            binding.imageViewCell23.background = getBackgroundDrawableForCell(it)
        }
        viewModel.backgroundCell31.observe(this) {
            binding.imageViewCell31.background =
                getBackgroundDrawableForCell(it, leftBottomCorner = true)
        }
        viewModel.backgroundCell32.observe(this) {
            binding.imageViewCell32.background = getBackgroundDrawableForCell(it)
        }
        viewModel.backgroundCell33.observe(this) {
            binding.imageViewCell33.background =
                getBackgroundDrawableForCell(it, rightBottomCorner = true)
        }
        viewModel.fieldAnimation.observe(this) {
            shakeFieldAnim()
        }
        viewModel.startButtonAnimation.observe(this) {
            shakeNewGameButtonAnim()
        }
    }

    private fun shakeFieldAnim() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.shake_field_anim)
        binding.constraintLayoutWithGameField.startAnimation(animation)
    }

    private fun shakeNewGameButtonAnim() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.shake_anim_new_game_button)
        binding.buttonStartNewGame.startAnimation(animation)
    }

    private fun initViews() {
        binding.imageViewCell11.setOnClickListener { viewModel.putCell(0, 0) }
        binding.imageViewCell12.setOnClickListener { viewModel.putCell(0, 1) }
        binding.imageViewCell13.setOnClickListener { viewModel.putCell(0, 2) }
        binding.imageViewCell21.setOnClickListener { viewModel.putCell(1, 0) }
        binding.imageViewCell22.setOnClickListener { viewModel.putCell(1, 1) }
        binding.imageViewCell23.setOnClickListener { viewModel.putCell(1, 2) }
        binding.imageViewCell31.setOnClickListener { viewModel.putCell(2, 0) }
        binding.imageViewCell32.setOnClickListener { viewModel.putCell(2, 1) }
        binding.imageViewCell33.setOnClickListener { viewModel.putCell(2, 2) }

        binding.buttonStartNewGame.setOnClickListener {
            viewModel.newGame()
            binding.buttonStartNewGame.startAnimation(touchButtonAnim())
        }
        binding.buttonDeleteScores.setOnClickListener {
            viewModel.clearScores()
            binding.buttonDeleteScores.startAnimation(touchButtonAnim())
        }
        binding.buttonExit.setOnClickListener {
            binding.buttonExit.startAnimation(touchButtonAnim())
            finish()
        }
    }

    private fun touchButtonAnim(): Animation? {
        return AnimationUtils.loadAnimation(this, R.anim.touch_button_anim)
    }

    override fun onStop() {
        viewModel.exitGame()
        super.onStop()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }
}