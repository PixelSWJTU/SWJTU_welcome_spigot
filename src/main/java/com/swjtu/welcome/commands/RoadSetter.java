package com.swjtu.welcome.commands;

import com.swjtu.welcome.Welcome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class RoadSetter implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("§6§用错了！");
            return false;
        }
        String type = args[0];
        String status = args[1];
        if (!type.equals("switch") && !type.equals("line")) {

            sender.sendMessage("§6§l你输入的类型不存在！" + type);
            return false;
        }
        
        if(type.equals("switch")) {
            if (!status.equals("on") && !status.equals("off")) {
                sender.sendMessage("§6§l你输入的状态不存在！" + status);
                return false;
            }
            sender.sendMessage("§6§l你已经设置了道路开关为" + status + "！");
            int intStatus = 0;
            if(status.equals("on")) {
                intStatus = 1;
            }
            sender.getServer().getPlayer(sender.getName()).setMetadata(
                    "roadSwitch", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), intStatus));
        } else if(type.equals("line")) {
            if (!status.equals("single") && !status.equals("double") && !status.equals("off")) {
                sender.sendMessage("§6§l你输入的状态不存在！" + status);
                return false;
            }
            sender.sendMessage("§6§l你已经设置了道路线为" + status + "！");
            sender.getServer().getPlayer(sender.getName()).setMetadata(
                    "roadLine", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), status));
        }

        return true;

//        if(args.length == 0) {
//            sender.sendMessage("请输入on或者off");
//            return false;
//        }
//        String status = args[0];
//        if (!status.equals("on") && !status.equals("off")) {
//            sender.sendMessage("请输入off或者on");
//            return false;
//        }
//        int intStatus = 0;
//        if (status.equals("on")) {
//            intStatus = 1;
//        }
//        sender.getServer().getPlayer(sender.getName()).setMetadata(
//                "roadSwitch", new FixedMetadataValue(
//                        Welcome.getProvidingPlugin(Welcome.class), intStatus
//                )
//        );
//        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("switch");
            list.add("line");
            return list;
        } else if(args.length == 2) {
            if(args[0].equals("switch")) {
                List<String> list = new ArrayList<>();
                list.add("on");
                list.add("off");
                return list;
            } else if(args[0].equals("line")) {
                List<String> list = new ArrayList<>();
                list.add("single");
                list.add("double");
                list.add("off");
                return list;
            }
        }
        return null;
    }
}
