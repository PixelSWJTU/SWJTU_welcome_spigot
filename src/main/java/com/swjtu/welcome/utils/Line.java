package com.swjtu.welcome.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Line {
    public static void drawLine(Location firstPoint, Location secondPoint, Material material, Player player) {
        List<Location> list = new ArrayList<>();
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
            for (int domstep = 0; domstep <= dx; domstep++) {
                tipx = x1 + domstep * (x2 - x1 > 0 ? 1 : -1);
                tipy = (int) Math.round(y1 + domstep * ((double) dy) / ((double) dx) * (y2 - y1 > 0 ? 1 : -1));
                tipz = (int) Math.round(z1 + domstep * ((double) dz) / ((double) dx) * (z2 - z1 > 0 ? 1 : -1));
                list.add(new Location(firstPoint.getWorld(), tipx, tipy, tipz));
            }
        } else if (dMax == dy) {
            for (int domstep = 0; domstep <= dy; domstep++) {
                tipy = y1 + domstep * (y2 - y1 > 0 ? 1 : -1);
                tipx = (int) Math.round(x1 + domstep * ((double) dx) / ((double) dy) * (x2 - x1 > 0 ? 1 : -1));
                tipz = (int) Math.round(z1 + domstep * ((double) dz) / ((double) dy) * (z2 - z1 > 0 ? 1 : -1));
                list.add(new Location(firstPoint.getWorld(), tipx, tipy, tipz));
            }
        } else /* if (dMax == dz) */ {
            for (int domstep = 0; domstep <= dz; domstep++) {
                tipz = z1 + domstep * (z2 - z1 > 0 ? 1 : -1);
                tipy = (int) Math.round(y1 + domstep * ((double) dy) / ((double) dz) * (y2 - y1 > 0 ? 1 : -1));
                tipx = (int) Math.round(x1 + domstep * ((double) dx) / ((double) dz) * (x2 - x1 > 0 ? 1 : -1));
                list.add(new Location(firstPoint.getWorld(), tipx, tipy, tipz));
            }
        }
//        Material material = (Material) player.getMetadata("lineMaterial").get(0).value();
        for (Location loc : list) {
            loc.getBlock().setType(material);
        }
    }
}
