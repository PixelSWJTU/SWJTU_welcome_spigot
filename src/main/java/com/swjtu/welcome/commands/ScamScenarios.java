package com.swjtu.welcome.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ScamScenarios implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("§6§l你不是玩家！");
        } else {
            sender.sendMessage("§6§l你不是玩家！");
        }
        return true;
    }

    public void scamInterface(CommandSender sender) {
        //从配置文件中读取剧本
//        List<String> scenarios = getConfig().getStringList("scamScenarios");

    }
}
