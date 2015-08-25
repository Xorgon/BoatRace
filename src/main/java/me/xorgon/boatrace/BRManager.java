package me.xorgon.boatrace;

import lombok.Getter;
import lombok.Setter;
import me.xorgon.boatrace.util.Race;
import me.xorgon.boatrace.util.ScheduledChecks;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elijah on 24/08/2015.
 */
@Getter
@Setter
public class BRManager {

    List<Race> races = new ArrayList<>();

    ScheduledChecks checks;

    public BRManager() {
        checks = new ScheduledChecks();
    }
}