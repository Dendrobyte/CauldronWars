package com.redstoneoinkcraft.me.commands;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Created by Mark on 5/15/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class ShoutCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getInstance().getPrefix() + "You must be a player to use this command.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("cwshout")) {
            Player player = (Player) sender;
            RunningArenaManager ram = RunningArenaManager.getManager();
            RunningArena ra = ram.isInGame(player);
            if (ra == null) {
                player.sendMessage(Main.getInstance().getPrefix() + "You must be in a game to shout from it.");
                return false;
            }
            if (ra != null) {
                // TODO: Add a shout cooldown.
                String message = "";
                for (int i = 0; i < args.length; i++) {
                    message += args[i];
                    message += " ";
                }
                for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
                    player1.sendMessage("§7[§bC§cW§7]§8(§7Global§8)§2" + player.getName() + "§7> " + message);
                }
                return true;
            }
            return true;
        }
        return false;
    }
}
