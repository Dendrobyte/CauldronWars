package com.redstoneoinkcraft.me.timers;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by Mark on 4/29/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class ItemAboveHeadSpawner extends BukkitRunnable {

    RunningArena ra;

    public ItemAboveHeadSpawner(RunningArena ra){
        this.ra = ra;
        // Spawns wool block above head every 5 ticks while in the game
    }

    @Override
    public void run(){
        for(Player player : ra.getPlayers()){
            if(ra.getTeamBlue().contains(player)){
                removeDroppedItems(ra, player, true);
            }

            // TODO: See how it looks from eye location

            if(ra.getTeamRed().contains(player)){
                removeDroppedItems(ra, player, false);
            }
        }
    }

    // true = blue, false = red
    public void removeDroppedItems(RunningArena ra, Player player, boolean team){
        ItemStack wool;
        if(team) wool = new ItemStack(Material.BLUE_WOOL, 1);
        else wool = new ItemStack(Material.RED_WOOL, 1);
        World world = ra.getLobby().getWorld();
        double newLocY = player.getEyeLocation().getY();
        Location itemLoc = new Location(world, player.getEyeLocation().getX(), newLocY, player.getEyeLocation().getZ());
        final Entity droppedItem = world.dropItem(itemLoc, wool);
        droppedItem.setVelocity(new Vector(0.00, 0.40, 0.00));
        new BukkitRunnable(){
            @Override
            public void run() {
                droppedItem.remove();
            }

        }.runTaskLater(Main.getInstance(), 10L);
    }
}

