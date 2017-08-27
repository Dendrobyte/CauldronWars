package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.events.CustomBottleLossEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mark on 4/21/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class CustomBottleLossListener implements Listener {

    @EventHandler
    public void onBottleLoss(CustomBottleLossEvent event){
        RunningArena ra = event.getRa();
        Player player = event.getPlayer();
        ItemStack bottle = event.getBottle();
        event.spawnNewBottle(ra);
    }

}
