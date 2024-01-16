package io.github.forceload.overlayer.property

import com.mojang.blaze3d.platform.GlDebugInfo
import io.github.forceload.overlayer.OverlayerMod
import io.github.forceload.overlayer.property.type.PropertyMap
import org.lwjgl.opengl.GL11

object PropertyLoader {
    fun loadInitialProperty(): PropertyMap<String> {
        val property = PropertyMap<String>()

        // Memory Information
        loadMemoryInfo(property)

        // Game Properties
        val version = OverlayerMod.clientInstance.gameVersion
        property["Version"] = version

        // GUI Properties
        loadGUIInfo(property)

        return property
    }

    var gameTime = 0.0
    var frameCounter = 0
    var targetTime = 0

    var fps = 0
    fun updateProperty(property: PropertyMap<String>, tickDelta: Float) {
        // System Properties
        val processor = GlDebugInfo.getCpuInfo()
        val renderer = GL11.glGetString(GL11.GL_RENDERER)

        property["Processor"] = processor
        property["Renderer"] = renderer

        // Performance Properties
        // tickDelta 1 = 50ms (1/20s)
        val deltaTime = tickDelta * 50.0
        gameTime += deltaTime
        frameCounter++

        fps = if (gameTime >= targetTime) {
            val temp = frameCounter
            targetTime += 1000; frameCounter = 0; temp
        } else fps

        property["DeltaTime"] = deltaTime
        property["FPS"] = fps

        // Memory Information
        loadMemoryInfo(property)

        // GUI Properties
        loadGUIInfo(property)
    }

    private fun loadGUIInfo(property: PropertyMap<String>) {
        val currentScreen = OverlayerMod.clientInstance.currentScreen?.javaClass?.simpleName
        val currentOverlay = OverlayerMod.clientInstance.overlay?.javaClass?.simpleName

        property["CurrentScreen"] = currentOverlay
        property["CurrentOverlay"] = currentScreen
    }

    private fun loadMemoryInfo(property: PropertyMap<String>) {
        val maxMemory = Runtime.getRuntime().maxMemory()
        val memoryUsage = Runtime.getRuntime().totalMemory()
        val freeMemory = Runtime.getRuntime().totalMemory()

        property["MaxMemory"] = toMiB(maxMemory)
        property["MemoryUsage"] = toMiB(memoryUsage)
        property["FreeMemory"] = toMiB(freeMemory)
    }

    private fun toMiB(bytes: Long) = bytes / 1024.0 / 1024.0
}