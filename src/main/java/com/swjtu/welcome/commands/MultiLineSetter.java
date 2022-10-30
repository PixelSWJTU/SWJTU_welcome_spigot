package com.swjtu.welcome.commands;

import com.swjtu.welcome.utils.GISParser;
import com.swjtu.welcome.utils.Line;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class MultiLineSetter implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("TODO");
        return false;
        // multiline http://example.com/gis.txt
//        if (args.length != 3) {
//            sender.sendMessage("§6§用错了！");
//            return false;
//        }
//        String type = args[0];
//        String worldName = args[1];
//        int y = Integer.parseInt(args[2]);
//        String url = args[3];
//        if (!type.equals("multiline")) {
//            sender.sendMessage("§6§l你输入的类型不存在！" + type);
//            return false;
//        }
//        World world = sender.getServer().getWorld(worldName);
//        // download file from url
//        URL urlObj = null;
//        try {
//            urlObj = new URL(url);
//            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
//            connection.connect();
//            int responseCode = connection.getResponseCode();
//            if (responseCode != 200) {
//                sender.sendMessage("§6§l下载文件失败！" + responseCode);
//                return false;
//            }
//            // create file
//            // random filename
//            Random random = new Random();
//            String filename = "gis" + random.nextInt(100000) + ".txt";
//            // save file
//            InputStream inputStream = connection.getInputStream();
//            String filepath = "plugins/Welcome/gis/" + filename;
//            // check if path exists
//            File file = new File(filepath);
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            // write file
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = inputStream.read(buffer)) != -1) {
//                fileOutputStream.write(buffer, 0, len);
//            }
//            fileOutputStream.close();
//            inputStream.close();
//            sender.sendMessage("§6§l下载的文件" + filename + "！");
//            GISParser gisParser = new GISParser();
//            List<List<List<Float>>> result = gisParser.parse(filepath);
//            sender.sendMessage("§6§l解析出" + result.size() + "GROUPS！");
//            for (int i = 0; i < result.size(); i++) {
//                // line
//                List<List<Float>> line = result.get(i);
//                sender.sendMessage("§6§l第" + i + "组有" + line.size() + "个点！");
//                // set line for adjecent points
//                for (int j = 0; j < line.size() - 1; j++) {
//                    List<Float> point1 = line.get(j);
//                    List<Float> point2 = line.get(j + 1);
//                    Location location1 = new Location(world, point1.get(0), y, point1.get(1));
//                    Location location2 = new Location(world, point2.get(0), y, point2.get(1));
//                    Line.drawLine(location1, location2, Material.SMOOTH_STONE_SLAB, (Player) sender);
//                    sender.sendMessage("§6§l第" + j + "个点和第" + (j + 1) + "个点连线！");
//                }
//
//            }
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
