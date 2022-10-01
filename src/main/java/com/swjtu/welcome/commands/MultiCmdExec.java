package com.swjtu.welcome.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiCmdExec implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6§l你没有输入命令！");
            return true;
        }
//        sender.getServer().getWorld().loadChunk();
        // join args into a string
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        String cmd = sb.toString().trim();
        String[] cmdList = cmd.split(";");
        // 每0.5秒执行一次
        for (String cmdStr : cmdList) {
            String cmdStrTrim = cmdStr.trim();
            // 异步执行
            sender.getServer().dispatchCommand(sender, cmdStrTrim);
        }


        return false;

    }
}
