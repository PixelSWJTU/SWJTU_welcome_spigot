package com.swjtu.welcome.commands;

import com.swjtu.welcome.Welcome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class setGatePos implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6§l请输入第几个位置[1,2]！");
            return false;
        }

        Plugin config = Welcome.getProvidingPlugin(Welcome.class);

        // get player now location
        int X = ((Player) sender).getLocation().getBlockX();
        int Y = ((Player) sender).getLocation().getBlockY();

        String arg = args[0];
        if (arg.equals("1")) {
            config.getConfig().set("gatePos1.X", X);
            config.getConfig().set("gatePos1.Y", Y);
//            config.getConfig().set("gatePos1.Z", Z);
            // append to config file
            config.saveConfig();
            sender.sendMessage("§6§l你已经设置了第一个门的位置！");
            // reload
            config.reloadConfig();
        } else if (arg.equals("2")) {
            config.getConfig().set("gatePos2.X", X);
            config.getConfig().set("gatePos2.Y", Y);
//            config.getConfig().set("gatePos2.Z", Z);
            config.saveConfig();
            sender.sendMessage("§6§l你已经设置了第二个门的位置！");
            // reload config
            config.reloadConfig();
        } else {
            sender.sendMessage("§6§l你输入的参数有误！");
        }
        int X1 = config.getConfig().getInt("gatePos1.X");
        int Y1 = config.getConfig().getInt("gatePos1.Y");
        int X2 = config.getConfig().getInt("gatePos2.X");
        int Y2 = config.getConfig().getInt("gatePos2.Y");
        sender.sendMessage("§6§l现在校门位置：" + X1 + "," + Y1 + "," + X2 + "," + Y2);

        return true;
    }
}
