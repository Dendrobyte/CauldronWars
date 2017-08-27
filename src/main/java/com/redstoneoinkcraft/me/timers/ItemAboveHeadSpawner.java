package com.redstoneoinkcraft.me.timers;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
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

    private ItemStack blueWool = new ItemStack(Material.WOOL, 1, (byte)11);
    ItemMeta blueWoolMeta = blueWool.getItemMeta();

    private ItemStack redWool = new ItemStack(Material.WOOL, 1, (byte)14);
    ItemMeta redWoolMeta = redWool.getItemMeta();

    public ItemAboveHeadSpawner(RunningArena ra){
        this.ra = ra;
        // Spawns wool block above head every 5 ticks while in the game
    }

    @Override
    public void run(){
        for(Player player : ra.getPlayers()){
            if(ra.getTeamBlue().contains(player)){
                World world = ra.getLobby().getWorld();
                double newLocY = player.getEyeLocation().getY();
                Location itemLoc = new Location(world, player.getEyeLocation().getX(), newLocY, player.getEyeLocation().getZ());
                Entity droppedItemBlue = world.dropItem(itemLoc, blueWool);
                droppedItemBlue.setVelocity(new Vector(0.00, 0.40, 0.00));
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        droppedItemBlue.remove();
                    }

                }.runTaskLater(Main.getInstance(), 10L);
            }

            // TODO: See how it looks from eye location

            if(ra.getTeamRed().contains(player)){
                World world = ra.getLobby().getWorld();
                double newLocY = player.getEyeLocation().getY();
                Location itemLoc = new Location(world, player.getEyeLocation().getX(), newLocY, player.getEyeLocation().getZ());
                Entity droppedItemRed = world.dropItem(itemLoc, redWool);
                droppedItemRed.setVelocity(new Vector(0.00, 0.40, 0.00));
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        droppedItemRed.remove();
                    }

                }.runTaskLater(Main.getInstance(), 10L);
            }
        }
    }

}
