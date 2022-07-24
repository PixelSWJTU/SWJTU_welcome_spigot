package com.swjtu.welcome.commands;

import com.swjtu.welcome.Welcome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class setReportStatus implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("§6§/setReportStatus [Player] [Status 1/0]！");
            return false;
        }
        String PlayerName = args[0];
        Player player = sender.getServer().getPlayer(PlayerName);
        if (player == null) {
            sender.sendMessage("§6§l你输入的玩家不存在！");
            return false;
        }
//        player.setMetadata("hasReport", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), true));
        String status = args[1];
        if (status.equals("1")) {
            player.setMetadata("hasReport", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), 1));
            sender.sendMessage("§6§l你已经设置了玩家" + PlayerName + "的报到状态为1！");
        } else if (status.equals("0")) {
            player.setMetadata("hasReport", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), 0));
            sender.sendMessage("§6§l你已经设置了玩家" + PlayerName + "的报到状态为0！");
        } else {
            sender.sendMessage("§6§l你输入的状态不正确！");
        }
        return false;
    }
}
