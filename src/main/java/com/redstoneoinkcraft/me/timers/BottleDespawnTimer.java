package com.redstoneoinkcraft.me.timers;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * Created by Mark on 4/21/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class BottleDespawnTimer extends BukkitRunnable {

    ItemStack item;
    int timer = 60;
    Location firstLoc, secondLoc;
    RunningArena ra;

    public BottleDespawnTimer(ItemStack item, Location firstLoc, RunningArena ra){
        this.item = item;
        this.firstLoc = firstLoc;
        this.ra = ra;
    }

    @Override
    public void run(){
        if(timer != 0){
            this.timer--;
        }
        if(timer == 0) {
            for (Player player : ra.getPlayers()) {
                if(player.getInventory().contains(item)){
                    // BottleDespawnTimer bst = new BottleDespawnTimer(item, firstLoc, ra);
                    // bst.runTaskTimer(Main.getInstance(), 0, 20L);
                    cancel();
                    return;
                }
                break;
            }
            ItemStack bottle = new ItemStack(Material.GLASS_BOTTLE, 1);
            ItemMeta bottleMeta = bottle.getItemMeta();
            bottleMeta.setDisplayName("§7§lEmpty Bottle");
            bottle.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            bottle.setItemMeta(bottleMeta);
            for(Entity current : firstLoc.getChunk().getEntities()) {
                if (current instanceof Item) { // Don't delete any mobs or players! :P
                    if(((Item) current).getItemStack().equals(bottle)){
                        current.remove();
                        for(Player player : ra.getPlayers()) {
                            player.sendMessage(Main.getInstance().getPrefix() + "The bottle despawned. A new one will spawn soon!");
                        }
                        BottleSpawnTimer bst = new BottleSpawnTimer(ra);
                        bst.runTaskTimer(Main.getInstance(), 0, 20L);
                        cancel();
                        break;
                    }
                }
            }
            return;
        }
    }

}
