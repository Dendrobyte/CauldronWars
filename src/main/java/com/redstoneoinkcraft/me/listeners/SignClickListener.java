package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class SignClickListener implements Listener {

    RunningArenaManager ram = RunningArenaManager.getManager();
    RunningArena joinArena;

    @EventHandler
    public void onSignClick(PlayerInteractEvent event ){
        Player player = event.getPlayer();
        Block blockClicked = event.getClickedBlock();
        RunningArenaManager ram = RunningArenaManager.getManager();
        String prefix = Main.getInstance().getPrefix();

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(blockClicked.getType().equals(Material.WALL_SIGN) || blockClicked.getType().equals(Material.WALL_SIGN)){
                Sign sign = (Sign) blockClicked.getState();
                String line1 = sign.getLine(0); // Main Headline
                String line2 = sign.getLine(1); // Create/Join keyword
                String line3 = sign.getLine(2); // Arena name
                String line4 = sign.getLine(3);

                double bSignY = sign.getY() - 1;
                Location bSignLoc = new Location(sign.getWorld(), sign.getX(), bSignY, sign.getZ());
                Block bSignBlock = bSignLoc.getBlock();
                Sign bSign;
                try {
                    bSign = (Sign) bSignBlock.getState();
                } catch (ClassCastException exception){
                    if(line1.equals("§8[§bCauldron§cWars§8]") && line2.equalsIgnoreCase("create")){
                        player.sendMessage(prefix + "A sign needs to be below!");
                    }
                    // Otherwise not a sign and that's fine.
                    bSign = null;
                }

                if(line1.equals("§8[§bCauldron§cWars§8]")){

                    // Create a game
                    if(line2.equalsIgnoreCase("create")){
                        if(!bSignBlock.getType().equals(Material.WALL_SIGN)){
                            player.sendMessage(prefix + "There is no sign below to make this arena joinable!");
                            return;
                        }

                        if(bSign.getLine(2).equals("")){
                            player.sendMessage(prefix + "The join sign needs to be initialized. Contact an admin.");
                            return;
                        }

                        int bSignArena = Integer.parseInt(bSign.getLine(2));
                        if((ram.getRunningArena(bSignArena) == null) && (Integer.parseInt(bSign.getLine(2)) > 0)){
                            // Reset the sign
                            bSign.setLine(0, "§8[§bCauldron§cWars§8]");
                            bSign.setLine(1, "join");
                            bSign.setLine(2, "-1");
                            bSign.setLine(3, "NULL");
                            bSign.update();
                            player.sendMessage(prefix + "Sign reloaded! Try again.");
                            return;
                        }
                        if((bSignArena > 0) && (!ram.getRunningArena(bSignArena).getGameState().equals(RunningArena.GameState.OVER))){
                            player.sendMessage(prefix + "That arena is already in use. Please wait until the game ends.");
                            return;
                        }

                        ram.create(line3);
                        int arenaID  = ram.getRunningArenasSize();
                        bSign.setLine(0, "§8[§bCauldron§cWars§8]");
                        bSign.setLine(1, "join");
                        bSign.setLine(2, "" + arenaID);
                        bSign.setLine(3, "0 Players");
                        bSign.update();
                        ram.getArenaJoinSigns().put(arenaID, bSign);

                        player.sendMessage(prefix + "Arena on map " + line3 + " has been created! ID: " + arenaID);
                        return;
                    }

                    // Join a game
                    if(line2.equalsIgnoreCase("join")){
                        if(line3.isEmpty()){
                            player.sendMessage(prefix + "This shouldn't be possible, but the line is empty!");
                            return;
                        }
                        try {
                            int line3ID = Integer.parseInt(line3);
                            if(line1.equals("-1")){
                                player.sendMessage(prefix + "This sign needs to be initialized.");
                                return;
                            }
                            RunningArena joinArena = ram.getRunningArena(line3ID);
                            if(joinArena == null){
                                player.sendMessage(prefix + "It appears no arena with that ID is currently started.");
                                return;
                            }
                            if(ram.isInGame(player) != null){
                                player.sendMessage(prefix + "You can not be in more than one game!");
                                return;
                            }
                            if(joinArena.getGameState() == RunningArena.GameState.STARTING){
                                ram.addPlayer(player, line3ID);
                                return;
                            }
                            if(joinArena.getGameState() == RunningArena.GameState.IN_PROGRESS){
                                player.sendMessage(prefix + "Sorry, that game is already in progress!");
                                return;
                            }
                            if(joinArena.getGameState() == RunningArena.GameState.OVER){
                                player.sendMessage("Sorry, that game has ended!");
                                return;
                            }
                            if(joinArena.getGameState() == RunningArena.GameState.WAITING) {
                                int playerCount = Integer.parseInt(line4.substring(0, 1));
                                ram.addPlayer(player, line3ID);
                                sign.setLine(3, (playerCount + 1) + " Players");
                                sign.update();
                                return;
                            }
                        } catch (NumberFormatException exception){
                            // Not an ID, whatever
                            player.sendMessage(prefix + "That game is currently in progress.");
                        }
                        return;
                    }
                    if(line2.equalsIgnoreCase("list")){
                        RunningArena ra = ram.isInGame(player);
                        if(ra == null){
                            player.sendMessage(prefix + "You need to be in a game to list your team!");
                            return;
                        }
                        if(ra.getTeamBlue().contains(player)){
                            player.sendMessage(prefix + "Players on §b§lTeam Blue:");
                            for(int i = 0; i < ra.getTeamBlue().size(); i++){
                                player.sendMessage("§b" + (i + 1) + ": " + ra.getTeamBlue().get(i).getName());
                            }
                            return;
                        }
                        if(ra.getTeamRed().contains(player)){
                            player.sendMessage(prefix + "Players on §c§lTeam Red:");
                            for(int i = 0; i < ra.getTeamRed().size(); i++){
                                player.sendMessage("§c" + (i + 1) + ": " + ra.getTeamRed().get(i).getName());
                            }
                            return;
                        }
                        return;
                    }
                }
                return;
            }
        }

        if(event.getAction() == Action.LEFT_CLICK_BLOCK){ // In case they left click, do nothing! Nothing would happen anyway so this is pointless \o/
            if(blockClicked.getType().equals(Material.WALL_SIGN)){
                Sign sign = (Sign) blockClicked.getState();
                if(sign.getLine(0).contains("[CauldronWars]")){
                    // event.setCancelled(true);
                    return;
                }
            }
        }
    }

}
