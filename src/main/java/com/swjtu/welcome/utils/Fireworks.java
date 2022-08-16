package com.swjtu.welcome.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class Fireworks {
    private Player player = null;
    private Location location = null;
    private World world = null;

    public Fireworks(Player player) {
        this.player = player;
    }

    public void playParticle() {
        int X = player.getLocation().getBlockX();
        int Y = player.getLocation().getBlockY();
        int Z = player.getLocation().getBlockZ();
        World world = player.getWorld();
        int Z1 = player.getLocation().getBlockZ();
        int X1 = player.getLocation().getBlockX();

        int offset = 5;
        int offset1 = 20;
        float yaw = player.getLocation().getYaw();
        if (yaw > -45 && yaw < 45) {
            Z += offset;
            Z1 += offset1;
        } else if (yaw > 45 && yaw < 135) {
            X -= offset;
            X1 -= offset1;
        } else if (yaw > 135 || yaw < -135) {
            Z -= offset;
            Z1 -= offset1;
        } else if (yaw > -135 && yaw < -45) {
            X += offset;
            X1 += offset1;
        }

        Location newLoc1 = new Location(world, X, Y-1, Z); // 在脚底下炸开
        Location newLoc2 = new Location(world, X-10, Y, Z1);
        Location newLoc3 = new Location(world, X+10, Y, Z1);

        Firework f1 = player.getWorld().spawn(newLoc1, Firework.class);
        Firework f2 = player.getWorld().spawn(newLoc2, Firework.class);
        Firework f3 = player.getWorld().spawn(newLoc3, Firework.class);

        FireworkMeta fm = f1.getFireworkMeta();
        fm.addEffect(
                FireworkEffect.builder()
                        .flicker(true)
                        .trail(true)
//                        .with(FireworkEffect.Type.BALL_LARGE)
                        .with(FireworkEffect.Type.BURST)
                        .withColor(Color.BLUE).build());
        fm.setPower(1);
        f1.setFireworkMeta(fm);
        f2.setFireworkMeta(fm);
        f3.setFireworkMeta(fm);
    }
}
