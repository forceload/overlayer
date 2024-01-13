package io.github.forceload.overlayer

import com.mojang.logging.LogUtils
import io.github.forceload.overlayer.property.PropertyLoader
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient

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
            }

            else -> logger.error("This mode is only supported on the client!")
        }
    }
}