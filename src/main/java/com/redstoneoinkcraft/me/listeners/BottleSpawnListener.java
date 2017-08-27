package com.redstoneoinkcraft.me.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class BottleSpawnListener implements Listener {

    @EventHandler
    public void onBottleSpawn(EntitySpawnEvent event){
        Entity spawnedEntity = event.getEntity();
        if(spawnedEntity.getType().equals(Material.GLASS_BOTTLE)){
            /*
             * Check pseudocode.
             */
            return;
        } // Else, do nothing
    }
}
