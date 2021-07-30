package com.MeneerPinguin.MCMMORPG;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main main;

    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        main = this;

        getLogger().info("Started");
    }

    @Override
    public void onDisable() {


        getLogger().info("Stopped");
    }

    public static Main getInstance(){
        return main;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
