package com.brunowcnascimento.projectpoc.common

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSizeManager

abstract class CommonFontSizeActivity: AppCompatActivity() {

    var fontSizeManager: FontSizeManager? = null

    override fun attachBaseContext(newBase: Context) {
        fontSizeManager = FontSizeManager(newBase.prefs())
        val safeFontSizeManager = fontSizeManager ?: return

        val newConfig = Configuration(newBase.resources.configuration)
        newConfig.fontScale = safeFontSizeManager.fontSize.scale
        applyOverrideConfiguration(newConfig)
        super.attachBaseContext(newBase)
    }

    private fun Context.prefs(): SharedPreferences = getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
}