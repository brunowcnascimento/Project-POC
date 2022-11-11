package com.brunowcnascimento.projectpoc.feature.font_size.setup

import android.content.SharedPreferences
import com.brunowcnascimento.projectpoc.utils.common.CommonGenericActivity.Companion.PREFS_FONT_SIZE_SYSTEM

class FontSizeManager(private val prefs: SharedPreferences, private val oldFontSizeSystem: SharedPreferences) {

    var fontSizeSystem: Float = FontSize.DEFAULT

    private fun oldFontSizeF() {
        val oldFontSize =  oldFontSizeSystem.getFloat(PREFS_OLD_FONT_SIZE_SYSTEM, UNSET_FONT_SIZE_VALUE)

        if(oldFontSize == UNSET_FONT_SIZE_VALUE) {
            oldFontSize
            oldFontSizeSystem.edit()
                .putFloat(PREFS_OLD_FONT_SIZE_SYSTEM, fontSizeSystem)
                .apply()
            // colocar a fonteSize atual
            // reload == false
        } else if(oldFontSize.compareTo(fontSizeSystem) == 0) {
            oldFontSize
            // reload == false
        } else {
            oldFontSize
            // colocar a fonte size atual
            // relogar trocando o switch para false
        }
    }

    var fontSize: Float
        get() {
            oldFontSizeF()
            val scale = prefs.getFloat("font_scale", UNSET_FONT_SIZE_VALUE)
            return if (scale == UNSET_FONT_SIZE_VALUE) {
                FontSize.DEFAULT
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

    companion object {
        private const val UNSET_FONT_SIZE_VALUE = -1f
        private const val PREFS_OLD_FONT_SIZE_SYSTEM = "PREFS_OLD_FONT_SIZE_SYSTEM"
    }
}