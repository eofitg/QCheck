package net.eofitg.qcheck.listener;

import net.eofitg.qcheck.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

/**
 * I'll finish this someday, maybe
 */
public class DropListener {

    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onDropKeyPress(InputEvent.KeyInputEvent event) {
        if (mc == null || mc.gameSettings == null) return;
        if (!Keyboard.getEventKeyState()) return;

        GameSettings settings = mc.gameSettings;
        int keyCode = Keyboard.getEventKey();

        if (keyCode == settings.keyBindDrop.getKeyCode()) {
            PlayerUtil.addMessage("Pressed" + mc.thePlayer.getHeldItem().getDisplayName());
        }
    }

}