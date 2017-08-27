package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.ArenaManager;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.kits.KitManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Mark on 3/19/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class SignCreateListener implements Listener {

    FileConfiguration config = Main.getInstance().getConfig();
    RunningArenaManager ram = RunningArenaManager.getManager();
    int playerCount = 0;
    String gameStatus = "";
    String arenaName;
    String prefix = Main.getInstance().getPrefix();

    @EventHandler
    public void onSignCreation(SignChangeEvent event){
            Player player = event.getPlayer();
            ArenaManager am = ArenaManager.getManager();
            String prefix = Main.getInstance().getPrefix();
            KitManager km = KitManager.getManager();

            if (player.hasPermission("cauldronwars.sign.create") || player.isOp()) {
                String line1 = event.getLine(0); // Main Headline
                String line2 = event.getLine(1); // Keyword
                String line3 = event.getLine(2);
                String line4 = event.getLine(3);
                String newLine1 = "§8[§bCauldron§cWars§8]";

                // Sign to create and join games
                if(line1.equalsIgnoreCase("[CauldronWars]")){
                    if(line2.isEmpty()){
                        player.sendMessage(prefix + "Please provide the create/join keyword.");
                        event.setLine(0, "§4[CauldronWars]");
                        return;
                    }

                    // Create an arena
                    if(line2.equalsIgnoreCase("create")){
                        if (!config.getStringList("list-of-arenas").contains(line3)) {
                            player.sendMessage(Main.getInstance().getPrefix() + "Sorry, the arena \"" + line3 + "\" could not be found. (Case matters)");
                            event.setLine(0, "§4[CauldronWars]");
                            return;
                        }
                        if(line3.isEmpty()){
                            player.sendMessage(prefix + "Please provide an arena name on line 3");
                            return;
                        }
                        // Sign created
                        event.setLine(0, newLine1);
                    }

                    // Join an arena
                    if(line2.equalsIgnoreCase("join")){
                        event.setLine(0, "§8[§bCauldron§cWars§8]");
                        event.setLine(1, "join");
                        event.setLine(2, "-1");
                        event.setLine(3, "NULL");
                    }


                }

                if(line2.equalsIgnoreCase("list")){
                    event.setLine(0, newLine1);
                    player.sendMessage(prefix + "Successfully created a team listing sign!");
                    return;
                }

                // Create a leave sign
                if(line1.equalsIgnoreCase(("[CWars Leave]"))){
                    event.setLine(0, "§c[CWars Leave]");
                    event.setLine(1, "Leave Arena");
                }

                // Create a kit sign
                if(line1.equalsIgnoreCase("[CWars Kit]")){
                    if(line2.isEmpty()){
                        event.setLine(0, "§9[CWars Kit]");
                        return;
                    }
                    if(!line2.isEmpty()){
                        if(km.getKitByName(line2) == null){
                            player.sendMessage(prefix + "You did not enter a valid kit!");
                            event.setLine(0, "§4[CWars Kit]");
                            return;
                        }
                        if(km.getKitByName(line2) != null){
                            player.sendMessage(prefix + "Successfully created a kit sign for the " + line2 + " kit!");
                            event.setLine(0, "§9[CWars Kit]");
                            return;
                        }
                    }
                }

            }
    }

    public int getPlayerCount(){
        return this.playerCount;
    }

    public String getGameStatus(){
        return this.gameStatus;
    }

}
