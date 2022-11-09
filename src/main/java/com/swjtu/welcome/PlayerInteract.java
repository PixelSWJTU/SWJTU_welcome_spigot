package com.swjtu.welcome;


import com.swjtu.welcome.utils.Fireworks;
import com.swjtu.welcome.utils.Line;
import com.swjtu.welcome.utils.SpecialItemGenerator;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
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

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.swjtu.welcome.utils.Line.drawLine;
import static org.bukkit.inventory.EquipmentSlot.OFF_HAND;

public class PlayerInteract implements Listener {

    public String getVersion(String rawVersion) {
        return rawVersion.substring(rawVersion.indexOf("(MC: ") + 5, rawVersion.indexOf(")"));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // 添加对副手的检测，不响应副手，避免右键交互时PlayerInteractEvent被触发两次
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getHand().equals(OFF_HAND)) {
            return;
        }
        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();

        // 用户点击的坐标
        Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
        Material m = itemStack.getType();
        Player player = event.getPlayer();
        // 如果为石斧头
        if (m.equals(Material.STONE_AXE)) {
            // 拿着石斧一般就是为了连线，因此立即取消事件，也是为了避免意外破坏方块
            event.setCancelled(true);

            // 如果设置了连线材质，则只在敲击对应材质的方块时才进行连线操作，减少误触的可能性
            if (player.getMetadata("lineMaterial").size() == 0) {
                return;
            }

            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (event.getPlayer().getMetadata("firstPointOfLine").size() != 0) {
                    event.getPlayer().sendMessage("§6§l你已经更改第一个点！");
                } else {
                    event.getPlayer().sendMessage("§6§l你已经设置第一个点！");
                }
                event.getPlayer().setMetadata("firstPointOfLine", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), location));
                return;
            } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                event.getPlayer().setMetadata("secondPointOfLine", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), location));
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
        // check if user has set lineMaterial
        if (player.getMetadata("lineMaterial").size() == 0) {
            player.sendMessage("§6§l你没有设置划线材质，已设置为默认的平滑石砖");
            player.setMetadata("lineMaterial", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), Material.SMOOTH_STONE_SLAB));
        }


        player.sendMessage("§6§l你已经设置了第二个点！划线中...");
        if (firstPoint == null || secondPoint == null) {
            return;
        }

        player.removeMetadata("firstPointOfLine", Welcome.getProvidingPlugin(Welcome.class));
        player.removeMetadata("secondPointOfLine", Welcome.getProvidingPlugin(Welcome.class));


        drawLine(firstPoint, secondPoint, (Material) player.getMetadata("lineMaterial").get(0).value(), player);

        player.setMetadata("firstPointOfLine", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), secondPoint));
    }

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent event) {
        Location location = event.getSpawnLocation();
        // particale effect
        Objects.requireNonNull(location.getWorld()).spawnParticle(Particle.FIREWORKS_SPARK, location, 100);
        event.getPlayer().sendMessage("Spawn at " + location.getX() + " " + location.getY() + " " + location.getZ());

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Location location = event.getRespawnLocation();
        event.getPlayer().sendMessage("ReSpawn at " + location.getX());
    }

    public void sendWelcomeMsg(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String title = new String("§6欢迎来到西南交通大学！".getBytes(), StandardCharsets.UTF_8);
        player.sendTitle(title, "Welcome", 10, 50, 30);
        player.setGameMode(GameMode.CREATIVE);
    }

    // 用户登陆
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws UnsupportedEncodingException {
        sendWelcomeMsg(event);
        Player player = event.getPlayer();
        player.sendMessage("Welcome to the server!");
        Plugin config = Welcome.getProvidingPlugin(Welcome.class);
        Boolean isEnable = (Boolean) config.getConfig().get("resEnable");
        player.sendMessage("将要使用服务器材质包: " + isEnable);
        if (Boolean.TRUE.equals(isEnable) || config.getConfig().get("resEnable") == "true") {
            String resPack = config.getConfig().getString("resPackURL");
            player.sendMessage("resPack: " + resPack);
            if (resPack == null) {
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

    public ArrayList<Location> scanLine(Material stopMaterial, BlockFace facing, int maxBlock, Location startLocation) {
        double x = startLocation.getX();
        double y = startLocation.getY();
        double z = startLocation.getZ();
        // create res array
        ArrayList<Location> res = new ArrayList<>();
        if (facing.equals(BlockFace.NORTH) || facing.equals(BlockFace.SOUTH)) {
            // x
            // left side
            for (int i = 0; i < maxBlock; i++) {
                Location newLocation = new Location(startLocation.getWorld(), x + i, y, z);
                if (newLocation.getBlock().getType().equals(stopMaterial)) {
                    res.add(new Location(startLocation.getWorld(), x + i - 1, y - 1, z));
                    break;
                }
            }

            // right side
            for (int i = 0; i < maxBlock; i++) {
                Location newLocation = new Location(startLocation.getWorld(), x - i, y, z);
                if (newLocation.getBlock().getType().equals(stopMaterial)) {
                    res.add(new Location(startLocation.getWorld(), x - i + 1, y - 1, z));
                    break;
                }
            }

        } else if (facing.equals(BlockFace.EAST) || facing.equals(BlockFace.WEST)) {
            // z
            // left side
            for (int i = 0; i < maxBlock; i++) {
                Location newLocation = new Location(startLocation.getWorld(), x, y, z + i);
                if (newLocation.getBlock().getType().equals(stopMaterial)) {
                    res.add(new Location(startLocation.getWorld(), x, y - 1, z + i - 1));
                    break;
                }
            }

            // right side
            for (int i = 0; i < maxBlock; i++) {
                Location newLocation = new Location(startLocation.getWorld(), x, y, z - i);
                if (newLocation.getBlock().getType().equals(stopMaterial)) {
                    res.add(new Location(startLocation.getWorld(), x, y - 1, z - i + 1));
                    break;
                }
            }

        }
        if (res.size() == 2) {
            return res;
        } else {
            return null;
        }
    }

    public void drawLineBetweenPlayer(PlayerMoveEvent event) {
        // check switch is on or off
        //(Material) player.getMetadata("lineMaterial").get(0).value();
        int status = 0;
        // test if roadSwitch is null
        if (event.getPlayer().getMetadata("roadSwitch").size() == 0) {
            event.getPlayer().sendMessage("Please set roadSwitch");
            // set to 0
            event.getPlayer().setMetadata("roadSwitch", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), 0));
        } else {
            status = (Integer) event.getPlayer().getMetadata("roadSwitch").get(0).value();
        }
        if (status == 0) {
            return;

        }

//        event.getPlayer().sendMessage(event.getPlayer().getFacing().toString());
        Material stopMaterial = Material.SMOOTH_STONE_SLAB;
        BlockFace facing = event.getPlayer().getFacing();
        int maxBlock = 50;
        Location startLocation = event.getPlayer().getLocation();
        ArrayList<Location> res = scanLine(stopMaterial, facing, maxBlock, startLocation);
        if (res == null) {
//            event.getPlayer().sendMessage("res is null");
            return;
        }
//        event.getPlayer().sendMessage("res is not null and size is " + res.size());
        Location firstPoint = res.get(0);
//        event.getPlayer().sendMessage("firstPoint is " + firstPoint.getX() + " " + firstPoint.getY() + " " + firstPoint.getZ());

        Location secondPoint = res.get(1);
        // middle point


//        event.getPlayer().sendMessage("secondPoint is " + secondPoint.getX() + " " + secondPoint.getY() + " " + secondPoint.getZ());
        // test linematerial is null
        if (event.getPlayer().getMetadata("lineMaterial").size() == 0) {
            event.getPlayer().sendMessage("lineMaterial is null and set to STONE");
            event.getPlayer().setMetadata("lineMaterial", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), Material.STONE));

        }
        drawLine(firstPoint, secondPoint, (Material) event.getPlayer().getMetadata("lineMaterial").get(0).value(), event.getPlayer());

        // draw mid point
        Location midPoint = new Location(startLocation.getWorld(), (firstPoint.getX() + secondPoint.getX()) / 2, (firstPoint.getY() + secondPoint.getY()) / 2, (firstPoint.getZ() + secondPoint.getZ()) / 2);
        // test roadLine is null
        if (event.getPlayer().getMetadata("roadLine").size() == 0) {
            event.getPlayer().sendMessage("middle line is null and set it to off");
            event.getPlayer().setMetadata("roadLine", new FixedMetadataValue(Welcome.getProvidingPlugin(Welcome.class), "off"));
        }
        if (event.getPlayer().getMetadata("roadLine").get(0).value().equals("single")) {
            midPoint.getBlock().setType((Material) Objects.requireNonNull(event.getPlayer().getMetadata("midLineMaterial").get(0).value()));
        } else if (event.getPlayer().getMetadata("roadLine").get(0).value().equals("double")) {
            // two point on the left and right 1 block
            Location left, right;
            if (facing.equals(BlockFace.EAST) || facing.equals(BlockFace.WEST)) {
                left = new Location(startLocation.getWorld(), midPoint.getX(), midPoint.getY(), midPoint.getZ() + 1);
                right = new Location(startLocation.getWorld(), midPoint.getX(), midPoint.getY(), midPoint.getZ() - 1);
            } else {
                left = new Location(startLocation.getWorld(), midPoint.getX() + 1, midPoint.getY(), midPoint.getZ());
                right = new Location(startLocation.getWorld(), midPoint.getX() - 1, midPoint.getY(), midPoint.getZ());
            }

            left.getBlock().setType((Material) Objects.requireNonNull(event.getPlayer().getMetadata("midLineMaterial").get(0).value()));
            right.getBlock().setType((Material) Objects.requireNonNull(event.getPlayer().getMetadata("midLineMaterial").get(0).value()));
        }

    }


    public void listenGate(PlayerMoveEvent event) {
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

    @EventHandler
    public void PlayerPosListener(PlayerMoveEvent event) {

        drawLineBetweenPlayer(event);
        listenGate(event);

    }


}
