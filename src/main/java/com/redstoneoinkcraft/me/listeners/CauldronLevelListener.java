package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.material.Cauldron;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class CauldronLevelListener implements Listener {

    /* Lmao, so much non-used code for one small line */

    @EventHandler
    public void onCauldronLevelChange(CauldronLevelChangeEvent event){
        CauldronLevelChangeEvent.ChangeReason reason = event.getReason();
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        Player player = (Player) event.getEntity();
        RunningArenaManager ram = RunningArenaManager.getManager();
        if(ram.isInGame(player) == null){
            return;
        }
        RunningArena ra = ram.isInGame(player);
        Block involvedCauldronBlock = event.getBlock();
        FileConfiguration config = Main.getInstance().getConfig();
        for(String arenaName: config.getStringList("list-of-arenas")){
            World world = event.getBlock().getWorld();
            Block blueCauldronBlock = ra.getBlueCauldron();
            Block redCauldronBlock = ra.getRedCauldron();
            Cauldron involvedCauldron = (Cauldron) involvedCauldronBlock.getState().getData();
            Cauldron blueCauldron = (Cauldron) blueCauldronBlock.getState().getData();
            Cauldron redCauldron = (Cauldron) redCauldronBlock.getState().getData();
            if(!involvedCauldronBlock.equals(redCauldronBlock) && !involvedCauldronBlock.equals(blueCauldronBlock)){
                return;
            }

            else if(involvedCauldronBlock.equals(blueCauldronBlock) || involvedCauldronBlock.equals(redCauldronBlock)){
                event.setCancelled(true); // I am manually changing levels in the interact event.
                player.sendMessage(Main.getInstance().getPrefix() + "Oi! No changing the cauldron levels while in a game.");
            }
        }
    }

}
