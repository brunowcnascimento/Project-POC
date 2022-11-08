package com.brunowcnascimento.projectpoc.feature.font_size.setup

object FontSize {
    const val SMALL = 0.7f
    const val DEFAULT = 1.0f
    const val LARGE = 1.3f
    const val LARGEST = 1.6f

    val fontSizeList = arrayListOf(
        SMALL,
        DEFAULT,
        LARGE,
        LARGEST
    )

    fun ArrayList<Float>.getFontSizeByPosition(position: Int) = this[position]
}