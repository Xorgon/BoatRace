package me.xorgon.boatrace;

import com.supaham.commons.bukkit.SimpleCommonPlugin;

import java.io.File;

/**
 * Created by Elijah on 24/08/2015.
 */
public class BoatRacePlugin extends SimpleCommonPlugin<BoatRacePlugin> {

    private static BoatRacePlugin instance;
    private static final String COMMAND_PREFIX = "boatrace";

    private BRManager manager;

    public BoatRacePlugin() {
        super(BoatRacePlugin.class, COMMAND_PREFIX);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        manager = new BRManager();
        registerEvents(new BRListener());
        getCommand("boatrace").setExecutor(new BRCommand());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static BoatRacePlugin getInstance() {
        return instance;
    }

    public BRManager getManager() {
        return manager;
    }
}