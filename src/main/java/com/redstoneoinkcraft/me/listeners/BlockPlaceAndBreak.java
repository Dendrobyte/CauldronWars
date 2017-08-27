package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Mark on 4/20/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class BlockPlaceAndBreak implements Listener {

    RunningArenaManager ram = RunningArenaManager.getManager();

    @EventHandler
    public void blockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();

        if(player.getLocation().getWorld().getName().equalsIgnoreCase("minigames")){
            if(player.hasPermission("cauldronwars.break")){
                return;
            }
            event.setCancelled(true);
            return;
        }

        if(ram.isInGame(player) != null){
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();

        if(player.getLocation().getWorld().getName().equalsIgnoreCase("minigames")){
            if(player.hasPermission("cauldronwars.break")){
                return;
            }
            event.setCancelled(true);
            return;
        }

        if(ram.isInGame(player) != null){
            event.setCancelled(true);
        }
        else {
        }
    }
}
