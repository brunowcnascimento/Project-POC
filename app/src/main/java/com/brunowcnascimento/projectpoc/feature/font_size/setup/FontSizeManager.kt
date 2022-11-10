package com.brunowcnascimento.projectpoc.feature.font_size.setup

import android.content.SharedPreferences

class FontSizeManager(private val prefs: SharedPreferences) {

    var fontSizeSystem: Float = FontSize.DEFAULT

    var fontSize: Float
        get() {
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
    }
}