package me.xorgon.boatrace;

import com.supaham.commons.bukkit.utils.SerializationUtils;
import lombok.Getter;
import lombok.Setter;
import me.xorgon.boatrace.util.CourseProperties;
import me.xorgon.boatrace.util.Race;
import me.xorgon.boatrace.util.ScheduledChecks;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elijah on 24/08/2015.
 */
@Getter
@Setter
public class BRManager {

    private BoatRacePlugin plugin = BoatRacePlugin.getInstance();

    private Map<String, Race> races = new HashMap<>();
    private Map<String, CourseProperties.Course> courses = new HashMap<>();

    private ScheduledChecks checks;

    private File file;
    private CourseProperties config;

    public BRManager(File file) {
        this.file = file;
        load();
        checks = new ScheduledChecks();
    }

    public Race readyRace(String courseName){
        return races.put(courseName, new Race(courses.get(courseName)));
    }

    public void addPlayer(Player player, String courseName){
        if (races.containsKey(courseName)){
            races.get(courseName).addPlayer(player);
        } else {
            readyRace(courseName).addPlayer(player);
        }
    }

    public void load(){
        config = SerializationUtils.loadOrCreateProperties(plugin.getLog(), file, new CourseProperties(), "settings");
        for (CourseProperties.Course course : config.getCourses()) {
            courses.put(course.getId().toLowerCase(), course);
        }
    }

}