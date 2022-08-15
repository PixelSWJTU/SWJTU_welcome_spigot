package com.swjtu.welcome;

import org.bukkit.Color;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

public class utils {

    public void playParticle(Player player) {
        Location loc = player.getLocation();
        int X = player.getLocation().getBlockX();
        int Y = player.getLocation().getBlockY();
        int Z = player.getLocation().getBlockZ();
        World world = player.getWorld();

        int offset = 5;
        float yaw = player.getLocation().getYaw();
        if (yaw > -45 && yaw < 45) {
            Z += offset;
        } else if (yaw > 45 && yaw < 135) {
            X -= offset;
        } else if (yaw > 135 || yaw < -135) {
            Z -= offset;
        } else if (yaw > -135 && yaw < -45) {
            X += offset;
        }



        Location newLoc1 = new Location(world, X, Y-1, Z);
//        Location newLoc2 = new Location(world, X, Y-1, Z);
//        Location newLoc3 = new Location(world, X, Y-1, Z);

        Firework f1 = player.getWorld().spawn(newLoc1, Firework.class);
//        Firework f2 = player.getWorld().spawn(newLoc2, Firework.class);
//        Firework f3 = player.getWorld().spawn(newLoc3, Firework.class);

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
//        f2.setFireworkMeta(fm);
//        f3.setFireworkMeta(fm);
    }


}
