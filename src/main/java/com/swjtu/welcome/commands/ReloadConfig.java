package com.swjtu.welcome.commands;

import com.swjtu.welcome.Welcome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ReloadConfig implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Plugin config = Welcome.getProvidingPlugin(Welcome.class);
        config.reloadConfig();
        sender.sendMessage("§3§l配置文件已重载！");
        return false;
    }
}
