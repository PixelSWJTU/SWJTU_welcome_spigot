package com.swjtu.welcome.commands;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BlockSetter implements CommandExecutor,TabCompleter {
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
        try {
            sender.getServer().getWorld(worldName).loadChunk(X, Z);
            sender.getServer().getWorld(worldName).getBlockAt(X, Y, Z).setType(material);
        } catch (Exception e) {
            sender.sendMessage("§6§l出错了请检查方块和坐标！");
            return false;
        }

        sender.sendMessage("§6§l你已经设置了点(" + X + "," + Y + "," + Z + ")的物品为" + MaterialName + "！");


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        int len = args.length;
        int x = sender.getServer().getPlayer(sender.getName()).getLocation().getBlockX();
        int y = sender.getServer().getPlayer(sender.getName()).getLocation().getBlockY();
        int z = sender.getServer().getPlayer(sender.getName()).getLocation().getBlockZ();
        // /blocksetter <x> <y> <z> <material> <world>
        if(args.length == 1) {
            List<String> sub = Arrays.asList(String.valueOf(x));
            return sub;
        } else if (args.length == 2) {
            List<String> sub = Arrays.asList(String.valueOf(y));
            return sub;
        } else if (args.length == 3) {
            List<String> sub = Arrays.asList(String.valueOf(z));
            return sub;
        } else if (args.length == 4) {
            List<String> sub = Arrays.asList(Material.values()).stream().map(Material::name).collect(Collectors.toList());
            sub.removeIf(s -> !s.startsWith(args[3]));
            return sub;
        } else if (args.length == 5) {
            List<String> sub = new ArrayList<>();
            for(World world : sender.getServer().getWorlds()) {
                sub.add(world.getName());
            }
            sub.removeIf(s -> !s.startsWith(args[4]));
            return sub;
        }
        return null;
    }
}
