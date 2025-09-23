package net.eofitg.qcheck.command;

import net.eofitg.qcheck.QCheck;
import net.eofitg.qcheck.util.PlayerUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class QCheckCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "qcheck";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("qc");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/qcheck toggle|add|remove <value>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) return;
        if (args.length == 0) {
            PlayerUtil.addMessage(EnumChatFormatting.YELLOW + "Usage: " + getCommandUsage(sender));
            return;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "toggle": {
                QCheck.config.setEnabled(!QCheck.config.isEnabled());
                boolean isEnabled = QCheck.config.isEnabled();
                String status = isEnabled ? EnumChatFormatting.GREEN + "enabled" : EnumChatFormatting.RED + "disabled";
                PlayerUtil.addMessage(EnumChatFormatting.GOLD + "Mod " + status + EnumChatFormatting.GOLD + ".");
                QCheck.saveConfig();
                break;
            }
            case "add": {
                QCheck.config.mapAdd(((EntityPlayer) sender).getHeldItem());
                PlayerUtil.addMessage(EnumChatFormatting.GOLD + "Held item added");
                QCheck.saveConfig();
                break;
            }
            case "remove": {
                if (args.length >= 2) {
                    try {
                        int id = Integer.parseInt(args[1]);
                        if (id >= QCheck.config.getCheckMap().size() || id < 0) {
                            PlayerUtil.addMessage(EnumChatFormatting.RED + "Unknown item id.");
                            break;
                        }
                        QCheck.config.mapRemove(id);
                        PlayerUtil.addMessage(EnumChatFormatting.GOLD + "Item removed.");
                        QCheck.saveConfig();
                    } catch (NumberFormatException e) {
                        PlayerUtil.addMessage(EnumChatFormatting.RED + "Unknown item id.");
                    }
                } else {
                    StringBuilder list = new StringBuilder("\n");
                    for (Map.Entry<Integer, String> entry : QCheck.config.getCheckMap().entrySet()) {
                        list.append(entry.getKey()).append(": {").append(entry.getValue()).append("}\n");
                    }
                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + list.toString()));
                }
                break;
            }
            default: {
                PlayerUtil.addMessage(EnumChatFormatting.RED + "Unknown argument: " + sub);
                PlayerUtil.addMessage(EnumChatFormatting.YELLOW + "Usage: " + getCommandUsage(sender));
            }
        }
    }

}
