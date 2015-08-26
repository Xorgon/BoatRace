package me.xorgon.boatrace;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Elijah on 24/08/2015.
 */
public class BRCommand implements CommandExecutor {
    BoatRacePlugin plugin = BoatRacePlugin.getInstance();
    BRManager manager = plugin.getManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            boatRace(player, args);
        }
        return true;
    }

    private boolean boatRace(Player player, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("list")){
                manager.getCourses().keySet().forEach((s) -> player.sendMessage(ChatColor.GREEN + s));
            } else if (args.length == 1){
                if (manager.getCourses().containsKey(args[0])) {
                    manager.addPlayer(player, args[0]);
                } else {
                    player.sendMessage(ChatColor.RED + "That course does not exist.");
                }
            }
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "Correct usage: /boatrace <command>");
            player.sendMessage(ChatColor.RED + "list, <course name>");
            return true;
        }
    }
}
