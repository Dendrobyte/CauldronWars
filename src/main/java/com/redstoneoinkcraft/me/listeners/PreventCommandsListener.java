package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 5/11/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class PreventCommandsListener implements Listener {

    RunningArenaManager ram = RunningArenaManager.getManager();
    List<String> allowedCommands = new ArrayList<String>();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerTypesCommand(PlayerCommandPreprocessEvent event){
        String command = event.getMessage().substring(1);
        Player player = event.getPlayer();
        allowedCommands.add("cwars");
        allowedCommands.add("shout");
        allowedCommands.add("reply");
        allowedCommands.add("msg");
        allowedCommands.add("sc");
        if(event.getMessage().length() < 2){ // There is nothing there
            return;
        }
        if(event.getMessage().substring(0, 2).contains("mc")){
            player.sendMessage(Main.getInstance().getPrefix() + "No using Magic Carpet in the minigame world.");
            event.setCancelled(true);
            return;
        }

        RunningArena ra = ram.isInGame(player);
        if(ra == null){
            return;
        }
        for(String cmd : allowedCommands){
            if(command.contains(cmd)){
                return;
            }
        }
        if(!ra.getGameState().equals(RunningArena.GameState.OVER)){
            for(String word : allowedCommands){
                if(command.length() >= 7) {
                    if (command.substring(0, 6).contains(word)) {
                        return;
                    }
                }
            }
            event.setCancelled(true);
            player.sendMessage(Main.getInstance().getPrefix() + "That command is not allowed while in a game.");
            return;
        }

    }

}
