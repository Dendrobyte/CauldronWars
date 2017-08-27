package com.redstoneoinkcraft.me.commands;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.ArenaManager;
import com.redstoneoinkcraft.me.arenas.ArenaPoints;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mark on 3/19/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class ArenaCreateCommand implements CommandExecutor {

    String prefix = Main.getInstance().getPrefix();

    /*
     * Each arena needs the following:
     * An id, getArenaSize from arena manager
     * A name
     * A location/teleport location
     * Spawnpoints, for both red team and blue team
     * Cauldrons, for both red team and blue team
     * A lobby
     * Bottle spawning bounds, two corner blocks
     */

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + "Sorry, only players are able to use this command!");
            return false;
        }
        FileConfiguration config = Main.getInstance().getConfig();
        ArenaManager am = ArenaManager.getManager();
        Player player = (Player) sender;
        ItemStack pointWand = new ItemStack(Material.FEATHER, 1);
        ItemMeta pointWandMeta = pointWand.getItemMeta();
        pointWandMeta.setDisplayName("§9§lCWars Arena Creation Wand");
        pointWand.setItemMeta(pointWandMeta);

        if (cmd.getName().equalsIgnoreCase("cwarsarena")) {
            if (!sender.hasPermission("cauldronwars.arena")) {
                player.sendMessage(Main.getInstance().getPrefix() + "Sorry! You do not have access to that command.");
                player.sendMessage(Main.getInstance().getPrefix() + "§Required permission: cauldronwars.arena");
                return false;
            }
            if (args.length == 0) {
                player.sendMessage(prefix + "Welcome to Cauldron Wars arena creation!");
                player.sendMessage(prefix + "Please create your arena! /cwarsarena create <arena_name>");
                player.sendMessage(prefix + "Make sure you are standing WITHIN the arena you want to create.");
                return true;
            }
            // Command to list steps after initial creation.

            // Removal command

            // Creation command
            if (args[0].equalsIgnoreCase("create")) {
                if(am.getPointStorage().containsKey(player.getUniqueId())){
                    player.sendMessage(prefix + "It appears as though you are already in creation mode! Here's another wand anyway.");
                    player.getInventory().setItemInMainHand(pointWand);
                    return true;
                }
                    player.sendMessage(prefix + "Arena creation mode activated!");
                    am.getPointStorage().put(player.getUniqueId(), new ArenaPoints(null, null, null, null, null, null, null));
                    player.sendMessage("Use the editing wand to set a §llobby location"); // From then on, use the interact event to carry forth.
                    player.getInventory().setItemInMainHand(pointWand);
                    return true;
            }

            // Command to place all points in the configuration file. args[1] is the arena name.
            if (args[0].equalsIgnoreCase("confirm")) {
                if (args.length == 1) {
                    sender.sendMessage(prefix + "Please provide an arena name to confirm.");
                    return false;
                }

                if(config.getStringList("list-of-arenas").contains(args[1])){
                    player.sendMessage(prefix + "Sorry, but it appears that arena already exists!");
                    return false;

                }

                if(!am.getPointStorage().containsKey(player.getUniqueId())){
                    player.sendMessage(prefix + "You have not entered arena creation! /cwarsarena create <name>");
                    return false;
                }

                // All of these values SHOULD have been added in the interact listener by clicking on blocks.
                if (am.getPointStorage().get(player.getUniqueId()).getBlueCauldron() == null || am.getPointStorage().get(player.getUniqueId()).getRedCauldron() == null || am.getPointStorage().get(player.getUniqueId()).getBlueSpawn() == null || am.getPointStorage().get(player.getUniqueId()).getRedSpawn() == null || am.getPointStorage().get(player.getUniqueId()).getLobby() == null || am.getPointStorage().get(player.getUniqueId()).getBounds1() == null || am.getPointStorage().get(player.getUniqueId()).getBounds2() == null) {
                    player.sendMessage(prefix + "Please make sure that all points have been clicked on!");
                    player.sendMessage(prefix + "Use /cwarsarena edit to make changes");
                    return false;
                }

                // And if they do... (Could I just use else? Lol)
                if (am.getPointStorage().get(player.getUniqueId()).getBlueCauldron() != null && am.getPointStorage().get(player.getUniqueId()).getRedCauldron() != null && am.getPointStorage().get(player.getUniqueId()).getBlueSpawn() != null && am.getPointStorage().get(player.getUniqueId()).getRedSpawn() != null && am.getPointStorage().get(player.getUniqueId()).getLobby() != null && am.getPointStorage().get(player.getUniqueId()).getBounds1() != null && am.getPointStorage().get(player.getUniqueId()).getBounds2() != null) {
                    // Create and set paths on the configuration!
                    String arenaName = args[1];
                    config.createSection("arenas." + arenaName);
                    /*config.createSection("arenas." + arenaName + ".world");
                    config.createSection("arenas." + arenaName + ".lobby");

                    config.createSection("arenas." + arenaName + ".spawnpoints");
                    config.createSection("arenas." + arenaName + ".spawnpoints.teamBlue");
                    config.createSection("arenas." + arenaName + ".spawnpoints.teamRed");

                    config.createSection("arenas." + arenaName + ".cauldrons");
                    config.createSection("arenas." + arenaName + ".cauldrons.teamBlue");
                    config.createSection("arenas." + arenaName + ".cauldrons.teamRed");

                    config.createSection("arenas." + arenaName + ".bounds");
                    config.createSection("arenas." + arenaName + ".bounds.firstbound");
                    config.createSection("arenas." + arenaName + ".bounds.secondbound");*/

                    //config.getStringList("list-of-arenas").add(arenaName);

                    // All location values!
                    config.set("arenas." + arenaName + ".world", am.getPointStorage().get(player.getUniqueId()).getBlueSpawn().getWorld().getName());
                    config.set("arenas." + arenaName + ".lobby.x", am.getPointStorage().get(player.getUniqueId()).getLobby().getBlockX());
                    config.set("arenas." + arenaName + ".lobby.y", am.getPointStorage().get(player.getUniqueId()).getLobby().getBlockY());
                    config.set("arenas." + arenaName + ".lobby.z", am.getPointStorage().get(player.getUniqueId()).getLobby().getBlockZ());
                    config.set("arenas." + arenaName + ".spawnpoints.teamBlue.x", am.getPointStorage().get(player.getUniqueId()).getBlueSpawn().getBlockX());
                    config.set("arenas." + arenaName + ".spawnpoints.teamBlue.y", am.getPointStorage().get(player.getUniqueId()).getBlueSpawn().getBlockY());
                    config.set("arenas." + arenaName + ".spawnpoints.teamBlue.z", am.getPointStorage().get(player.getUniqueId()).getBlueSpawn().getBlockZ());
                    config.set("arenas." + arenaName + ".spawnpoints.teamRed.x", am.getPointStorage().get(player.getUniqueId()).getRedSpawn().getBlockX());
                    config.set("arenas." + arenaName + ".spawnpoints.teamRed.y", am.getPointStorage().get(player.getUniqueId()).getRedSpawn().getBlockY());
                    config.set("arenas." + arenaName + ".spawnpoints.teamRed.z", am.getPointStorage().get(player.getUniqueId()).getRedSpawn().getBlockZ());
                    config.set("arenas." + arenaName + ".cauldrons.teamBlue.x", am.getPointStorage().get(player.getUniqueId()).getBlueCauldron().getBlockX());
                    config.set("arenas." + arenaName + ".cauldrons.teamBlue.y", am.getPointStorage().get(player.getUniqueId()).getBlueCauldron().getBlockY());
                    config.set("arenas." + arenaName + ".cauldrons.teamBlue.z", am.getPointStorage().get(player.getUniqueId()).getBlueCauldron().getBlockZ());
                    config.set("arenas." + arenaName + ".cauldrons.teamRed.x", am.getPointStorage().get(player.getUniqueId()).getRedCauldron().getBlockX());
                    config.set("arenas." + arenaName + ".cauldrons.teamRed.y", am.getPointStorage().get(player.getUniqueId()).getRedCauldron().getBlockY());
                    config.set("arenas." + arenaName + ".cauldrons.teamRed.z", am.getPointStorage().get(player.getUniqueId()).getRedCauldron().getBlockZ());
                    config.set("arenas." + arenaName + ".bounds.firstbound.x", am.getPointStorage().get(player.getUniqueId()).getBounds1().getBlockX());
                    config.set("arenas." + arenaName + ".bounds.firstbound.y", am.getPointStorage().get(player.getUniqueId()).getBounds1().getBlockY());
                    config.set("arenas." + arenaName + ".bounds.firstbound.z", am.getPointStorage().get(player.getUniqueId()).getBounds1().getBlockZ());
                    config.set("arenas." + arenaName + ".bounds.secondbound.x", am.getPointStorage().get(player.getUniqueId()).getBounds2().getBlockX());
                    config.set("arenas." + arenaName + ".bounds.secondbound.y", am.getPointStorage().get(player.getUniqueId()).getBounds2().getBlockY());
                    config.set("arenas." + arenaName + ".bounds.secondbound.z", am.getPointStorage().get(player.getUniqueId()).getBounds2().getBlockZ());

                    am.getPointStorage().remove(player.getUniqueId()); // Clear the HashMap from the player, in case they want to make another arena right away.
                    if(player.getInventory().contains(pointWand)){ // Remove the wand from their inventory.
                        player.getInventory().remove(pointWand);
                    }

                    am.addArena(arenaName);
                    Main.getInstance().saveConfig();
                    player.sendMessage(prefix + "§a§lSuccess!");
                    player.sendMessage(prefix + "Arena " + arenaName + " has had its locations successfully configured.");

                    return true;
                }

                if(args[0].equalsIgnoreCase("remove")){
                    if(args.length == 1){
                        sender.sendMessage(prefix + "Please provide an arena name.");
                        return false;
                    }
                    if(args.length == 2){
                        String arenaToRemove = args[1];
                        if(!config.getStringList("list-of-arenas").contains(arenaToRemove)){
                            sender.sendMessage(prefix + "Sorry, that arena does not exist!");
                            return false;
                        }
                        if(config.getStringList("list-of-arenas").contains(arenaToRemove)){
                            config.getStringList("list-of-arenas").remove(arenaToRemove);
                            int arenaAmount = config.getInt("existing-arenas");
                            arenaAmount--;
                            config.set("arenas." + arenaToRemove, null); // ???
                            // TODO: Remove the arena
                            return true;
                        }
                    }
                }

                // Editing arenas command
                // args[0] = "edit"
                // args[1] = arena name
                // args[2] = field {location, spawnpointblue, spawnpointred, cauldronblue, cauldronred, lobby, bounds1, bounds2}
                if (args[0].equalsIgnoreCase("edit")) {
                    if (args.length == 1) { // Check if no arguments and check if arena actually exists.
                        sender.sendMessage(prefix + "Please provide an arena name.");
                        return false;
                    }
                    if (args.length == 2) {
                        player.getInventory().setItemInMainHand(pointWand);
                        sender.sendMessage(prefix + "You have been given the editing wand!");
                        sender.sendMessage(prefix + "You will be required to reset ALL points, aside from location.");
                        sender.sendMessage(prefix + "Please begin with §lobby location");
                        return true;
                    }

                }
            }
            return false;
        }
        return false;
    }
}
