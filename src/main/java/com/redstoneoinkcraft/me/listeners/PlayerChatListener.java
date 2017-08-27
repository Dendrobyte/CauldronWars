package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.Set;

/**
 * Created by Mark on 4/22/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class PlayerChatListener implements Listener {

    RunningArenaManager ram = RunningArenaManager.getManager();
    String newMessage;
    String playerName;
    String coloredName;
    String suffix;
    String prefix = "§7[§bC§cW§7]";
    String teamPrefix;

    @EventHandler
    public void playerTalksInGame(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();

        RunningArena ra = ram.isInGame(player);
        if(ra == null){
            Set<Player> players = event.getRecipients();
            for(RunningArena ra1 : ram.getRunningArenas()){
                for(Player player1 : ra1.getPlayers()){
                    players.remove(player1);
                }
            }
            return;
        }
        if(ra != null){
            playerName = player.getName();
            event.setCancelled(true);
            if(message.substring(0, 1).equals("@")){
                if(ra.getTeamBlue().contains(player)){
                    this.teamPrefix = "§7[§bTeam Blue§7] ";
                    this.coloredName = "§b" + playerName;
                    this.suffix = "§8> §7";
                }
                if(ra.getTeamRed().contains(player)){
                    this.teamPrefix = "§7[§cTeam Red§7] ";
                    this.coloredName = "§c" + playerName;
                    this.suffix = "§8> §7";
                }
                this.newMessage = prefix + teamPrefix + coloredName + suffix + message.substring(1);
                for(Player player1 : ra.getPlayers()){
                    player1.sendMessage(this.newMessage);
                }
                return;
            }

            String oldMessage = message;
            if(ra.getTeamBlue().contains(player)){
                this.teamPrefix = "§8[§aTeam Chat§8] ";
                this.coloredName = "§b" + playerName;
                this.suffix = "§8> §3";
                this.newMessage = prefix + teamPrefix + coloredName + suffix + oldMessage;
                for(Player player1 : ra.getTeamBlue()){
                    player1.sendMessage(newMessage);
                }
            }
            if(ra.getTeamRed().contains(player)){
                this.teamPrefix = "§8[§aTeam Chat§8] ";
                this.coloredName = "§c" + playerName;
                this.suffix = "§8> §c";
                this.newMessage = prefix + teamPrefix + coloredName + suffix + oldMessage;
                for(Player player1 : ra.getTeamRed()){
                    player1.sendMessage(newMessage);
                }
            }
            return;
        }
    }

    // If a player talks and they are in game, only send it to the players in the game.

    // Cancel the message and resend it with the format you want so it doesn't show rank etc

}
