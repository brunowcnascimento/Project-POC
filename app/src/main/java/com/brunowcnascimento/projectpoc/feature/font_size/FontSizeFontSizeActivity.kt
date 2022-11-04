package com.brunowcnascimento.projectpoc.feature.font_size

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.brunowcnascimento.projectpoc.utils.common.CommonGenericActivity
import com.brunowcnascimento.projectpoc.databinding.ActivityFontSizeBinding
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize

class FontSizeFontSizeActivity : CommonGenericActivity() {

    private var binding: ActivityFontSizeBinding? = null
    private var mProgress = 1

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
            textFontSize.text = mProgress.toString()
            switchFont.setOnClickListener {
                getToast("Switch ${switchFont.isChecked}").show()
            }
        }
    }

    private fun setupSeekBar() {
        binding?.apply {
            seekbarFont.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                    mProgress = progress
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
        fun newInstance() = FontSizeFontSizeActivity()
        fun getIntent(context: Context) = Intent(context, FontSizeFontSizeActivity::class.java)
    }
}