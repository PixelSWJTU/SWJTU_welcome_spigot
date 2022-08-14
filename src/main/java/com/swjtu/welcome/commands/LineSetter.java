package com.swjtu.welcome.commands;

import com.swjtu.welcome.Welcome;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LineSetter implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6§用错了！");
            return false;
        }
        String materialName = args[0];
        Material material = Material.getMaterial(materialName);
        Material material1 = Material.getMaterial(materialName.toUpperCase(Locale.ROOT));

        if (material == null && material1 == null) {
            sender.sendMessage("§6§l你输入的物品不存在！");
            return false;
        }
        sender.sendMessage("§6§l你已经设置了线的物品为" + materialName + "！");
        Material finalMaterial = material == null ? material1 : material;
        sender.getServer().getPlayer(sender.getName()).setMetadata(
                "lineMaterial", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), finalMaterial));
        return true;
    }

    //tab complete
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            // all material names
            List<String> sub = Arrays.asList(Material.values()).stream().map(Material::name).collect(Collectors.toList());
            sub.removeIf(s -> !s.startsWith(args[0]));
            return sub;

        }
        return null;
    }



}
