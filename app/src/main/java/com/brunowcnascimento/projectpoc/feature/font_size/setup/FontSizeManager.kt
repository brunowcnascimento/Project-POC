package com.brunowcnascimento.projectpoc.feature.font_size.setup

import android.content.SharedPreferences

class FontSizeManager(private val prefs: SharedPreferences) {

    private val unsetFontSizeValue = -1f

    var fontSize: FontSize
        get() {
            val scale = prefs.getFloat("font_scale", unsetFontSizeValue)
            return if (scale == unsetFontSizeValue) {
                FontSize.DEFAULT
            } else {
                FontSize.values().first { fontSize -> fontSize.scale == scale }
            }
        }
        set(value) {
            prefs.edit()
                .putFloat("font_scale", value.scale)
                .apply()
        }

}