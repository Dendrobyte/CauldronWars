package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Mark on 4/20/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class PotionPickupListener implements Listener {

    RunningArenaManager ram = RunningArenaManager.getManager();

    @EventHandler
    public void potionPickupByPlayer(EntityPickupItemEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        RunningArena ra = ram.isInGame(player);
        ItemStack pickedUp = event.getItem().getItemStack();

        ItemStack bottle = new ItemStack(Material.GLASS_BOTTLE, 1);
        ItemMeta bottleMeta = bottle.getItemMeta();
        bottleMeta.setDisplayName("§7§lEmpty Bottle");
        bottle.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        bottle.setItemMeta(bottleMeta);

        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName("§7§lCauldron Locator");
        compass.setItemMeta(compassMeta);

        PotionEffect sideEffect1 = new PotionEffect(PotionEffectType.BLINDNESS, 9999, 1);
        PotionEffect sideEffect2 = new PotionEffect(PotionEffectType.WEAKNESS, 9999, 4);

        if(ra == null){
            return;
        }
        if(ra != null){
            if(pickedUp.getType().toString().contains("WOOL")){
                pickedUp.setAmount(0);
                event.setCancelled(true);
                return;
            }
            if(pickedUp.equals(bottle)){
                if(ra.getTeamBlue().contains(player)){
                    for(Player player1 : ra.getPlayers()){
                        player1.sendMessage(Main.getInstance().getPrefix() + "§b§l" + player.getName() + " §7picked up the bottle!");
                    }
                    player.sendMessage(Main.getInstance().getPrefix() + "Check your compass for the right direction!");

                    player.getInventory().addItem(compass);
                    player.setCompassTarget(ra.getRedCauldron().getLocation());

                    player.addPotionEffect(sideEffect1);
                    player.addPotionEffect(sideEffect2);
                    return;
                }
                else if(ra.getTeamRed().contains(player)){
                    for(Player player1 : ra.getPlayers()){
                        player1.sendMessage(Main.getInstance().getPrefix() + "§c§l" + player.getName() + " §7picked up the bottle!");
                    }
                    player.sendMessage(Main.getInstance().getPrefix() + "Check your compass for the right direction!");

                    player.getInventory().addItem(compass);
                    player.setCompassTarget(ra.getBlueCauldron().getLocation());

                    player.addPotionEffect(sideEffect1);
                    player.addPotionEffect(sideEffect2);
                    return;
                }
                return;
            }
        }
    }

}
