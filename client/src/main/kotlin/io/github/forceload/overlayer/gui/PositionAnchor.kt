package io.github.forceload.overlayer.gui

import net.minecraft.client.gui.DrawContext

enum class PositionAnchor {
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

    fun calc(context: DrawContext, x: Float, y: Float): Pair<Float, Float> {
        val convertedX =
            if (isHorizontalLeft()) x
            else if (isHorizontalCenter()) context.scaledWindowWidth / 2f + x
            else context.scaledWindowWidth + x
        val convertedY =
            if (isVerticalTop()) y
            else if (isVerticalCenter()) context.scaledWindowHeight / 2f + y
            else context.scaledWindowHeight + y

        return Pair(convertedX, convertedY)
    }
}