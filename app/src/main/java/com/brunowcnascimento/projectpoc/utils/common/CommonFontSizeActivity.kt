package com.brunowcnascimento.projectpoc.utils.common

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSizeManager

abstract class CommonGenericActivity: AppCompatActivity() {

    var fontSizeManager: FontSizeManager? = null

    override fun attachBaseContext(newBase: Context) {
        fontSizeManager = FontSizeManager(newBase.prefs())
        val safeFontSizeManager = fontSizeManager ?: return

        val newConfig = Configuration(newBase.resources.configuration)
        safeFontSizeManager.fontSizeSystem = newConfig.fontScale
        newConfig.fontScale = safeFontSizeManager.fontSize
        applyOverrideConfiguration(newConfig)
        super.attachBaseContext(newBase)
    }

        fun getToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT)

    private fun Context.prefs(): SharedPreferences = getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
}