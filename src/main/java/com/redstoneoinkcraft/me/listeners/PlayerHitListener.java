package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class PlayerHitListener implements Listener {

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event){
        if((event.getDamager() instanceof Player) && (event.getEntity() instanceof Player)) {
            Player attacker = (Player) event.getDamager();
            Player defender = (Player) event.getEntity();
            RunningArenaManager ram = RunningArenaManager.getManager();
            if(ram.isInGame(attacker) == null || ram.isInGame(defender) == null){
                return;
            }
            if(ram.isInGame(attacker).getGameState().equals(RunningArena.GameState.WAITING) || ram.isInGame(attacker).getGameState().equals(RunningArena.GameState.STARTING) || ram.isInGame(attacker).getGameState().equals(RunningArena.GameState.ENDING) || ram.isInGame(attacker).getGameState().equals(RunningArena.GameState.OVER)){
                event.setCancelled(true);
                return;
            }
            RunningArena ra = ram.isInGame(attacker); // Works for both, anyway

            // Different team special items
            ItemStack item = attacker.getInventory().getItemInMainHand();
            // Mummy Items
            if(item.getItemMeta().getDisplayName().equals("§c§lSickening Flesh")){
                hungerChanceWithFlesh(attacker, defender);
                return;
            }
            if(item.getItemMeta().getDisplayName().equals("§7§lLeftover Mummy Wraps")){
                cobwebsChanceOnHit(attacker, defender);
                return;
            }

            // Same team special items
            if(ra.getTeamBlue().contains(attacker) && ra.getTeamBlue().contains(defender)){

                // Monk Items
                if(item.getItemMeta().getDisplayName().equals("§6§lMonk's Healing Melon")){
                    healOnMelonHit(attacker, defender);
                    event.setCancelled(true);
                    return;
                }

                else {
                    attacker.sendMessage(Main.getInstance().getPrefix() + "§c§lThat player is on your team!");
                    event.setCancelled(true);
                    return;
                }
            }
            if(ra.getTeamRed().contains(attacker) && ra.getTeamRed().contains(defender)){
                // Item exceptions go here

                // Monk Items
                if(item.getItemMeta().getDisplayName().equals("§6§lMonk's Healing Melon")){
                    healOnMelonHit(attacker, defender);
                    event.setCancelled(true);
                    return;
                }

                else {
                    attacker.sendMessage(Main.getInstance().getPrefix() + "§c§lThat player is on your team!");
                    return;
                }
            }

        }
        if(event.getDamager() instanceof Projectile && event.getEntity() instanceof Player){
            ProjectileSource projSource = ((Projectile) event.getDamager()).getShooter();
            if(!(projSource instanceof Player)){
                return;
            }
            if(event.getDamager().getType().equals(EntityType.ARROW)){
                Player attacker = (Player) ((Projectile) event.getDamager()).getShooter();
                Player defender = (Player) event.getEntity();
                RunningArenaManager ram = RunningArenaManager.getManager();
                if(ram.isInGame(attacker) == null || ram.isInGame(defender) == null){
                    return;
                }
                if(ram.isInGame(attacker).getGameState().equals(RunningArena.GameState.WAITING) || ram.isInGame(attacker).getGameState().equals(RunningArena.GameState.STARTING) || ram.isInGame(attacker).getGameState().equals(RunningArena.GameState.ENDING) || ram.isInGame(attacker).getGameState().equals(RunningArena.GameState.OVER)){
                    event.setCancelled(true);
                    return;
                }
                RunningArena ra = ram.isInGame(attacker); // Works for both, anyway

                if(ra.getTeamBlue().contains(attacker) && ra.getTeamBlue().contains(defender)){
                    attacker.sendMessage(Main.getInstance().getPrefix() + "§c§lThat player is on your team!");
                    event.setCancelled(true);
                    return;
                }
                if(ra.getTeamRed().contains(attacker) && ra.getTeamRed().contains(defender)){
                    attacker.sendMessage(Main.getInstance().getPrefix() + "§c§lThat player is on your team!");
                    event.setCancelled(true);
                    return;
                }
            }
        } else {
            return;
        }
    }
    private void healOnMelonHit(Player attacker, Player defender){
        if(defender.getHealth() < 19){
            if(defender.getHealth() % 2 == 0){
                defender.sendMessage(Main.getInstance().getPrefix() + "§a§lYou have been healed by §2§l" + attacker.getName());
            }
            defender.setHealth(defender.getHealth() + 1);
            attacker.sendMessage(Main.getInstance().getPrefix() + "§a§lYou have healed §2§l" + defender.getName());
            Bukkit.getServer().getWorld(defender.getWorld().getName()).spawnParticle(Particle.HEART, defender.getEyeLocation(), 1, 1, 1, 1, 20);
        } else {
            attacker.sendMessage(Main.getInstance().getPrefix() + "§a§lTheir health is full!");
        }
    }

    private void hungerChanceWithFlesh(Player attacker, Player defender){
        Random rand = new Random();
        int chance = rand.nextInt(4) + 1;
        if(chance == 2){ // 25% odds
            defender.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20*5, 200));
            defender.sendMessage(Main.getInstance().getPrefix() + "A mummy has inflicted hunger upon you!");
            attacker.sendMessage(Main.getInstance().getPrefix() + "You have inflicted hunger upon " + defender.getName());
        } else {
            // Chances were not met!
        }
    }

    private void cobwebsChanceOnHit(Player attacker, Player defender){
        Random rand = new Random();
        int chance = rand.nextInt(3) + 1;
        if(chance == 2) { // 33% odds
            defender.sendMessage(Main.getInstance().getPrefix() + "You have been wrapped by " + attacker.getName() + "!");
            attacker.sendMessage(Main.getInstance().getPrefix() + "You have wrapped " + defender.getName());

            // Place webs
            ArrayList<Location> cobwebsToClear = new ArrayList<>();
            Location defLoc = defender.getLocation();
            int defLocX = defLoc.getBlockX();
            int defLocY = defLoc.getBlockY();
            int defLocZ = defLoc.getBlockZ();
            if(defLoc.getBlock().getType().equals(Material.AIR)){ // Block at feet
                defLoc.getBlock().setType(Material.WEB);
                cobwebsToClear.add(defLoc);
            }
            // Time to try this the simple yet tedious way!
            ArrayList<Location> blocksToWebs = new ArrayList<>();
            Location oneUp = new Location(defLoc.getWorld(), defLocX, defLocY, defLocZ+1);
            Location oneUpOneRight = new Location(defLoc.getWorld(), defLocX+1, defLocY, defLocZ+1);
            Location oneUpOneLeft = new Location(defLoc.getWorld(), defLocX-1, defLocY, defLocZ+1);
            Location oneLeft = new Location(defLoc.getWorld(), defLocX-1, defLocY, defLocZ);
            Location oneRight = new Location(defLoc.getWorld(), defLocX+1, defLocY, defLocZ);
            Location oneDown = new Location(defLoc.getWorld(), defLocX, defLocY, defLocZ-1);
            Location oneDownOneRight = new Location(defLoc.getWorld(), defLocX+1, defLocY, defLocZ-1);
            Location oneDownOneLeft = new Location(defLoc.getWorld(), defLocX-1, defLocY, defLocZ-1);
            blocksToWebs.add(oneUp);
            blocksToWebs.add(oneUpOneRight);
            blocksToWebs.add(oneUpOneLeft);
            blocksToWebs.add(oneLeft);
            blocksToWebs.add(oneRight);
            blocksToWebs.add(oneDown);
            blocksToWebs.add(oneDownOneRight);
            blocksToWebs.add(oneDownOneLeft);
            for(Location loc : blocksToWebs){
                if(loc.getBlock().getType().equals(Material.AIR)){
                    loc.getBlock().setType(Material.WEB);
                    cobwebsToClear.add(loc);
                }
            }

            // For loops were 30 lines;
            MummyTimer mT = new MummyTimer(5, cobwebsToClear); // Start cobweb reset timer
            mT.runTaskTimer(Main.getInstance(), 0, 20);

            // Send attacker backwards
            int playerVecX = attacker.getVelocity().getBlockX();
            int playerVecZ = attacker.getVelocity().getBlockZ();
            attacker.setVelocity(new Vector(((-1)*playerVecX + 1), .5, ((-1)*playerVecZ) + 1)); // Send the player opposite of the above directions
        }
    }
}

class MummyTimer extends BukkitRunnable{
    int counter;
    ArrayList<Location> cobwebsToClear;

    public MummyTimer(int seconds, ArrayList<Location> cobwebsToClear){
        this.counter = seconds; // Multiply by 20 because ticks
        this.cobwebsToClear = cobwebsToClear;
    }

    @Override
    public void run(){
        if(this.counter == 0) {
            for (Location loc : cobwebsToClear) {
                if(loc.getBlock().getType().equals(Material.WEB)) loc.getBlock().setType(Material.AIR);
            }
            this.cancel();
        }
        this.counter--;
    }

}
