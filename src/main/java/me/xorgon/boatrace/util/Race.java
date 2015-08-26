package me.xorgon.boatrace.util;

import com.supaham.commons.bukkit.text.FancyMessage;
import com.supaham.commons.bukkit.title.Title;
import lombok.Getter;
import me.xorgon.boatrace.BoatRacePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Elijah on 24/08/2015.
 */
@Getter
public class Race {

    private Map<String, Player> players = new HashMap<>();
    private CourseProperties.Course course;
    private int maxCheckpoints = 8;
    private Map<String, Integer> playerProgress = new HashMap<>();

    private boolean starting = false;
    private boolean racing = false;
    private String winner;

    private BukkitTask task;
    private BukkitTask startTimer;

    private BukkitTask countdown;
    private int toStart = 3;

    private static final int MAX_PLAYERS = 6;

    public Race(CourseProperties.Course course) {
        this.course = course;
    }

    public Runnable testPlayers() {
        return new Runnable() {
            @Override
            public void run() {
                if (racing) {
                    for (String name : players.keySet()) {
                        List<Vector> checkpoints = course.getCheckpoints();
                        for (Vector vector : checkpoints) {
                            if (players.get(name).getLocation().toVector().distance(vector) < 5) {
                                int index = checkpoints.indexOf(vector);
                                if (playerProgress.get(name) + 1 == index) {
                                    playerProgress.remove(name);
                                    if (index + 1 == maxCheckpoints) {
                                        endRace();
                                    } else {
                                        playerProgress.put(name, index);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    public boolean addPlayer(Player player) {
        if (players.size() == 0) {
            Bukkit.getScheduler().runTaskLater(BoatRacePlugin.getInstance(), new Runnable() {
                @Override
                public void run() {
                    startRace();
                }
            }, 60 * 20);
        }
        if (players.containsKey(player.getName())) {
            return true;
        } else if (players.size() == MAX_PLAYERS) {
            startTimer.cancel();
            startRace();
            return false;
        } else {
            players.put(player.getName(), player);
            return true;
        }
    }

    public void startRace() {
        task = Bukkit.getScheduler().runTaskTimer(BoatRacePlugin.getInstance(), testPlayers(), 0, 5);
        Vector start = course.getCheckpoints().get(1).clone().subtract(course.getCheckpoints().get(0)).normalize();
        Vector perp = start.crossProduct(new Vector(0, 1, 0)).normalize().multiply(1);
        Location spawnStart = course.getCheckpoints().get(0).clone().subtract(perp.clone().multiply((double) players.size() / 2.0)).add(start).toLocation(Bukkit.getWorld(course.getWorld()));
        World world = spawnStart.getWorld();

        toStart = 3;
        starting = true;

        countdown = Bukkit.getScheduler().runTaskTimer(BoatRacePlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player player : players.values()) {
                    Title.sendTimes(player, 0, 10, 10);
                    if (toStart == 0) {
                        Title.sendTitle(player, new FancyMessage("GO!"));
                    } else {
                        Title.sendTitle(player, new FancyMessage(String.valueOf(toStart)));
                    }
                }
                toStart--;
                if (toStart < 0) {
                    starting = false;
                    racing = true;
                    countdown.cancel();
                }
            }
        }, 20, 20);

        int n = 0;
        for (Player player : players.values()) {

            Title.sendTimes(player, 0, 75, 10);
            Title.sendTitle(player, new FancyMessage("Get ready"));

            Location loc = spawnStart.clone().add(perp.clone().multiply(n)).setDirection(start);
            player.teleport(loc);
            Boat boat = (Boat) world.spawnEntity(loc, EntityType.BOAT);
            boat.setPassenger(player);
            n++;

            playerProgress.put(player.getName(), 0);
        }
    }

    public void endRace() {
        racing = false;
        task.cancel();
        Double distance = null;
        Location loc = course.getCheckpoints().get(course.getCheckpoints().size() - 1).toLocation(Bukkit.getWorld(course.getWorld()));
        for (Player player : players.values()) {
            if (distance == null) {
                distance = loc.distance(player.getLocation());
            } else {
                if (loc.distance(player.getLocation()) < distance) {
                    distance = loc.distance(player.getLocation());
                    winner = player.getName();
                }
            }
        }

        for (Player player : players.values()) {
            Title.sendTimes(player, 0, 75, 10);
            Title.sendTitle(player, new FancyMessage(ChatColor.GREEN + winner + ChatColor.YELLOW + " wins!"));
        }
    }
}