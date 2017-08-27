package com.redstoneoinkcraft.me.timers;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Mark on 4/20/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class GatesOpenTimer extends BukkitRunnable {
        int time;
        String prefix = Main.getInstance().getPrefix();
        RunningArena ra;

        public GatesOpenTimer(){
            this.time = 5;
        }

        public void setRa(RunningArena ra){
            this.ra = ra;
        }

        @Override
        public void run(){
            if(time == 0){
                cancel();
                for(Player player : ra.getPlayers()){
                    // Open gates...?
                    player.sendMessage(prefix + "The gates have opened, good luck and §ohave fun!");
                    player.sendMessage(prefix + "For now, the gates have already opened. I need to implement this feature =)");
                }
            }
            else if(time != 0){
                time--;
                this.time = time;
            }
        }
    }
