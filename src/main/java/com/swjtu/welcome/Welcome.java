package com.swjtu.welcome;

import com.swjtu.welcome.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public final class Welcome extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // hello world

        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
        // register cmd
        Bukkit.getPluginCommand("getEssentialItem").setExecutor(new getEssentialItem());
        Bukkit.getPluginCommand("maskChecker").setExecutor(new maskChecker());
        Bukkit.getPluginCommand("scamScenarios").setExecutor(new ScamScenarios());
        Bukkit.getPluginCommand("GateSetter").setExecutor(new setGatePos());
        Bukkit.getPluginCommand("setReportStatus").setExecutor(new setReportStatus());
        Bukkit.getPluginCommand("MultiCmdExec").setExecutor(new MultiCmdExec());
        Bukkit.getPluginCommand("BlockSetter").setExecutor(new BlockSetter());
        // Read config
        saveDefaultConfig();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye World!");
    }

}
