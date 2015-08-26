package me.xorgon.boatrace.util;

import lombok.Getter;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Elijah on 26/08/2015.
 */
@Getter
public class CourseProperties {

    private List<Course> courses = new ArrayList<>();

    @Getter
    public static class Course {
        private String id;
        private String world;
        private Map<Integer, Vector> checkpoints = new HashMap<>();
    }

}
