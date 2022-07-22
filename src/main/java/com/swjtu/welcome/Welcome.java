package com.swjtu.welcome;

import com.swjtu.welcome.commands.getEssentialItem;
import com.swjtu.welcome.commands.maskChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public final class Welcome extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // hello world
        getLogger().info("Hello World!");

        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
        // register cmd
        Bukkit.getPluginCommand("getEssentialItem").setExecutor(new getEssentialItem());
        Bukkit.getPluginCommand("maskChecker").setExecutor(new maskChecker());

        // Read config
        saveDefaultConfig();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye World!");
    }

}
