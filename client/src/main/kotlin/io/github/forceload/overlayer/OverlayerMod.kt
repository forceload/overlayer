package io.github.forceload.overlayer

import com.mojang.logging.LogUtils
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader

object OverlayerMod: ModInitializer {
    const val MOD_ID = "sample"
    private lateinit var loaderInstance: FabricLoader
    val logger = LogUtils.getLogger()

    override fun onInitialize() {
        loaderInstance = FabricLoader.getInstance()

        when (loaderInstance.environmentType) {
            EnvType.CLIENT -> {
                TODO("Write Your Code")
            }

            else -> logger.error("This mode is only supported on the client!")
        }
    }
}