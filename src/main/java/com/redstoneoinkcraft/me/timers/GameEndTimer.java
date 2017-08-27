package com.redstoneoinkcraft.me.timers;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.kits.KitManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Mark on 4/21/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class GameEndTimer extends BukkitRunnable {

    RunningArenaManager ram = RunningArenaManager.getManager();
    KitManager km = KitManager.getManager();
    RunningArena ra;
    int timer = 5;

    public GameEndTimer(RunningArena ra){
        this.ra = ra;
    }

    @Override
    public void run(){
        if(this.timer != 0){
            this.timer--;
        }
        if(this.timer == 0){
            for(Player player : ra.getPlayers()){
                player.sendMessage(Main.getInstance().getPrefix() + "Thanks for playing!");
                ram.removePlayer(player);
            }
            ra.setGameState(RunningArena.GameState.OVER);
            ra.getPlayers().clear();
            cancel();
        }
    }

}
