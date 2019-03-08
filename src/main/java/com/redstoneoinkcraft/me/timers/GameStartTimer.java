package com.redstoneoinkcraft.me.timers;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.kits.Gladiator;
import com.redstoneoinkcraft.me.kits.KitManager;
import org.bukkit.Bukkit;
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
public class GameStartTimer extends BukkitRunnable {

    RunningArena ra;
    int timeRemaining;

    public GameStartTimer(){
        this.timeRemaining = 5;
    }

    public void setRa(RunningArena ra){
        this.ra = ra;
    }

    public int getTimeRemaining(){
        return timeRemaining;
    }

    @Override
    public void run(){
        RunningArenaManager ram = RunningArenaManager.getManager();
        KitManager km = KitManager.getManager();
        String prefix = Main.getInstance().getPrefix();

            if(timeRemaining == 20){
                for(Player player : ra.getPlayers()){
                    player.sendMessage(prefix + "The game will start in §d20 seconds!");
                }
            }
            if(timeRemaining == 10){
                for(Player player : ra.getPlayers()){
                    player.sendMessage(prefix + "The game will start in §d10 seconds!");
                }
            }
            if(timeRemaining == 3){
                for(Player player : ra.getPlayers()){
                    player.sendMessage(prefix + "The game is starting in §d3 seconds!");
                }
            }
            if(timeRemaining == 2){
                for(Player player : ra.getPlayers()){
                    player.sendMessage(prefix + "The game is starting in §d2 seconds!");
                }
            }
            if(timeRemaining == 1){
                for(Player player : ra.getPlayers()){
                    player.sendMessage(prefix + "The game is starting in §d1 second!");
                }
            }
            if(timeRemaining == 0){
                cancel();
                for(Player player1 : ra.getTeamBlue()){
                    player1.teleport(ra.getBlueSpawn());
                }
                for(Player player1 : ra.getTeamRed()){
                    player1.teleport(ra.getRedSpawn());
                }
                for(Player player : ra.getPlayers()){
                    player.sendMessage(prefix + "You may now select your kit!");
                    player.sendMessage(prefix + "Good luck and §ohave fun!");
                    km.addKit(new Gladiator(), player);
                    // player.sendMessage(prefix + "Select your kits! The gates will open in §d5 seconds!");
                    ra.setGameState(RunningArena.GameState.IN_PROGRESS);

                }

                ram.getArenaJoinSigns().get(ra.getId()).setLine(3, "§2§lIN PROGRESS");
                ram.getArenaJoinSigns().get(ra.getId()).update();

                // Start bottle spawning timer
                BottleSpawnTimer bst = new BottleSpawnTimer(ra);
                bst.runTaskTimer(Main.getInstance(), 0, 20L);

                /* Gates haven't been created or implemented yet... spawn killing may become an issue :/
                GatesOpenTimer got = new GatesOpenTimer(); // Start gate timer
                got.setRa(ra);
                got.runTaskTimer(Main.getInstance(), 0, 20); */
            }
            timeRemaining--;
            this.timeRemaining = timeRemaining;
    }
}
