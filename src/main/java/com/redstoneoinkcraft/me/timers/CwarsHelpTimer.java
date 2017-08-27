package com.redstoneoinkcraft.me.timers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Author - markbacon78
 * File created on 8/21/17
 * This project is property of markbacon78 and was created for single user purposes only.
 * Do not copy or redistribute this document (if you have somehow gotten access to the code...)
 * §
 */
public class CwarsHelpTimer extends BukkitRunnable {

    int counter;
    Player player;

    public CwarsHelpTimer(Player player){
        this.counter = 40;
        this.player = player;
    }

    @Override
    public void run(){
        if(counter == 40) player.sendMessage("§a§lHow to Play: §cCauldron§bWars");
        if(counter == 38) player.sendMessage("§a§lAim of the Game: §7Empty the opposing team's cauldron!");
        if(counter == 36) player.sendMessage("§a§lHow the Game is Played: §7Cauldron Wars is a primarily team-based player versus player game.");
        if(counter == 34) player.sendMessage("§a§lWin Condition: §7One team's cauldron is completely empty");
        if(counter == 32) player.sendMessage("§a§lThe Main Goal: §7To have fun and work as a team.");
        if(counter == 30) player.sendMessage("§a§l-+Game Mechanics+-");
        if(counter == 28) player.sendMessage("§7Your goal is to obtain an empty bottle that can be used to empty your opponents' cauldron.");
        if(counter == 26) player.sendMessage("§7These bottles will randomly spawn within a pre-set region of the map. They are clearly seen with large fireworks and loud noises.");
        if(counter == 24) player.sendMessage("§7When a bottle is picked up by a player, the player is inhibited with various negative effects, such as slowness and blindness.");
        if(counter == 22) player.sendMessage("§7Their goal is to reach the opposing team's cauldron using the compass navigator placed into their inventory.");
        if(counter == 20) player.sendMessage("§7If a player with a bottle is killed, they drop the bottle which can then be picked up by another player.");
        if(counter == 18) player.sendMessage("§7Once a player with a bottle empties the cauldron, it drops a level.");
        if(counter == 16) player.sendMessage("§7Cauldrons only have 3 levels, so once they're all gone the game ends!");
        if(counter == 14) player.sendMessage("§7The team whose cauldron is not empty has won the game!");
        if(counter == 12) player.sendMessage("§a§lQuestions? Is this menu missing something?");
        if(counter == 10) player.sendMessage("§7Ask the community or contact a staff member. ");
        if(counter == 0) this.cancel();
        this.counter--;
    }
}