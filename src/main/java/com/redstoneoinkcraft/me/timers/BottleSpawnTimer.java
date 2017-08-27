package com.redstoneoinkcraft.me.timers;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.utilities.BottleSpawning;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Created by Mark on 4/20/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class BottleSpawnTimer extends BukkitRunnable {

    RunningArenaManager ram = RunningArenaManager.getManager();
    RunningArena ra;
    String prefix = Main.getInstance().getPrefix();
    int counter;
    BottleSpawning bottleSpawning = new BottleSpawning();
    public int arenaIDForBottleSpawning;

    public int getArenaIDForBottleSpawning(){
        return this.arenaIDForBottleSpawning;
    }

    public BottleSpawnTimer(RunningArena ra) {

        int longest = 20; // In seconds
        int shortest = 10; // In seconds
        Random rand = new Random();
        this.counter = rand.nextInt(longest - shortest) + shortest;
        this.ra = ra;
    }

    @Override
    public void run() {
        int timer = this.counter;
        if(ra.getGameState().equals(RunningArena.GameState.OVER)){
            cancel();
            return;
        }
        if (timer == 3) {
            for (Player player : ra.getPlayers()) {
                player.sendMessage(prefix + "A bottle will spawn in §d3 seconds!");
                player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 3.0F, 0.5F);
            }
        }
        if (timer == 0) {
            this.arenaIDForBottleSpawning = ra.getId();
            ItemStack bottle = bottleSpawning.generateBottle(ra);
            Location spawnLoc = null;

            while(spawnLoc == null){ // While it's null (y value is has void under it) get a new location
                spawnLoc = bottleSpawning.generateBottleLocation(ra);
            }
            Location spawnParticlesLoc = new Location(ra.getLobby().getWorld(), spawnLoc.getX(), spawnLoc.getY() + 20, spawnLoc.getZ());
            spawnParticles(spawnParticlesLoc, 20);

            if(bottle.equals(null)){
                System.out.println("Something went wrong with the bottle timer.");
                cancel();
                return;
            }
            World world = ra.getLobby().getWorld();
            if(!ra.getGameState().equals(RunningArena.GameState.IN_PROGRESS)){
                cancel();
                return;
            }
            world.dropItem(spawnLoc, bottle);
            BottleDespawnTimer bdt = new BottleDespawnTimer(bottle, spawnLoc, ra);
            bdt.runTaskTimer(Main.getInstance(), 0, 20L);
            cancel();
        }
        timer--;
        this.counter = timer;

    }

    public void spawnParticles(Location loc, int count){
        ParticleSpawningTimer pst = new ParticleSpawningTimer(loc, count);
        pst.runTaskTimer(Main.getInstance(), 0, 5L);
    }
}
