package me.xorgon.boatrace;

import me.xorgon.boatrace.util.Race;
import me.xorgon.boatrace.util.ScheduledChecks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

/**
 * Created by Elijah on 24/08/2015.
 */
public class BRListener implements Listener {

    private BoatRacePlugin plugin = BoatRacePlugin.getInstance();
    private BRManager manager = plugin.getManager();

    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        for (Race race : manager.getRaces()) {
            if (race.isRacing() || race.isStarting()) {
                for (Player player : race.getPlayers().values()) {
                    if (event.getEntity().equals(player)) {
                        event.getDismounted().setPassenger(event.getEntity());
                    }
                }
            }
        }
    }

}
