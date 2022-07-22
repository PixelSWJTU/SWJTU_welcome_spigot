package com.swjtu.welcome.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class maskChecker implements CommandExecutor {
    public static boolean checkMask(Player player) {
        if (player.getInventory().getHelmet() == null) {
            return false;
        }
        return player.getInventory().getHelmet().getType() == Material.LEATHER_HELMET;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (checkMask(player)) {
                player.sendMessage("§6§l你已经带好口罩了！");
            } else {
                player.sendMessage("§6§l你还没带口罩！");
            }
        } else {
            sender.sendMessage("§6§l你不是玩家！");
        }
        return true;
    }
}
