package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Mark on 4/21/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class BottleDespawnListener implements Listener {

    // This class is unimportant. I will have to redo the bounds for maps.

    RunningArenaManager ram = RunningArenaManager.getManager();

    boolean validX, validY, validZ; // Check if the coordinates of the entity location are within an arena's bounds
    RunningArena ra;

    @EventHandler
    public void onBottleDespawn(ItemDespawnEvent event){
        ItemStack bottle = new ItemStack(Material.GLASS_BOTTLE);


        Item item = event.getEntity();
        if(!item.getItemStack().equals(bottle)){
            return;
        }
        if(item.getItemStack().equals(bottle)){
            ItemStack gameBottle = new ItemStack(Material.GLASS_BOTTLE, 1);
            ItemMeta bottleMeta = bottle.getItemMeta();
            bottleMeta.setDisplayName("§7§lEmpty Bottle");
            gameBottle.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            gameBottle.setItemMeta(bottleMeta);
            if(bottle.equals(gameBottle)){
                Location location = event.getLocation();
            }
        }
    }
}
