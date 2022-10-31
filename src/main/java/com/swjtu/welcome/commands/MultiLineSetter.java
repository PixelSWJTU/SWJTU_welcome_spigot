package com.swjtu.welcome.commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import com.swjtu.welcome.utils.GISParser;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MultiLineSetter implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // multiline world y materialname url
        if (args.length != 4) {
            sender.sendMessage("§6§用错了！");
            return false;
        }
//        String type = args[0];
        String worldName = args[0];
        int y = Integer.parseInt(args[1]);
        String materialName = args[2];
        String filepath = args[3];


        World world = sender.getServer().getWorld(worldName);
        sender.sendMessage("§6§l文件路径" + filepath + "！");
        GISParser gisParser = new GISParser();
        List<List<List<Float>>> result = null;
        try {
            result = gisParser.parse(filepath);
        } catch (FileNotFoundException ex) {
            sender.sendMessage("§6§l文件不存在！");
            return false;
        }
        sender.sendMessage("§6§l解析出" + result.size() + "GROUPS！");
        EditSession session = WorldEdit.getInstance().newEditSession(new BukkitWorld(world));
        Material material = Material.getMaterial(materialName);
        BlockType blockType = BlockTypes.get("minecraft:" + materialName.toLowerCase());
        assert blockType != null;
        Pattern pattern = blockType.getDefaultState();
        for (int i = 0; i < result.size(); i++) {
            // line
            List<List<Float>> line = result.get(i);
            sender.sendMessage("§6§l第" + i + "组有" + line.size() + "个点！");
            // set line for adjecent points
            List<BlockVector3> vector3s = new ArrayList<>();
            if (line.size() == 2) {
                BlockVector3 start = BlockVector3.at(line.get(0).get(0), y, line.get(0).get(1));
                BlockVector3 end = BlockVector3.at(line.get(1).get(0), y, line.get(1).get(1));
                try {
                    session.drawLine(pattern, start, end, 0, true);
                } catch (MaxChangedBlocksException e) {
                    sender.sendMessage("§6§l设置失败！");
                    return false;
                }
                continue;
            }
            for (int j = 0; j < line.size() - 1; j++) {
                vector3s.add(BlockVector3.at(line.get(j).get(0), y, line.get(j).get(1)));
            }
            try {
                session.drawLine(pattern, vector3s, 0, true);
            } catch (Exception e) {
                sender.sendMessage("§6§l设置方块失败！" + e.getMessage());
                return false;
            }
        }
        session.commit();
        session.close();
        sender.sendMessage("§6§l设置完成！");
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
