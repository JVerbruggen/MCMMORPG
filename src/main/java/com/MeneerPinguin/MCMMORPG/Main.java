package com.MeneerPinguin.MCMMORPG;

import com.MeneerPinguin.MCMMORPG.command.DefaultCommandExecutor;
import com.MeneerPinguin.MCMMORPG.entity.VirtualEntityManager;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main main;

    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        main = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, VirtualEntityManager::tick, 1l, 1l);

        getCommand("rpg").setExecutor(new DefaultCommandExecutor());

        getLogger().info("Started");
    }

    @Override
    public void onDisable() {
        VirtualEntityManager.destroyAll();

        getLogger().info("Stopped");
    }

    public static Main getInstance(){
        return main;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
