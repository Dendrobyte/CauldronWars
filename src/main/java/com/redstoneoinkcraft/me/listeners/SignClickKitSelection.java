package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.kits.KitBase;
import com.redstoneoinkcraft.me.kits.KitManager;
import com.redstoneoinkcraft.me.utilities.KitSelectionInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by Mark on 4/18/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class SignClickKitSelection implements Listener {

    @EventHandler
    public void onSignClickForKitSelection(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block blockClicked = event.getClickedBlock();
        KitManager km = KitManager.getManager();
        RunningArenaManager ram = RunningArenaManager.getManager();
        String prefix = Main.getInstance().getPrefix();

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(blockClicked.getType().equals(Material.WALL_SIGN) || blockClicked.getType().equals(Material.SIGN)){
                Sign sign = (Sign) blockClicked.getState();
                String line1 = sign.getLine(0); // Main Headline
                String line2 = sign.getLine(1); // Kit Name

                if(line1.equalsIgnoreCase("§c[CWars Leave]")) {
                    if (ram.isInGame(player) == null) {
                        player.sendMessage(prefix + "You aren't in a game!");
                        return;
                    }
                    if (ram.isInGame(player) != null) {
                        if (ram.isInGame(player).getGameState().equals(RunningArena.GameState.IN_PROGRESS)) {
                            player.sendMessage(prefix + "You can not leave while a game is in progress.");
                            return;
                        }
                        player.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
                        player.sendMessage(prefix + "You have left arena with ID " + ram.isInGame(player).getId());
                        ram.removePlayer(player);
                    }
                }

                if(line1.equalsIgnoreCase("§9[CWars Kit]")){
                    if(ram.isInGame(player) == null){
                        player.sendMessage(prefix + "Sorry, you aren't in a game. What sorcery is this?!?");
                        return;
                    }
                    if(line2.isEmpty()){
                        // Open Inventory
                        KitSelectionInventory ksl = new KitSelectionInventory();
                        player.openInventory(ksl.getKitInv());
                        return;
                    }
                    if(!ram.isInGame(player).getGameState().equals(RunningArena.GameState.IN_PROGRESS)){
                        player.sendMessage(prefix + "You can't choose a kit yet!");
                        return;
                    }
                    /* This be old stuff
                     * KitBase kit = km.getKitByName(line2);
                     * km.removeKit(player);
                     * km.addKit(kit, player); */
                    return;
                }
            }
        }
    }

}
