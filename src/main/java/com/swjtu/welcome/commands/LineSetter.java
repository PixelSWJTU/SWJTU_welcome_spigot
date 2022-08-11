package com.swjtu.welcome.commands;

import com.swjtu.welcome.Welcome;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.metadata.FixedMetadataValue;

public class LineSetter implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6§用错了！");
            return false;
        }
        String materialName = args[0];
        Material material = Material.matchMaterial(materialName);
        if (material == null) {
            sender.sendMessage("§6§l你输入的物品不存在！");
            return false;
        }
        sender.sendMessage("§6§l你已经设置了线的物品为" + materialName + "！");
        sender.getServer().getPlayer(sender.getName()).setMetadata(
                "lineMaterial", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), material));
        return false;
    }
}
