package io.github.forceload.overlayer

import com.mojang.logging.LogUtils
import io.github.forceload.overlayer.gui.PositionAnchor
import io.github.forceload.overlayer.gui.TextAlign
import io.github.forceload.overlayer.gui.TextDrawUtil.renderText
import io.github.forceload.overlayer.property.PropertyLoader
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

object OverlayerMod: ModInitializer {
    const val MOD_ID = "overlayer"
    private lateinit var loaderInstance: FabricLoader
    lateinit var clientInstance: MinecraftClient

    val logger = LogUtils.getLogger()

    override fun onInitialize() {
        loaderInstance = FabricLoader.getInstance()
        clientInstance = MinecraftClient.getInstance()

        when (loaderInstance.environmentType) {
            EnvType.CLIENT -> {
                logger.info("Client environment detected")

                val properties = PropertyLoader.loadInitialProperty()
                logger.info("Initial property loaded")

                HudRenderCallback.EVENT.register { drawContext: DrawContext, tickDelta: Float ->
                    val textRenderer = clientInstance.textRenderer
                    PropertyLoader.updateProperty(properties, tickDelta)

                    val processorText = "Processor: ${properties["Processor"]}"
                    drawContext.renderText(
                        textRenderer, TextAlign.TOP_LEFT, PositionAnchor.TOP_LEFT,
                        processorText, x = 0.0f, y = 0.0f, color = 0xffffff, shadow = true
                    )

                    val performanceText = "FPS: ${properties["FPS"]} " +
                            "(${properties["DeltaTime"]?.formatFloatDigit(2)}ms)"

                    drawContext.renderText(
                        textRenderer, TextAlign.BOTTOM_LEFT, PositionAnchor.BOTTOM_LEFT,
                        performanceText, x = 0.0f, y = 0.0f, color = 0xffffff, shadow = true
                    )
                }
            }

            else -> logger.error("This mod is only supported on the client!")
        }
    }
}