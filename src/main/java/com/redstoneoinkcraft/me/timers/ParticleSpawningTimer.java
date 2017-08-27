package com.redstoneoinkcraft.me.timers;

import com.redstoneoinkcraft.me.Main;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Mark on 4/20/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class ParticleSpawningTimer extends BukkitRunnable {

    Location loc;
    int count;

    public ParticleSpawningTimer(Location loc, int count) {
        this.loc = loc;
        this.count = count;
    }

    private void createFirework(Location loc){
        Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwMeta = firework.getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.withTrail().withColor(Color.TEAL, Color.MAROON).with(FireworkEffect.Type.BALL_LARGE);
        fwMeta.setPower(1);
        fwMeta.addEffect(builder.build());
        firework.setFireworkMeta(fwMeta);
        firework.teleport(loc);
        // QuickTimer qt = new QuickTimer(firework);
        // qt.runTaskLater(Main.getInstance(), 1L);
        }

    @Override
    public void run(){
            if(count != 0) {
                createFirework(loc);
                count--;
                double newYLoc = loc.getY() - 2;
                Location newLoc = new Location(loc.getWorld(), loc.getX(), newYLoc, loc.getZ());
                this.loc = newLoc;
                this.count = count;
            }
            if(count == 0) {
                cancel();
            }
    }

}

class QuickTimer extends BukkitRunnable{

    int counter = 1;
    Firework firework;

    QuickTimer(Firework firework){
        this.firework = firework;
    }

    @Override
    public void run() {
        if(counter != 0){
            counter--;
        } else {
            firework.detonate();
        }
    }
}
