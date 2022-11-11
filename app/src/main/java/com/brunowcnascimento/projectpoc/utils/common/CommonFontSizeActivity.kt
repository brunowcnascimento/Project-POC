package com.brunowcnascimento.projectpoc.utils.common

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brunowcnascimento.projectpoc.feature.font_size.FontSizeFontSizeActivity
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSizeManager

abstract class CommonGenericActivity : AppCompatActivity() {

    var fontSizeManager: FontSizeManager? = null
    var oldFontSize: SharedPreferences? = null

    override fun attachBaseContext(newBase: Context) {
        fontSizeManager = FontSizeManager(newBase.prefs(), newBase.prefsOldFontSize())
        val safeFontSizeManager = fontSizeManager ?: return

        val newConfig = Configuration(newBase.resources.configuration)

        safeFontSizeManager.fontSizeSystem = newConfig.fontScale
        newConfig.fontScale = safeFontSizeManager.fontSize

        applyOverrideConfiguration(newConfig)
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val safeFontSize = fontSizeManager?.fontSizeSystem ?: ERROR_FONT_SIZE
        oldFontSize?.edit()
            ?.putFloat(PREFS_FONT_SIZE_SYSTEM, safeFontSize)
            ?.apply()
    }

    private fun verifyFontSizeSystem() {
        /*
        fontSizeManager?.apply {
        when {
                oldFontSizeSystem == ERROR_FONT_SIZE -> {  }
                fontSizeSystem == oldFontSizeSystem -> { }
                fontSizeSystem != oldFontSizeSystem -> { }
            }
        }
         */
    }

    fun getToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT)

    private fun Context.prefs(): SharedPreferences =
        getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)

    private fun Context.prefsOldFontSize(): SharedPreferences =
        getSharedPreferences(PREFS_FONT_SIZE_SYSTEM, Context.MODE_PRIVATE)

    companion object {
        private const val ERROR_FONT_SIZE = -1f
        const val PREFS_FONT_SIZE_SYSTEM = "PREFS_FONT_SIZE_SYSTEM"
        const val PREFS_OLD_FONT_SIZE_SYSTEM = "PREFS_OLD_FONT_SIZE_SYSTEM"
    }

}