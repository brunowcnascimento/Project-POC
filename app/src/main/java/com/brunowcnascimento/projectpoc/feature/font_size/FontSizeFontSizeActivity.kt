package com.brunowcnascimento.projectpoc.feature.font_size

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.brunowcnascimento.projectpoc.common.CommonFontSizeActivity
import com.brunowcnascimento.projectpoc.databinding.ActivityFontSizeBinding
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize

class FontSizeFontSizeActivity : CommonFontSizeActivity() {

    private var binding: ActivityFontSizeBinding? = null

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
            buttonSmall.setOnClickListener { updateFontSize(FontSize.SMALL) }
            buttonDefault.setOnClickListener { updateFontSize(FontSize.DEFAULT) }
            buttonLarge.setOnClickListener { updateFontSize(FontSize.LARGE) }
        }
    }

//    override fun attachBaseContext(newBase: Context) {
//        fontSizeManager = FontSizeManager(newBase.prefs())
//        val newConfig = Configuration(newBase.resources.configuration)
//        newConfig.fontScale = fontSizeManager.fontSize.scale
//        applyOverrideConfiguration(newConfig)
//        super.attachBaseContext(newBase)
//    }

    private fun updateFontSize(fontSize: FontSize) {
        fontSizeManager?.fontSize = fontSize
        recreate()
    }

    companion object {
        fun newInstance() = FontSizeFontSizeActivity()
        fun getIntent(context: Context) = Intent(context, FontSizeFontSizeActivity::class.java)
    }


}