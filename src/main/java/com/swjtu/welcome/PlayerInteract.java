package com.swjtu.welcome;


import com.sun.org.apache.xpath.internal.operations.Bool;
import com.swjtu.welcome.utils.Fireworks;
import com.swjtu.welcome.utils.SpecialItemGenerator;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import sun.jvm.hotspot.debugger.SymbolLookup;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerInteract implements Listener {

    public String getVersion(String rawVersion) {
        return rawVersion.substring(rawVersion.indexOf("(MC: ") + 5, rawVersion.indexOf(")"));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
//        String version = event.getPlayer().getServer().getVersion();
//        event.getPlayer().sendMessage(version);
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            return;
        }
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();

        // 用户点击的坐标
        Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
        Material m = itemStack.getType();
//        event.getPlayer().sendMessage(itemStack.toString());
        // 如果为石斧头
        if (m.equals(Material.STONE_AXE)) {
            // 检测用户有无权限
            // debug
//            if (!event.getPlayer().hasPermission("welcome.drawline")) {
//                event.getPlayer().sendMessage(ChatColor.RED + "你没有权限使用该命令");
//                return;
//            }
            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                event.getPlayer().setMetadata("firstPointOfLine", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), location));
                event.getPlayer().sendMessage("§6§l你已经设置了第一个点！");
                event.setCancelled(true);
                return;
            } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                event.getPlayer().setMetadata("secondPointOfLine", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), location));
                event.setCancelled(true);
                setLine(event.getPlayer());
                return;
            }
        }

    }

    public Material getBlock(String item) {
        return Material.getMaterial(item);
    }

    public void setLine(Player player) {

        List<Location> list = new ArrayList<>();
        Location firstPoint = null;
        Location secondPoint = null;
        // draw a line between two points
        try {
            firstPoint = (Location) player.getMetadata("firstPointOfLine").get(0).value();
            secondPoint = (Location) player.getMetadata("secondPointOfLine").get(0).value();
        } catch (Exception e) {
            return;
        }
        player.sendMessage("§6§l你已经设置了第二个点！划线中...");
        if (firstPoint == null || secondPoint == null) {
            return;
        }

        player.removeMetadata("firstPointOfLine", Welcome.getProvidingPlugin(Welcome.class));
        player.removeMetadata("secondPointOfLine", Welcome.getProvidingPlugin(Welcome.class));

        int x1 = firstPoint.getBlockX();
        int y1 = firstPoint.getBlockY();
        int z1 = firstPoint.getBlockZ();
        int x2 = secondPoint.getBlockX();
        int y2 = secondPoint.getBlockY();
        int z2 = secondPoint.getBlockZ();
        int tipx = x1;
        int tipy = y1;
        int tipz = z1;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int dz = Math.abs(z2 - z1);

        if (dx + dy + dz == 0) {
            list.add(new Location(firstPoint.getWorld(), tipx, tipy, tipz));
            return;
        }
        int dMax = Math.max(Math.max(dx, dy), dz);
        if (dMax == dx) {
            for(int domstep = 0; domstep <= dx; domstep++) {
                tipx = x1 + domstep * (x2 - x1 > 0 ? 1 : -1);
                tipy = (int) Math.round(y1 + domstep * ((double) dy) / ((double) dx) * (y2 - y1 > 0 ? 1 : -1));
                tipz = (int) Math.round(z1 + domstep * ((double) dz) / ((double) dx) * (z2 - z1 > 0 ? 1 : -1));
                list.add(new Location(firstPoint.getWorld(), tipx, tipy, tipz));
            }
        } else if (dMax == dy) {
            for(int domstep = 0; domstep <= dy; domstep++) {
                tipy = y1 + domstep * (y2 - y1 > 0 ? 1 : -1);
                tipx = (int) Math.round(x1 + domstep * ((double) dx) / ((double) dy) * (x2 - x1 > 0 ? 1 : -1));
                tipz = (int) Math.round(z1 + domstep * ((double) dz) / ((double) dy) * (z2 - z1 > 0 ? 1 : -1));
                list.add(new Location(firstPoint.getWorld(), tipx, tipy, tipz));
            }
        } else /* if (dMax == dz) */ {
            for(int domstep = 0; domstep <= dz; domstep++) {
                tipz = z1 + domstep * (z2 - z1 > 0 ? 1 : -1);
                tipy = (int) Math.round(y1 + domstep * ((double) dy) / ((double) dz) * (y2 - y1 > 0 ? 1 : -1));
                tipx = (int) Math.round(x1 + domstep * ((double) dx) / ((double) dz) * (x2 - x1 > 0 ? 1 : -1));
                list.add(new Location(firstPoint.getWorld(), tipx, tipy, tipz));
            }
        }
        Material material = (Material) player.getMetadata("lineMaterial").get(0).value();
        for(Location loc : list) {
            loc.getBlock().setType(material);
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
//        new utils().playParticle(player);
//        Bukkit.getScheduler().runTaskAsynchronously(Welcome.getProvidingPlugin(Welcome.class),new Runnable() {
//            @Override
//            public void run() {
//        new utils().playParticle(player);
//            }
//        });

        player.sendMessage("Welcome to the server!");

        String title = new String("§6欢迎来到西南交通大学！".getBytes(), StandardCharsets.UTF_8);
        player.sendTitle(title, "Welcome", 10, 50, 30);
        player.setGameMode(GameMode.CREATIVE);
        Plugin config = Welcome.getProvidingPlugin(Welcome.class);
        config.reloadConfig();
        Boolean isEnable = (Boolean) config.getConfig().get("resEnable");
        player.sendMessage("将要使用服务器材质包: " + isEnable);
        if (Boolean.TRUE.equals(isEnable) || config.getConfig().get("resEnable") == "true") {
            String resPack = config.getConfig().getString("resPackURL");
            player.sendMessage("resPack: " + resPack);
            if (resPack == null ) {
                resPack = "";
                config.saveConfig();
            } else {
                player.setResourcePack(resPack);
            }
        }


    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.CREATIVE);
//        player.performCommand("gamemode 1");
    }


    public void welcomeGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§6§lWelcome to SWJTU");
        ItemStack item = new ItemStack(Material.DIAMOND_BLOCK);
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

        int gateX1 = (int) config.getConfig().getDouble("gatePos1.X");
        int gateY1 = (int) config.getConfig().getDouble("gatePos1.Z");
        int gateX2 = (int) config.getConfig().getDouble("gatePos2.X");
        int gateY2 = (int) config.getConfig().getDouble("gatePos2.Z");
        int userX = (int) location.getX();
        int userY = (int) location.getZ();
        double distance = Math.sqrt(Math.pow(gateX1 - location.getX(), 2) + Math.pow(gateY1 - location.getY(), 2));
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
            // 穿上防护服
            SpecialItemGenerator spi = new SpecialItemGenerator();
            ItemStack uniform = spi.generateNewUniforms();
            player.getInventory().setChestplate(uniform);
            Fireworks fw = new Fireworks(player);
            fw.playParticle();
            player.spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 100);
            player.spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation(), 100);
        }

    }


}

