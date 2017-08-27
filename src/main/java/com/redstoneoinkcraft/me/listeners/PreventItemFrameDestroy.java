package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;

/**
 * Created by Mark on 5/12/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class PreventItemFrameDestroy implements Listener {

    RunningArenaManager ram = RunningArenaManager.getManager();

    @EventHandler
    public void onItemFrameInteract(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if(ram.isInGame(player) == null){
            return;
        }
        if(ram.isInGame(player) != null){
            if(entity instanceof ItemFrame){
                event.setCancelled(true);
                return;
            }
        }

    }

    @EventHandler
    public void onItemFrameBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(ram.isInGame(player) == null){
            return;
        }
        if(ram.isInGame(player) != null){
            if(event.getBlock().getType().equals(Material.ITEM_FRAME)){
                event.setCancelled(true);
                return;
            }
        }
    }

}
