package com.brunowcnascimento.projectpoc.feature.font_size.setup

import android.content.SharedPreferences

class FontSizeManager(private val prefs: SharedPreferences) {

    private val unsetFontSizeValue = -1f
    private val floatList = arrayListOf(0.7f, 1.0f, 1.3f, 1.6f)
    var fontSizeSystem: Float = 1.0f

    var fontSize: Float
        get() {
            val scale = prefs.getFloat("font_scale", unsetFontSizeValue)
            return if (scale == unsetFontSizeValue) {
                1.0f
            } else {
                floatList.first { fontSize -> fontSize == scale }
            }
        }
        set(fontSize) {
            prefs.edit()
                .putFloat("font_scale", fontSize)
                .apply()
        }

}