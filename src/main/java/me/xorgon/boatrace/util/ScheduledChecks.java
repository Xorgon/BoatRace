package me.xorgon.boatrace.util;

import me.xorgon.boatrace.BRManager;
import me.xorgon.boatrace.BoatRacePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

/**
 * Created by Elijah on 25/08/2015.
 */
public class ScheduledChecks {

    BoatRacePlugin plugin = BoatRacePlugin.getInstance();
    BRManager manager ;

    BukkitTask checks;

    public ScheduledChecks(BRManager manager) {
        this.manager = manager;
        checks = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for (Race race : manager.getRaces().values()) {
                    if (race.isStarting()) {
                        for (Player player : race.getPlayers().values()) {
                            player.getVehicle().setVelocity(new Vector(0, 0, 0));
                        }
                    }
                    race.testPlayers();
                }
            }
        }, 0, 1);
    }

    public BukkitTask getChecks() {
        return checks;
    }
}