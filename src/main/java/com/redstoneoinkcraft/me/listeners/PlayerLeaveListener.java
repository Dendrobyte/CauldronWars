package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.timers.BottleSpawnTimer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        RunningArenaManager ram = RunningArenaManager.getManager();
        if(ram.isInGame(player) == null){
            return;
        }
        if(ram.isInGame(player) != null){
            ItemStack bottle = new ItemStack(Material.GLASS_BOTTLE, 1);
            ItemMeta bottleMeta = bottle.getItemMeta();
            bottleMeta.setDisplayName("§7§lEmpty Bottle");
            bottle.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            bottle.setItemMeta(bottleMeta);

            RunningArena currentArena = ram.getRunningArena(ram.isInGame(player).getId());
            if(player.getInventory().contains(bottle)){
                BottleSpawnTimer bst = new BottleSpawnTimer(ram.isInGame(player));
                bst.runTaskTimer(Main.getInstance(), 0, 20L);
            }
            player.getInventory().clear();
            ram.removePlayer(player);
            return;
        }
    }

}
