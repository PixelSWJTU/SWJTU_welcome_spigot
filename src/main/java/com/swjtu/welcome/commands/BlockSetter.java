package com.swjtu.welcome.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BlockSetter implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6§用错了！");
            return false;
        }
        int X = Integer.parseInt(args[0]);
        int Y = Integer.parseInt(args[1]);
        int Z = Integer.parseInt(args[2]);
        String MaterialName = args[3];
        String worldName = args[4];
        Material material = Material.matchMaterial(MaterialName);


        if (material == null) {
            sender.sendMessage("§6§l你输入的物品不存在！");
            return false;
        }
        sender.getServer().getWorld(worldName).loadChunk(X, Z);
        sender.getServer().getWorld(worldName).getBlockAt(X, Y, Z).setType(material);
        sender.sendMessage("§6§l你已经设置了点(" + X + "," + Y + "," + Z + ")的物品为" + MaterialName + "！");


        return false;
    }
}
