package com.redstoneoinkcraft.me.commands;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.ArenaManager;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.kits.KitBase;
import com.redstoneoinkcraft.me.kits.KitManager;
import com.redstoneoinkcraft.me.timers.CwarsHelpTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class CWarsMainCommand implements CommandExecutor {

    // This class is all the commands except for arena creation... don't even know what those will be yet :P
    // Manage all joining of games through commands...?
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){ // General check in case sender isn't a player...
            sender.sendMessage("[Cauldron Wars] Sorry! This command can only be used by a player.");
            return false;
        }

        String prefix = Main.getInstance().getPrefix();
        FileConfiguration config = Main.getInstance().getConfig();
        Player player = (Player) sender;
        RunningArenaManager ram = RunningArenaManager.getManager();

        if(command.getName().equalsIgnoreCase("cauldronwars")){ // Command time!
            if(args[0].equalsIgnoreCase("help")){
                CwarsHelpTimer cht = new CwarsHelpTimer(player);
                cht.runTaskTimer(Main.getInstance(), 0, 20);
                return true;
            }
            if(args.length < 1){
                return false;
            }
            if(player.hasPermission("cauldronwars.leave")){
                if(args.length < 1){
                    player.sendMessage("§7-+[§bCauldron §cWars §7Help Menu]+-");
                    player.sendMessage("§7/cauldronwars list - List running arenas");
                    player.sendMessage("§7/cauldronwars create - Create a running arena");
                    player.sendMessage("§7/cauldronwars join - Join a running arena");
                    player.sendMessage("§7/cwarsarena create <name> - Create a new map");
                    return true;
                }
                if(args[0].equalsIgnoreCase("leave")){
                    if(args.length < 2){
                        // player.sendMessage(prefix + "§cPlease provide an arena ID that you wish to leave.");
                        if(ram.isInGame(player) == null){
                            player.sendMessage(prefix + "§cYou aren't even in a game!");
                            return false;
                        }
                        if(ram.isInGame(player) != null){
                            player.sendMessage(prefix + "You have left arena with ID " + ram.isInGame(player).getId());
                            ram.removePlayer(player);
                            return true;
                        }
                    }
                    try{
                        int id = Integer.parseInt(args[1]);
                        RunningArena currentArena = ram.getRunningArena(id);
                        if(currentArena == null){
                            player.sendMessage(prefix + "Sorry, no arena with that ID is currently leavable.");
                            return false;
                        }
                        if(currentArena.getGameState().equals(RunningArena.GameState.WAITING)){
                            ram.removePlayer(player);
                            return true;
                        }
                    } catch(NumberFormatException exception) {
                        player.sendMessage(prefix + "Sorry, that's not an ID!");
                    }
                }
            }
            if(!(player.hasPermission("cauldronwars.use"))){ // Uh oh... they don't have that permission!
                sender.sendMessage(prefix + "§cSorry! You do not have access to that command.");
                sender.sendMessage(prefix + "§cRequired permission: cauldronwars.use");
                return false;
            }
            if(player.hasPermission("cauldronwars.use") || player.isOp()){ // Alright, if they do... here is the bulk of it all!
                if(args.length == 0){
                    player.sendMessage("§7-+[§bCauldron §cWars §7Help Menu]+-");
                    player.sendMessage("§7/cauldronwars list - List running arenas");
                    player.sendMessage("§7/cauldronwars create - Create a running arena");
                    player.sendMessage("§7/cauldronwars join - Join a running arena");
                    player.sendMessage("§7/cwarsarena create <name> - Create a new map");
                    return true;
                }

                if(args[0].equalsIgnoreCase("list")){
                    if(config.getStringList("list-of-arenas").isEmpty()){
                        player.sendMessage(prefix + "§cNo arenas have been created! Use /cwarsarena to create an arena.");
                        return false;
                    }
                    player.sendMessage(prefix + "Existing arenas:");
                    for(String arena : config.getStringList("list-of-arenas")){
                        player.sendMessage(prefix + arena);
                    } // TODO: NO existing arenas have been created
                    return true;
                }

                if(args[0].equalsIgnoreCase("tp")){
                    Location location = new Location(Bukkit.getWorld("redcloudcw"), config.getDouble("arenas.redcloud.location.x"), config.getDouble("arenas.redcloud.location.y"), config.getDouble("arenas.redcloud.location.z"));
                    player.teleport(location);
                }

                // TODO: For testing purposes, start the arena with less than 6 people (if necessary).

                if(args[0].equalsIgnoreCase("debug")){
                    player.sendMessage("no debugs being attempted");
                    return true;
                }

                if(args[0].equalsIgnoreCase("end")){
                    if(args.length == 0){
                        player.sendMessage(prefix + "Please add an arena ID");
                        return true;
                    }
                    try {
                        int i = Integer.parseInt(args[1]);
                        ram.end(i);
                    } catch(NumberFormatException exception){
                        player.sendMessage(prefix + "That's not a valid arena ID.");
                        return false;
                    }

                }

                if(args[0].equalsIgnoreCase("create")){
                    if(args.length < 2){
                        player.sendMessage(prefix + "§cPlease provide the arena name that you wish to use.");
                        return false;
                    }
                    // String arenaName = args[1];
                    if(!config.getStringList("list-of-arenas").contains(args[1])){
                        player.sendMessage(prefix + "§cSorry, the arena §c§l'" + args[1] + "'§r§c does not exist!");
                        return false;
                    }
                    if(config.getStringList("list-of-arenas").contains(args[1])){
                        ram.create(args[1]);
                        player.sendMessage(prefix + "Arena " + args[1] + " has been created! ID: " + ram.getRunningArenasSize());
                        return true;
                    }
                }

                if(args[0].equalsIgnoreCase("join")){
                    if(args.length < 2){
                        player.sendMessage(prefix + "§cPlease provide an arena ID that you wish to join.");
                        if(ram.getRunningArenas().isEmpty()){
                            player.sendMessage(prefix + "§cNo arenas have been started yet!");
                            return false;
                        }
                        for(RunningArena ra : ram.getRunningArenas()){
                            if(ra.getGameState().equals(RunningArena.GameState.WAITING) || ra.getGameState().equals(RunningArena.GameState.STARTING)){
                                player.sendMessage(prefix + "§9Arena §9§l" + ra.getId() + " §9can be joined.");
                            }
                        }
                        return false;
                    }
                    try {
                        int id = Integer.parseInt(args[1]);
                        RunningArena currentArena = ram.getRunningArena(id);
                        if(currentArena == null){
                            player.sendMessage(prefix + "§cSorry, no arena with that ID is currently joinable.");
                            return false;
                        }
                        if(currentArena.getGameState().equals(RunningArena.GameState.WAITING)){
                            ram.addPlayer(player, id);
                            return true;
                        }
                        if(currentArena.getGameState().equals(RunningArena.GameState.STARTING)){
                            ram.addPlayer(player, id);
                            return false;
                        }
                        if(currentArena.getGameState().equals(RunningArena.GameState.IN_PROGRESS)){
                            player.sendMessage(prefix + "Sorry, that game is currently in progress!");
                            return false;
                        }
                        if(currentArena.getGameState().equals(RunningArena.GameState.OVER)){
                            player.sendMessage(prefix + "Sorry, that game ended!");
                            return false;
                        }
                    } catch(NumberFormatException exception) {
                        player.sendMessage(prefix + "§cSorry, that's not an ID!");
                    }
                }
                if(args[0].equalsIgnoreCase("leave")){
                    if(args.length < 2){
                        // player.sendMessage(prefix + "§cPlease provide an arena ID that you wish to leave.");
                        if(ram.isInGame(player) == null){
                            player.sendMessage(prefix + "§cYou aren't even in a game!");
                            return false;
                        }
                        if(ram.isInGame(player) != null){
                            player.sendMessage(prefix + "You have left arena with ID " + ram.isInGame(player).getId());
                            ram.removePlayer(player);
                            return true;
                        }
                    }
                    try{
                        int id = Integer.parseInt(args[1]);
                        RunningArena currentArena = ram.getRunningArena(id);
                        if(currentArena == null){
                            player.sendMessage(prefix + "Sorry, no arena with that ID is currently leavable.");
                            return false;
                        }
                        if(currentArena.getGameState().equals(RunningArena.GameState.WAITING)){
                            ram.removePlayer(player);
                            return true;
                        }
                    } catch(NumberFormatException exception) {
                        player.sendMessage(prefix + "Sorry, that's not an ID!");
                    }
                }
                if (args[0].equalsIgnoreCase("forcestart")) {
                    if(args.length < 2){
                        player.sendMessage(prefix + "Please provide an arena ID to force start!");
                        return false;
                    }
                    try{
                        int id = Integer.parseInt(args[1]);
                        RunningArena currentArena = ram.getRunningArena(id);
                        if(currentArena == null){
                            player.sendMessage(prefix + "Sorry, no arena with that ID is currently able to start.");
                            return false;
                        }
                        ram.start(id);
                        for(Player player1 : currentArena.getPlayers()){
                            player1.sendMessage(prefix + "§dThe game has been force-started by " + player.getName());
                            // Countdown started in the start() method
                        }
                        return true;
                    } catch(NumberFormatException exception) {
                        player.sendMessage(prefix + "Sorry, that's not an ID!");
                        return false;
                    }
                }
                if(args[0].equalsIgnoreCase("kit")){
                    ArrayList<String> kits = new ArrayList<>();
                    kits.add("Gladiator");
                    kits.add("Archer");
                    kits.add("Flying_Pig");
                    kits.add("Boxer");
                    kits.add("Ghost");
                    kits.add("Monk");
                    kits.add("Mummy");
                    if(!player.hasPermission("cauldronwars.kit")){
                        player.sendMessage(prefix + "Sorry, it appears you don't have access to that command! §cauldronwars.kit");
                        return false;
                    }
                    if(ram.isInGame(player) == null){
                        player.sendMessage(prefix + "Sorry, you have to be in a game to use this command!");
                        return false;
                    }
                    if(args.length < 2){
                        player.sendMessage(prefix + "Please provide the name of a kit you want to use!");
                        player.sendMessage(prefix + "Available kits: " + kits);
                        return false;
                    }
                    KitManager km = KitManager.getManager();
                    if(km.getKitByName(args[1]) == null){
                        player.sendMessage(prefix + "That is not a valid kit name!");
                        player.sendMessage(prefix + "Available kits: " + kits);
                        return false;
                    }

                    if(km.getKitByName(args[1]) != null){
                        player.getInventory().clear();
                        player.getInventory().setArmorContents(null);
                        km.removeKit(player);
                        km.addKit(km.getKitByName(args[1]), player);
                        player.sendMessage(prefix + "You gave yourself the " + args[1] + " kit via command.");
                        return true;
                    }

                }

                else {
                    player.sendMessage("§7-+[§bCauldron §cWars §7Help Menu]+-");
                    player.sendMessage("§7/cauldronwars list - List running arenas");
                    player.sendMessage("§7/cauldronwars create - Create a running arena");
                    player.sendMessage("§7/cauldronwars join - Join a running arena");
                    player.sendMessage("§7/cwarsarena create <name> - Create a new map");
                    return true;
                }

                return true;
            }
        }
        return true;
    }
}
