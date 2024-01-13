package io.github.forceload.overlayer.property

import io.github.forceload.overlayer.OverlayerMod
import io.github.forceload.overlayer.property.type.Property
import io.github.forceload.overlayer.property.type.PropertyMap

object PropertyLoader {
    fun loadInitialProperty(): HashMap<String, Property<*>> {
        val property = PropertyMap<String>()

        /*
        // System Properties
        val processor = GlDebugInfo.getCpuInfo()
        val renderer = GL11.glGetString(GL11.GL_RENDERER)

        property["Processor"] = processor
        property["Renderer"] = renderer
        */

        val maxMemory = Runtime.getRuntime().maxMemory()
        val memoryUsage = Runtime.getRuntime().totalMemory()
        val freeMemory = Runtime.getRuntime().totalMemory()

        property["MaxMemory"] = toMiB(maxMemory)
        property["MemoryUsage"] = toMiB(memoryUsage)
        property["FreeMemory"] = toMiB(freeMemory)

        // Game Properties
        val version = OverlayerMod.clientInstance.gameVersion
        property["Version"] = version

        // GUI Properties
        val currentScreen = OverlayerMod.clientInstance.currentScreen?.javaClass?.simpleName
        val currentOverlay = OverlayerMod.clientInstance.overlay?.javaClass?.simpleName

        property["CurrentScreen"] = currentOverlay
        property["CurrentOverlay"] = currentScreen

        return property
    }

    private fun toMiB(bytes: Long) = bytes / 1024.0 / 1024.0
}