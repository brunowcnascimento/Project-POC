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
    private var mProgress = 1
    private val prefsSwitch by lazy { getSwitchPrefs() }
    private val prefsProgress by lazy { getProgressPrefs() }

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
            textFontSize.text = getProgressIsPositionPrefs().toString()
        }
    }

    private fun setupSwitch() {
        binding?.apply {
            switchFont.apply {
                isChecked = getSwitchIsEnablePrefs()
                setOnClickListener {
                    prefsSwitch.edit()
                        .putBoolean(PREFS_SWITCH_IS_CHECKED, isChecked)
                        .apply()

                    setEnabledSeekBar()
                    getToast("Switch $isChecked").show()
                }
            }
        }


    }

    private fun setEnabledSeekBar() {
        binding?.apply {
            seekbarFont.isEnabled = getSwitchIsEnablePrefs()
        }
    }

    private fun getSwitchIsEnablePrefs() =
        prefsSwitch.getBoolean(PREFS_SWITCH_IS_CHECKED, binding?.switchFont?.isChecked ?: false)

    private fun getProgressIsPositionPrefs() =
        prefsProgress.getInt(PREFS_PROGRESS_IS_POSITION, mProgress)

    private fun setupSeekBar() {
        binding?.apply {
            seekbarFont.progress = getProgressIsPositionPrefs()
            seekbarFont.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    mProgress = progress
                    textFontSize.text = progress.toString()
                    prefsProgress.edit()
                        .putInt(PREFS_PROGRESS_IS_POSITION, progress)
                        .apply()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    getFontSizeByProgress(mProgress)
                }
            })
        }
    }

    private fun getFontSizeByProgress(progress: Int) {
        when(progress) {
            0 -> updateFontSize(FontSize.SMALL)
            1 -> updateFontSize(FontSize.DEFAULT)
            2 -> updateFontSize(FontSize.LARGE)
            3 -> updateFontSize(FontSize.LARGEST)
        }
    }

    private fun updateFontSize(fontSize: FontSize) {
        fontSizeManager?.fontSize = fontSize
        recreate()
    }

    companion object {
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