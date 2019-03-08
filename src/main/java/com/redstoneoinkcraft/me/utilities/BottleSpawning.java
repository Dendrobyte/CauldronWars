package com.redstoneoinkcraft.me.utilities;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.timers.ParticleSpawningTimer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mark on 4/19/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class BottleSpawning {

    double maxX, maxY, maxZ, minX, minY, minZ;
    double locX, locY, locZ;
    int maxIntX, maxIntY, maxIntZ, minIntX, minIntY, minIntZ;

    public Location generateBottleLocation(RunningArena arena){
        if(!arena.getGameState().equals(RunningArena.GameState.IN_PROGRESS)){
            return null;
        }


        // Declaring coordinates of the bounding area
        Location firstBound = arena.getBounds1();
        Location secondBound = arena.getBounds2();

        Location bottleSpawnLocation;
        // Generate X maximum and minimum
        if(firstBound.getX() > secondBound.getX()){
            maxX = firstBound.getX();
            minX = secondBound.getX();
        }
        else if(secondBound.getX() > firstBound.getX()) {
            maxX = secondBound.getX();
            minX = firstBound.getX();
        }

        // Generate Y maximum and minimum
        if(firstBound.getY() > secondBound.getY()){
            maxY = firstBound.getY();
            minY = secondBound.getY();
        }
        else if(secondBound.getY() > firstBound.getY()) {
            maxY = secondBound.getY();
            minY = firstBound.getY();
        }

        // Generate Z maximum and minimum
        if(firstBound.getZ() > secondBound.getZ()){
            maxZ = firstBound.getZ();
            minZ = secondBound.getZ();
        }
        else if(secondBound.getZ() > firstBound.getZ()) {
            maxZ = secondBound.getZ();
            minZ = firstBound.getZ();
        }

        // Cast 'em into integers!
        maxIntX = (int) maxX;
        minIntX = (int) minX;
        maxIntY = (int) maxY;
        minIntY = (int) minY;
        maxIntZ = (int) maxZ;
        minIntZ = (int) minZ;

        // Generate random coordinates
        Random rand = new Random();
        locX = (double) rand.nextInt((maxIntX - minIntX) + 1) + minX;
        locY = (double) rand.nextInt((maxIntY - minIntY) + 1) + minY;
        locZ = (double) rand.nextInt((maxIntZ - minIntZ) + 1) + minZ;

        bottleSpawnLocation = new Location(arena.getLobby().getWorld(), locX, locY, locZ);

        boolean blockHit = false;
        for(int i = 0; i < 256; i++){
            Location checkLoc = new Location(arena.getLobby().getWorld(), locX, i, locZ);
            if(!checkLoc.getBlock().getType().equals(Material.AIR)){
                blockHit = true;
                break;
            }
        }

        if(!blockHit) bottleSpawnLocation = null;

        return bottleSpawnLocation;
    }

    public ItemStack generateBottle(RunningArena arena){
        if(!arena.getGameState().equals(RunningArena.GameState.IN_PROGRESS)){
            return null;
        }
        ItemStack bottle = new ItemStack(Material.GLASS_BOTTLE, 1);
        ItemMeta bottleMeta = bottle.getItemMeta();
        bottleMeta.setDisplayName("§7§lEmpty Bottle");
        bottle.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        bottle.setItemMeta(bottleMeta);
        return bottle;
    }

}
