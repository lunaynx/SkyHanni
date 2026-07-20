package at.hannibal2.skyhanni.mixins.transformers;

import at.hannibal2.skyhanni.events.minecraft.RenderShutdownEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class MixinWindow {

    @WrapOperation(
        //~ if < 26.1 'createGlfwWindow' -> '<init>'
        method = "createGlfwWindow",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/glfw/GLFW;glfwCreateWindow(IILjava/lang/CharSequence;JJ)J"
        )
    )
    private static long createGameTestWindow(
        int width,
        int height,
        CharSequence title,
        long monitor,
        long share,
        Operation<Long> original
    ) {
        boolean startMinimized = Boolean.getBoolean("SkyHanniGameTest.minimizeWindow");
        if (startMinimized) {
            GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_FOCUSED, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW, GLFW.GLFW_FALSE);
        }
        long window = original.call(width, height, title, monitor, share);
        //if (startMinimized && window != 0L) GLFW.glfwIconifyWindow(window);
        return window;
    }

    @Inject(
        method = "close",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;assertOnRenderThread()V",
            shift = At.Shift.AFTER
        )
    )
    private void onRenderShutdown(CallbackInfo ci) {
        RenderShutdownEvent.INSTANCE.post();
    }
}
