package com.swjtu.welcome.commands;

import com.swjtu.welcome.Welcome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.metadata.FixedMetadataValue;

public class RoadSetter implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("请输入on或者off");
            return false;
        }
        String status = args[0];
        if (!status.equals("on") && !status.equals("off")) {
            sender.sendMessage("请输入off或者on");
            return false;
        }
        int intStatus = 0;
        if (status.equals("on")) {
            intStatus = 1;
        }
        sender.getServer().getPlayer(sender.getName()).setMetadata(
                "roadSwitch", new FixedMetadataValue(
                        Welcome.getProvidingPlugin(Welcome.class), intStatus
                )
        );
        return true;

    }
}
