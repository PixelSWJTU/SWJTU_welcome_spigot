package com.swjtu.welcome;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import org.bukkit.Particle.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PlayerInteract implements Listener {
    // 玩家拿到物品右键空气
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Inventory inventory = Bukkit.createInventory(null, 9, "§6§l欢迎来到服务器");


        }
    }

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent event) {
        Location location = event.getSpawnLocation();
        // particale effect
        location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 100);
        event.getPlayer().sendMessage("Spawn at " + location.getX() + " " + location.getY() + " " + location.getZ());

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Location location = event.getRespawnLocation();
        event.getPlayer().sendMessage("ReSpawn at " + location.getX());
    }

    // 用户登陆
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws UnsupportedEncodingException {
        Player player = event.getPlayer();
        player.sendMessage("Welcome to the server!");

        String title = new String("§6欢迎来到西南交通大学！".getBytes(), StandardCharsets.UTF_8);
        player.sendTitle(title, "Welcome", 10, 50, 30);
        welcomeGUI(player);
    }

    public void welcomeGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§6§lWelcome to SWJTU");
        ItemStack item = new ItemStack(Material.BLACK_BED);
        inventory.addItem(item);

        player.openInventory(inventory);
        //设置inventory 不可移动
        inventory.setItem(0, item);
//        inventory.setItem(1, item);


    }


    @EventHandler
    public void GUIChecker(InventoryClickEvent event) {
        if (event.getWhoClicked().getOpenInventory().getTitle().equals("§6§lWelcome to SWJTU")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerPosListener(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        int X = 0;
        int Y = 0;
        int Z = 0;
        int nowX = (int) location.getX();
        int nowY = (int) location.getY();
        int nowZ = (int) location.getZ();
        int delter = 3;

    }


}

