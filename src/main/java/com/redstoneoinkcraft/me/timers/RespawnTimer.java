package com.redstoneoinkcraft.me.timers;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.kits.KitManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Mark on 4/20/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
@SuppressWarnings("deprecation")
public class RespawnTimer extends BukkitRunnable {

    int counter; // Wait to respawn in X seconds
    Player player;
    RunningArena ra;
    KitManager km;

    public RespawnTimer(int counter, Player player, RunningArena ra, KitManager km){
        this.counter = counter;
        this.player = player;
        this.ra = ra;
        this.km = km;
    }

    @Override
    public void run(){
        if (ra.getTeamBlue().contains(player)) player.teleport(ra.getBlueSpawn());
        if (ra.getTeamRed().contains(player)) player.teleport(ra.getRedSpawn());
        player.setGameMode(GameMode.ADVENTURE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1));

        if(this.counter > 1) {
            Main.getInstance().sendActionChatPacket(player, "§4§lYOU DIED §r§7- §2§lRespawning in " + this.counter + " seconds");
            player.sendTitle(Main.getInstance().getPrefix(), "§4§lYOU DIED §r§7- §2§lRespawning in " + this.counter + " seconds");
            this.counter--;
        }
        else if(this.counter == 1){
            Main.getInstance().sendActionChatPacket(player, "§4§lYOU DIED §r§7- §2§lRespawning in " + this.counter + " second");
            player.sendTitle(Main.getInstance().getPrefix(), "§4§lYOU DIED §2§lRespawning in " + this.counter + " second", 20, 40, 20);
            this.counter--;
        }
        else if(this.counter == 0) {
            cancel();
            Main.getInstance().sendActionChatPacket(player, "§2§lRESPAWN SUCCESSFUL");
            player.sendTitle(Main.getInstance().getPrefix(), "§2§lRESPAWN SUCCESSFUL", 20, 40, 20);

            // Do things
            player.getInventory().clear();
            player.setFireTicks(0);
            player.setGameMode(GameMode.SURVIVAL);
            if (!ra.getPlayerKits().containsKey(player)) {
                System.out.println("Player not found in the hashmap!");
                return;
            }
            km.removeKit(player); // Remove to refresh timing and such
            km.addKit(ra.getPlayerKits().get(player), player);

            // For invincibility
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 99));

            // Back to spawn!
            if (ra.getTeamBlue().contains(player)) {
                player.teleport(ra.getBlueSpawn());
                return;
            }
            if (ra.getTeamRed().contains(player)) {
                player.teleport(ra.getRedSpawn());
                return;
            }
        }
    }
}
