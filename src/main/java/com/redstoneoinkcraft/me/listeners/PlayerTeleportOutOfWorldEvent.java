package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by Mark on 5/11/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class PlayerTeleportOutOfWorldEvent implements Listener {

    RunningArenaManager ram = RunningArenaManager.getManager();

    @EventHandler
    public void playerTeleportsOutOfGameWorld(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        RunningArena ra = ram.isInGame(player);
        Location prevLoc = event.getFrom();
        Location nextLoc = event.getTo();

        if(ra == null){
            return;
        }
        if(ra.getGameState().equals(RunningArena.GameState.STARTING) || ra.getGameState().equals(RunningArena.GameState.IN_PROGRESS)) {
            if (!prevLoc.getWorld().equals(nextLoc.getWorld())) {
                event.setCancelled(true);
                player.sendMessage(Main.getInstance().getPrefix() + "You can't teleport out while in a game!");
                return;
            }
        }
    }

}
