//? if < 26.1 {
/*package at.hannibal2.skyhanni.mixins.transformers;

import at.hannibal2.skyhanni.features.chat.ChatPeek;
import at.hannibal2.skyhanni.features.chroma.ChromaFontManager;
import at.hannibal2.skyhanni.features.misc.visualwords.ModifyVisualWords;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ListIterator;

@Mixin(ChatComponent.class)
public abstract class MixinChatComponent {

    @WrapOperation(
        method = "addMessageToDisplayQueue",
        at = @At(
            value = "NEW",
            target = "net/minecraft/client/GuiMessage$Line"
        )
    )
    private GuiMessage.Line addMessageId(
        int addedTime,
        FormattedCharSequence content,
        GuiMessageTag tag,
        boolean endOfEntry,
        Operation<GuiMessage.Line> original,
        GuiMessage message
    ) {
        GuiMessage.Line line = original.call(addedTime, content, tag, endOfEntry);
        line.skyhanni$setParent(message);
        return line;
    }

    @Shadow
    public static int getHeight(double heightOption) {
        return 0;
    }

    @Shadow
    @Final
    private Minecraft minecraft;

    @Redirect(
        method = "deleteMessageOrDelay",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getGuiTicks()I"),
        require = 0
    )
    private int clearChatHead(Gui instance) {
        return instance.getGuiTicks() + 90;
    }

    @Redirect(
        method = "deleteMessageOrDelay",
        at = @At(value = "INVOKE", target = "Ljava/util/ListIterator;set(Ljava/lang/Object;)V"),
        require = 0
    )
    private <E> void clearChatTail(ListIterator instance, E e) {
        instance.remove();
    }

    @Inject(method = "getHeight()I", at = @At("HEAD"), cancellable = true)
    private void getHeight(CallbackInfoReturnable<Integer> cir) {
        if (ChatPeek.peek()) {
            cir.setReturnValue(getHeight(this.minecraft.options.chatHeightFocused().get()));
        }
    }

    @WrapMethod(method = "render(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IIZ)V")
    private void wrapRender(
        ChatComponent.ChatGraphicsAccess chatGraphicsAccess,
        int screenHeight,
        int ticks,
        boolean isChatting,
        Operation<Void> original
    ) {
        ChromaFontManager.setRenderingChat(true);
        ModifyVisualWords.INSTANCE.setChangeWords(false);

        original.call(chatGraphicsAccess, screenHeight, ticks, isChatting);

        ChromaFontManager.setRenderingChat(false);
        ModifyVisualWords.INSTANCE.setChangeWords(true);
    }
}
*///?}
