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
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize.UNSET_FONT_SIZE
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize.getFontSizeByPosition

class FontSizeFontSizeActivity : CommonGenericActivity() {

    private var binding: ActivityFontSizeBinding? = null

    private val prefsSwitch by lazy { getSwitchPrefs() }
    private val prefsProgress by lazy { getProgressPrefs() }
    private val prefsFontSizeToDisabledSwitch by lazy { getOldFontSizeSystem() }

    private val switchIsEnabled get() = prefsSwitch.getBoolean(PREFS_SWITCH_IS_CHECKED, false)
    private val currentPosition get() = prefsProgress.getInt(PREFS_PROGRESS_IS_POSITION, POSITION_DEFAULT)
    private val currentFontSize get() = FontSize.fontSizeList.getFontSizeByPosition(currentPosition)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFontSizeBinding.inflate(layoutInflater)
        val safeBinding = binding ?: return
        setContentView(safeBinding.root)

        initialize()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initialize() {
        verifyIfHaveDiff()
        setupSeekBar()
        setupSwitch()
        isEnabledSeekBar()
        setVisibilityText()
        savePrefsFontSize()
    }

    private fun verifyIfHaveDiff() {
        val diff = fontSizeManager?.diff ?: false
        if(diff) {
            binding?.switchFont?.isChecked = false
            getValueToPrefsSwitch(false)
            recreate()
        } else {
            binding?.switchFont?.isChecked = switchIsEnabled
        }
    }

    private fun savePrefsFontSize() {
        val safeFontSize = fontSizeManager?.fontSizeSystem ?: UNSET_FONT_SIZE
        prefsFontSizeToDisabledSwitch.edit()
            .putFloat(PREFS_OLD_FONT_SIZE_SYSTEM, safeFontSize)
            .apply()
    }

    private fun setVisibilityText() {
        binding?.textFontSize?.text = if (switchIsEnabled) {
            currentFontSize.toString()
        } else {
            fontSizeManager?.fontSizeSystem.toString()
        }
    }

    private fun setupSwitch() {
        binding?.apply {
            switchFont.apply {
                isChecked = switchIsEnabled
                setOnClickListener {
                    getValueToPrefsSwitch(isChecked)
                    verifySwitchIsEnabled()
                }
            }
        }
    }

    private fun getValueToPrefsSwitch(isEnabled: Boolean) {
        prefsSwitch.edit()
            .putBoolean(PREFS_SWITCH_IS_CHECKED, isEnabled)
            .apply()
    }

    private fun setupSeekBar() {
        binding?.apply {
            seekbarFont.progress = currentPosition
            seekbarFont.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    prefsProgress.edit()
                        .putInt(PREFS_PROGRESS_IS_POSITION, progress)
                        .apply()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    updateFontSize(currentFontSize)
                }
            })
        }
    }

    private fun isEnabledSeekBar() {
        binding?.seekbarFont?.isEnabled = switchIsEnabled
    }

    private fun verifySwitchIsEnabled() {
        if (!switchIsEnabled) updateFontSize() else updateFontSize(currentFontSize)
    }

    private fun updateFontSize(
        fontSize: Float? = fontSizeManager?.fontSizeSystem ?: FontSize.DEFAULT
    ) {
        val safeFontSize = fontSize ?: FontSize.DEFAULT
        binding?.textFontSize?.text = fontSize.toString()
        fontSizeManager?.fontSize = safeFontSize
        recreate()
    }

    companion object {
        private const val POSITION_DEFAULT = 1
        const val PREFS_SWITCH = "PREFS_SWITCH"
        const val PREFS_SWITCH_IS_CHECKED = "PREFS_SWITCH_IS_CHECKED"

        const val PREFS_PROGRESS = "PREFS_PROGRESS"
        const val PREFS_PROGRESS_IS_POSITION = "PREFS_PROGRESS_IS_POSITION"

        const val PREFS_DIFF_FONT_SIZE_SYSTEM = "PREFS_DIFF_FONT_SIZE_SYSTEM"
        const val PREFS_VALUE_DIFF_FONT_SIZE_SYSTEM = "PREFS_VALUE_DIFF_FONT_SIZE_SYSTEM"

        fun newInstance() = FontSizeFontSizeActivity()
        fun getIntent(context: Context) = Intent(context, FontSizeFontSizeActivity::class.java)
    }

    private fun getSwitchPrefs(): SharedPreferences =
        getSharedPreferences(PREFS_SWITCH, Context.MODE_PRIVATE)

    private fun getProgressPrefs(): SharedPreferences =
        getSharedPreferences(PREFS_PROGRESS, Context.MODE_PRIVATE)

    private fun getOldFontSizeSystem(): SharedPreferences =
        getSharedPreferences(PREFS_FONT_SIZE_SYSTEM, Context.MODE_PRIVATE)
}