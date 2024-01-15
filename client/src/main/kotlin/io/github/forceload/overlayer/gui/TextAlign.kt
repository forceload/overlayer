package io.github.forceload.overlayer.gui

enum class TextAlign {
    TOP_LEFT, TOP_CENTER, TOP_RIGHT,
    LEFT, CENTER, RIGHT,
    BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;

    fun isHorizontalLeft() = when (this) {
        TOP_LEFT, LEFT, BOTTOM_LEFT -> true
        else -> false
    }

    fun isHorizontalCenter() = when (this) {
        TOP_CENTER, CENTER, BOTTOM_CENTER -> true
        else -> false
    }

    fun isHorizontalRight() = when (this) {
        TOP_RIGHT, RIGHT, BOTTOM_RIGHT -> true
        else -> false
    }

    fun isVerticalTop() = when (this) {
        TOP_LEFT, TOP_CENTER, TOP_RIGHT -> true
        else -> false
    }

    fun isVerticalCenter() = when (this) {
        LEFT, CENTER, RIGHT -> true
        else -> false
    }

    fun isVerticalBottom() = when (this) {
        BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT -> true
        else -> false
    }
}