package com.swjtu.welcome;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import org.bukkit.Particle.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PlayerInteract implements Listener {
    // 玩家拿到物品右键空气
//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event) {
//
//        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
//            Inventory inventory = Bukkit.createInventory(null, 9, "§6§l欢迎来到服务器");
//
//
//        }
//    }

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
        player.setGameMode(GameMode.CREATIVE);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.CREATIVE);
    }


    public void welcomeGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§6§lWelcome to SWJTU");
        ItemStack item = new ItemStack(Material.BLACK_BED);
        inventory.addItem(item);
        player.openInventory(inventory);
        inventory.setItem(0, item);
    }


    @EventHandler
    public void GUIChecker(InventoryClickEvent event) {
        if (event.getWhoClicked().getOpenInventory().getTitle().equals("§6§lWelcome to SWJTU")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerPosListener(PlayerMoveEvent event) {
        // monitor if user have moved to the gate
        Player player = event.getPlayer();
        Location location = player.getLocation();
        //read the position of the gate
        Plugin config = Welcome.getProvidingPlugin(Welcome.class);

//        player.setMetadata("hasReport", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), true));
        // check if user have reported from player Metadata



        int gateX1 = (int) config.getConfig().getDouble("gatePos1.X");
        int gateY1 = (int) config.getConfig().getDouble("gatePos1.Z");
        int gateX2 = (int) config.getConfig().getDouble("gatePos2.X");
        int gateY2 = (int) config.getConfig().getDouble("gatePos2.Z");
        int userX = (int) location.getX();
        int userY = (int) location.getZ();
        double distance = Math.sqrt(Math.pow(gateX1 - location.getX(), 2) + Math.pow(gateY1 - location.getY(), 2));
//        player.sendMessage("距离校门" + distance);
//        player.sendMessage("校门1" + gateX1 + " " + gateY1);
//        player.sendMessage("校门2" + gateX2 + " " + gateY2);
//        player.sendMessage("==============================");
        if (userX >= gateX1 && userX <= gateX2 && userY >= gateY1 && userY <= gateY2) {
            if (player.hasMetadata("hasReport")) {
                // check if it is 1
                String hasReport = player.getMetadata("hasReport").get(0).asString();
                if (hasReport.equals("1")) {
//                    player.sendMessage("§6§l你已经上报过了！");
                    return;
                }
            }
            player.sendTitle("§6§l欢迎来到西南交通大学！", "Welcome", 10, 50, 30);
            // 设置玩家已经报道为true
            player.setMetadata("hasReport", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), 1));
        }

    }


}

