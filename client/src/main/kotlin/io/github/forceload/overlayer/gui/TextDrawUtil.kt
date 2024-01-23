package io.github.forceload.overlayer.gui

import io.github.forceload.overlayer.mixins.DrawContextInvoker
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.font.TextRenderer.TextLayerType
import net.minecraft.client.gui.DrawContext

object TextDrawUtil {
    fun DrawContext.renderText(
        textRenderer: TextRenderer,
        align: TextAlign = TextAlign.TOP_LEFT, anchor: PositionAnchor = PositionAnchor.TOP_LEFT,
        text: String, x: Float, y: Float, color: Int, shadow: Boolean
    ) {
        var (convertedX, convertedY) = anchor.calc(this, x, y)

        if (align.isHorizontalCenter()) convertedX -= textRenderer.getWidth(text) / 2f
        if (align.isHorizontalRight()) convertedX -= textRenderer.getWidth(text)

        val lines = text.count { it == '\n' } + 1
        if (align.isVerticalCenter()) convertedY -= textRenderer.fontHeight * lines / 2f
        if (align.isVerticalBottom()) convertedY -= textRenderer.fontHeight * lines

        textRenderer.draw(
            text, convertedX, convertedY, color, shadow,
            matrices.peek().positionMatrix, this.vertexConsumers, TextLayerType.NORMAL,
            0, 15728880,
            textRenderer.isRightToLeft
        )

        (this as DrawContextInvoker).invokeTryDraw()
    }

    fun DrawContext.renderText(
        textRenderer: TextRenderer,
        align: TextAlign = TextAlign.TOP_LEFT, anchor: PositionAnchor = PositionAnchor.TOP_LEFT,
        text: FormattedText, x: Float, y: Float, color: Int, shadow: Boolean
    ) = renderText(textRenderer, align, anchor, text.toString(), x, y, color, shadow)
}