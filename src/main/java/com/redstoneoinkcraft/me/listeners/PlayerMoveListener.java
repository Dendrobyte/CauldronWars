package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

/**
 * Created by Mark on 7/26/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class PlayerMoveListener implements Listener { // To prevent spawn camping

    @EventHandler
    public void onPlayerEnterOpposingTeamSpawn(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location playerTo = new Location(event.getTo().getWorld(), event.getTo().getBlockX(), event.getTo().getBlockY(), event.getTo().getBlockZ());
        RunningArenaManager ram = RunningArenaManager.getManager();
        RunningArena ra = ram.isInGame(player);

        if(ra == null){
            return;
        }
        ArrayList<Location> blockLocs = getBlocksInSpawnRadius(player, ra);
        for(Location loc : blockLocs){
            if(playerTo.equals(loc)){
                player.sendMessage(Main.getInstance().getPrefix() + "You can not enter the opposing team's spawn!");
                event.setCancelled(true);
                return;
            }
        }
        return;
        // Call the getBlocks method, the blocks should be determined
        // For every block in that list, if the player's moving into the same one, cancel the event

    }

    private ArrayList<Location> getBlocksInSpawnRadius(Player player, RunningArena ra){ // Return a list of blocks that a player should be denied from
        ArrayList<Location> blocks = new ArrayList<>();
        int radius = 3;

        if(ra.getTeamBlue().contains(player)){
            Location spawnPoint = ra.getRedSpawn();
            for(int x = spawnPoint.getBlockX() - radius; x <= spawnPoint.getBlockX() + radius; x++) {
                for(int y = spawnPoint.getBlockY() - radius; y <= spawnPoint.getBlockY() + radius; y++) {
                    for(int z = spawnPoint.getBlockZ() - radius; z <= spawnPoint.getBlockZ() + radius; z++) {
                        blocks.add(spawnPoint.getWorld().getBlockAt(x, y, z).getLocation());
                    }
                }
            }
        }
        if(ra.getTeamRed().contains(player)){
            Location spawnPoint = ra.getBlueSpawn();
            for(int x = spawnPoint.getBlockX() - radius; x <= spawnPoint.getBlockX() + radius; x++) {
                for(int y = spawnPoint.getBlockY() - radius; y <= spawnPoint.getBlockY() + radius; y++) {
                    for(int z = spawnPoint.getBlockZ() - radius; z <= spawnPoint.getBlockZ() + radius; z++) {
                        blocks.add(spawnPoint.getWorld().getBlockAt(x, y, z).getLocation());
                    }
                }
            }
        }
        return blocks;
    }
}
