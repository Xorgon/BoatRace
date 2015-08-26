package me.xorgon.boatrace.util;

import com.supaham.commons.bukkit.serializers.VectorSerializer;
import lombok.Getter;
import org.bukkit.util.Vector;
import pluginbase.config.annotation.SerializeWith;

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
        @SerializeWith(VectorSerializer.class)
        private List<Vector> checkpoints = new ArrayList<>();
    }
}
