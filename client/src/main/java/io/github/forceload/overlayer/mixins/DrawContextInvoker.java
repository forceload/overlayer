package io.github.forceload.overlayer.mixins;

import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DrawContext.class)
public interface DrawContextInvoker {
    @Invoker("tryDraw")
    void invokeTryDraw();
}
