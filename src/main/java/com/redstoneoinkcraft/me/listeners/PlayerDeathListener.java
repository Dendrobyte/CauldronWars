package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.events.CustomBottleLossEvent;
import com.redstoneoinkcraft.me.kits.KitManager;
import com.redstoneoinkcraft.me.timers.BottleSpawnTimer;
import com.redstoneoinkcraft.me.timers.RespawnTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class PlayerDeathListener implements Listener {

    RunningArenaManager ram = RunningArenaManager.getManager();
    KitManager km = KitManager.getManager();
    String prefix = Main.getInstance().getPrefix();

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }

        // event.getCause();

        // Cancel the damage that causes death and respawn them
        Player player = (Player) event.getEntity();
        if(ram.isInGame(player) == null){
            return;
        }

        if(event.getDamage() < player.getHealth()){
            return;
        }
        if(event.getDamage() >= player.getHealth()){
            if(player.getHealth() == 20){ // So if it was a one hit kill
                System.out.println("Yikes! One hit kill alert!");
                player.sendMessage(prefix + "One hit kill has been prevented.");
                event.setCancelled(true);
            }
            if(event instanceof EntityDamageByEntityEvent){
                EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent)event;
                if(edbeEvent.getDamager() instanceof Player){
                    Player damager = (Player)edbeEvent.getDamager();
                    player.sendMessage(prefix + "§e§lYou have been killed by §6§l" + damager.getName());
                    damager.sendMessage(prefix + "§e§lYou killed §6§l" + player.getName());
                }
                if(edbeEvent.getDamager() instanceof Projectile){
                    Player shooter = (Player) ((Projectile) edbeEvent.getDamager()).getShooter();
                    player.sendMessage(prefix + "§e§lYou have been killed with a bow by §6§l" + shooter.getName());
                    shooter.sendMessage(prefix + "§e§lYou killed §6§l" + player.getName());
                }
            }
            player.setFallDistance(0F);
            event.setCancelled(true);
            player.setHealth(20);
            RunningArena ra = ram.isInGame(player);

            ItemStack bottle = new ItemStack(Material.GLASS_BOTTLE, 1);
            ItemMeta bottleMeta = bottle.getItemMeta();
            bottleMeta.setDisplayName("§7§lEmpty Bottle");
            bottle.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            bottle.setItemMeta(bottleMeta);

            RespawnTimer rt = new RespawnTimer(4, player, ra, km);
            rt.runTaskTimer(Main.getInstance(), 0, 20L);

            PotionEffect sideEffect1 = new PotionEffect(PotionEffectType.BLINDNESS, 400, 1);
            PotionEffect sideEffect2 = new PotionEffect(PotionEffectType.WEAKNESS, 400, 4);

            if(player.getInventory().contains(bottle)){
                    player.getInventory().remove(bottle);
                    player.removePotionEffect(sideEffect1.getType());
                    player.removePotionEffect(sideEffect2.getType());
                    Location playerLoc = player.getEyeLocation();
                    World world = player.getWorld();
                    world.dropItemNaturally(playerLoc, bottle);
                    if(event.getCause().equals(EntityDamageEvent.DamageCause.VOID)){
                        for(Player player1 : ra.getPlayers()){
                            player1.sendMessage(prefix + player.getName() + " has fallen into the void and lost the bottle!");
                        }
                        BottleSpawnTimer bst = new BottleSpawnTimer(ra);
                        bst.runTaskTimer(Main.getInstance(), 0, 20L);
                        return;
                    }
                    else if(ra.getTeamBlue().contains(player)){
                        for(Player player1 : ra.getPlayers()){
                            player1.sendMessage(prefix + player.getName() + " from §b§lTeam Blue §r§7has dropped the bottle!");
                        }
                    }
                    else if(ra.getTeamRed().contains(player)){
                        for(Player player1 : ra.getPlayers()){
                            player1.sendMessage(prefix + player.getName() + " from §c§lTeam Red §r§7has dropped the bottle!");
                        }
                    }
            }

        }
    }

}

