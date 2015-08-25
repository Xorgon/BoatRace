package me.xorgon.boatrace.util;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elijah on 25/08/2015.
 */
@Getter
public class Course {

    private Map<Integer, Location> checkpoints = new HashMap<>();

}
