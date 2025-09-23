package net.eofitg.qcheck.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class PlayerUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void addMessage(String msg) {
        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "[QCheck] " + msg));
    }

}
