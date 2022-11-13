package com.brunowcnascimento.projectpoc.feature.font_size.setup

import android.content.SharedPreferences
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize.DEFAULT
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize.DIFF
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize.EQUALS
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize.NO_VALUE
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize.UNSET_FONT_SIZE
import com.brunowcnascimento.projectpoc.feature.font_size.setup.FontSize.compare

class FontSizeManager(private val prefs: SharedPreferences, private val oldFontSizeSystem: SharedPreferences) {

    var fontSizeSystem: Float = DEFAULT
    var diff = false

    var fontSize: Float
        get() {
            oldFontSizeF()
            val scale = prefs.getFloat("font_scale", UNSET_FONT_SIZE)
            return if (scale == UNSET_FONT_SIZE) {
                DEFAULT
            } else {
                try {
                    FontSize.fontSizeList.first { fontSize -> fontSize == scale }
                } catch (e: Exception) {
                    scale
                }
            }
        }
        set(fontSize) {
            prefs.edit()
                .putFloat("font_scale", fontSize)
                .apply()
        }

    private fun oldFontSizeF() {
        val oldFontSize =  oldFontSizeSystem.getFloat(PREFS_OLD_FONT_SIZE_SYSTEM, UNSET_FONT_SIZE)

        when(oldFontSize.compare(fontSizeSystem)) {
            EQUALS -> {
                diff = false
            }
            DIFF -> {
                diff = true
                oldFontSizeSystem.edit().putFloat(PREFS_OLD_FONT_SIZE_SYSTEM, fontSizeSystem).apply()
            }
            NO_VALUE -> {
                diff = false
                oldFontSizeSystem.edit().putFloat(PREFS_OLD_FONT_SIZE_SYSTEM, fontSizeSystem).apply()
            }
        }
    }

    companion object {
        private const val PREFS_OLD_FONT_SIZE_SYSTEM = "PREFS_OLD_FONT_SIZE_SYSTEM"
    }
}