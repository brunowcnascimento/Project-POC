package com.brunowcnascimento.projectpoc.feature.font_size

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.brunowcnascimento.projectpoc.utils.common.CommonGenericActivity
import com.brunowcnascimento.projectpoc.databinding.ActivityFontSizeBinding
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize

class FontSizeFontSizeActivity : CommonGenericActivity() {

    private var binding: ActivityFontSizeBinding? = null

    private val prefsSwitch by lazy { getSwitchPrefs() }
    private val prefsProgress by lazy { getProgressPrefs() }

    private val switchIsEnabled get() = prefsSwitch.getBoolean(PREFS_SWITCH_IS_CHECKED, false)
    private val progressIsPosition get() = prefsProgress.getInt(PREFS_PROGRESS_IS_POSITION, POSITION_DEFAULT)

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFontSizeBinding.inflate(layoutInflater)
        val safeBinding = binding ?: return
        setContentView(safeBinding.root)

        initalize()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initalize() {
        binding?.apply {
            setupSeekBar()
            setupSwitch()
            setEnabledSeekBar()
            setVisibilityText()
        }
    }

    private fun setVisibilityText() {
        binding?.textFontSize?.text = if(switchIsEnabled) {
                getFontSize(progressIsPosition).toString()
            } else {
                fontSizeManager?.fontSizeSystem.toString()
            }
    }

    private fun setupSwitch() {
        binding?.apply {
            switchFont.apply {
                isChecked = switchIsEnabled
                setOnClickListener {
                    prefsSwitch.edit()
                        .putBoolean(PREFS_SWITCH_IS_CHECKED, isChecked)
                        .apply()

                    setEnabledSeekBar()
                    if(!isChecked) updateFontSizeSystem() else getFontSizeByProgress(progressIsPosition)
                    getToast("Switch $isChecked").show()
                }

            }
        }
    }

    private fun setEnabledSeekBar() {
        binding?.apply {
            seekbarFont.isEnabled = switchIsEnabled
        }
    }

    private fun setupSeekBar() {
        binding?.apply {
            seekbarFont.progress = progressIsPosition
            seekbarFont.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    prefsProgress.edit()
                        .putInt(PREFS_PROGRESS_IS_POSITION, progress)
                        .apply()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    getFontSizeByProgress(progressIsPosition)
                }
            })
        }
    }

    private fun getFontSize(progress: Int): Float {
        return when(progress) {
            0 -> 0.7f
            1 -> 1.0f
            2 -> 1.3f
            3 -> 1.6f
            else -> 1.0f
        }
    }

    private fun getFontSizeByProgress(progress: Int) {
        updateFontSize(getFontSize(progress))
    }

    private fun updateFontSize(fontSize: Float) {
        binding?.textFontSize?.text = fontSize.toString()
        fontSizeManager?.fontSize = fontSize
        recreate()
    }

    private fun updateFontSizeSystem() {
        val fontSize = fontSizeManager?.fontSizeSystem ?: 1.0f
        binding?.textFontSize?.text = fontSize.toString()
        fontSizeManager?.fontSize = fontSize
        recreate()
    }


    private fun getSwitchIsEnablePrefs() =
        prefsSwitch.getBoolean(PREFS_SWITCH_IS_CHECKED, false)

    private fun getProgressIsPositionPrefs() =
        prefsProgress.getInt(PREFS_PROGRESS_IS_POSITION, POSITION_DEFAULT)


    companion object {
        private const val POSITION_DEFAULT = 1
        const val PREFS_SWITCH = "PREFS_SWITCH"
        const val PREFS_SWITCH_IS_CHECKED = "PREFS_SWITCH_IS_CHECKED"
        const val PREFS_PROGRESS = "PREFS_PROGRESS"
        const val PREFS_PROGRESS_IS_POSITION = "PREFS_PROGRESS_IS_POSITION"

        fun newInstance() = FontSizeFontSizeActivity()
        fun getIntent(context: Context) = Intent(context, FontSizeFontSizeActivity::class.java)
    }

    private fun getSwitchPrefs(): SharedPreferences = getSharedPreferences(PREFS_SWITCH, Context.MODE_PRIVATE)
    private fun getProgressPrefs(): SharedPreferences = getSharedPreferences(PREFS_PROGRESS, Context.MODE_PRIVATE)
}