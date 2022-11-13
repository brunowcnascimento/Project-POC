package com.brunowcnascimento.projectpoc.feature.font_size.setup

object FontSize {
    private const val SMALL = 0.7f
    private const val LARGE = 1.3f
    private const val LARGEST = 1.6f

    const val DEFAULT = 1.0f
    const val UNSET_FONT_SIZE = -1f

    const val EQUALS = 0
    const val DIFF = 1
    const val NO_VALUE = -1

    val fontSizeList = listOf(
        SMALL,
        DEFAULT,
        LARGE,
        LARGEST
    )

    fun List<Float>.getFontSizeByPosition(position: Int) = this[position]

    fun Float.compare(fontSize: Float): Int {
        return when {
            this == UNSET_FONT_SIZE -> NO_VALUE
            this != fontSize -> DIFF
            else -> EQUALS
        }
    }
}

