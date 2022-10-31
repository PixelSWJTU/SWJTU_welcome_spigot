package com.swjtu.welcome.commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import io.papermc.lib.PaperLib;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class BlockSetter implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 5) {
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
                World world = sender.getServer().getWorld(worldName);
                assert world != null;
                PaperLib.getChunkAtAsync(world, X, Z);

                world.getBlockAt(X, Y, Z).setType(material);
            } catch (Exception e) {
                sender.sendMessage("§6§l出错了请检查方块和坐标！");
                return false;
            }

            sender.sendMessage("§6§l你已经设置了点(" + X + "," + Y + "," + Z + ")的物品为" + MaterialName + "！");

        } else if (args.length == 4) {
            String MaterialName = args[0];
            String worldName = args[1];
            String filename = args[2];
            int Y = Integer.parseInt(args[3]);
            // open file and read lines
            // check if file exist
            List<List<Integer>> co = new ArrayList<>();
            File file = new File(filename);
            if (!file.exists()) {
                sender.sendMessage("§6§l文件不存在！");
                return false;
            }
            try {
                Scanner input = new Scanner(file);
                List<String> lines = new ArrayList<>();
                while (input.hasNextLine()) {
                    lines.add(input.nextLine());
                }
                int size = lines.size();
                sender.sendMessage("§6§l共计读取" + size + "个点！");
                input.close();

                for (String l : lines) {
                    // split by ,
                    List<Integer> c = Arrays.stream(l.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                    co.add(c);
                }


            } catch (FileNotFoundException e) {
                sender.sendMessage("§6§l文件不存在！！");
                return false;
            }
            Material material = Material.matchMaterial(MaterialName);
            if (material == null) {
                sender.sendMessage("§6§l你输入的物品不存在！");
                return false;
            }
            World world = sender.getServer().getWorld(worldName);
            if (world == null) {
                sender.sendMessage("§6§l你输入的世界不存在！");
                return false;
            }
            BukkitWorld bukkitWorld = new BukkitWorld(world);
            EditSession session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(bukkitWorld, -1);
            sender.sendMessage("§6§l开始设置方块！");

            for (List<Integer> c : co) {
                BlockVector3 vec = BlockVector3.at(c.get(0), Y, c.get(1));
                try {
                    BlockType blockType = BlockTypes.get("minecraft:" + material.name().toLowerCase());
                    assert blockType != null;
                    session.setBlock(vec, blockType.getDefaultState());
                    sender.sendMessage(session.getBlock(vec).toString());
                } catch (MaxChangedBlocksException e) {
                    sender.sendMessage("§6§l出错了请检查方块和坐标！");
                }
            }

            session.commit();
            session.close();
            sender.sendMessage("§6§l设置完成！");

        } else {
            sender.sendMessage("§6§用错了！" + args.length);
            return false;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        int len = args.length;
        int x = sender.getServer().getPlayer(sender.getName()).getLocation().getBlockX();
        int y = sender.getServer().getPlayer(sender.getName()).getLocation().getBlockY();
        int z = sender.getServer().getPlayer(sender.getName()).getLocation().getBlockZ();
        // /blocksetter <x> <y> <z> <material> <world>
        // /blocksetter SMOOTH_STONE_SLAB world /home/onerain233/mc/233.csv 65
        if (args.length == 1) {
            return Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList());
        } else if (args.length == 2) {
            return sender.getServer().getWorlds().stream().map(World::getName).collect(Collectors.toList());
        } else if (args.length == 3) {
            // list file in plugin folder
            File file = new File("plugins/Welcome/BlockSetter");
            if (!file.exists()) {
                file.mkdirs();
            }
            return Arrays.stream(Objects.requireNonNull(file.listFiles())).map(File::getName).collect(Collectors.toList());

        } else if (args.length == 4) {
            return Arrays.asList(String.valueOf(y));
        }
        return null;
    }
}
