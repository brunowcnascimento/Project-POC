package com.brunowcnascimento.projectpoc.feature.font_size.setup

import android.content.SharedPreferences

class FontSizeManager(private val prefs: SharedPreferences) {

    private val unsetFontSizeValue = -1f
    var fontSizeSystem: Float = FontSize.DEFAULT

    var fontSize: Float
        get() {
            val scale = prefs.getFloat("font_scale", unsetFontSizeValue)
            return if (scale == unsetFontSizeValue) {
                1.0f
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

}