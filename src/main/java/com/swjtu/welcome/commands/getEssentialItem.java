package com.swjtu.welcome.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class getEssentialItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Hello World!");
        String cmd = command.getName();
        getEssentialItemHandler((Player) sender);
        return true;
    }

    public void getEssentialItemHandler(CommandSender sender) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§6§l请带好所有东西出门！");
        // 录取通知书
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§6§l录取通知书");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(0, itemStack);

        // 档案袋（箱子）
        ItemStack itemStack1 = new ItemStack(Material.CHEST);
        // 设置名字 -- 档案带
        ItemMeta iMeta = itemStack1.getItemMeta();
        iMeta.setDisplayName("§6§l档案带");
        itemStack1.setItemMeta(iMeta);
        inventory.setItem(1, itemStack1);

        // 口罩
        ItemStack itemStack2 = new ItemStack(Material.LEATHER_HELMET);
        // 设置名字 -- 口罩
        ItemMeta iMeta2 = itemStack2.getItemMeta();
        iMeta2.setDisplayName("§6§l口罩");
        itemStack2.setItemMeta(iMeta2);
        inventory.setItem(2, itemStack2);

        // open
        ((Player) sender).openInventory(inventory);
    }
}


