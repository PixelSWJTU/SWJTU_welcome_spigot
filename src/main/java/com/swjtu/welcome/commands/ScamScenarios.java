package com.swjtu.welcome.commands;

import com.swjtu.welcome.Welcome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class ScamScenarios implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        scamInterface(sender);
        return true;
    }

    public void scamInterface(CommandSender sender) {
        //从配置文件中读取剧本
//        List<String> scenarios = getConfig().getStringList("scamScenarios");
            Plugin config = Welcome.getProvidingPlugin(Welcome.class);
            List<String> scenarios = config.getConfig().getStringList("scamScenarios");

            // 延时发送
            for (String scenario : scenarios) {
                getServer().getScheduler().runTaskLater(config, () -> {
                    sender.sendMessage(scenario);
                }, 20L * scenarios.indexOf(scenario));
            }
    }
}
