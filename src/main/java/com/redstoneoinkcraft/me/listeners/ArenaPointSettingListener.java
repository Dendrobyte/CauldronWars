package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.ArenaManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

/**
 * Created by Mark on 3/20/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class ArenaPointSettingListener implements Listener {

    ArenaManager am = ArenaManager.getManager();
    String prefix = Main.getInstance().getPrefix();

    @EventHandler
    public void onPlayerWandInteract(PlayerInteractEvent event){
        ItemStack pointWand = new ItemStack(Material.FEATHER, 1);
        ItemMeta pointWandMeta = pointWand.getItemMeta();
        pointWandMeta.setDisplayName("§9§lCWars Arena Creation Wand");
        pointWand.setItemMeta(pointWandMeta);
        Player player = event.getPlayer();

        if(player.getInventory().getItemInMainHand().equals(pointWand)){
            Block block = event.getClickedBlock();
            if(block == null){
                return;
            }
            UUID pID = player.getUniqueId();
            double newBlockY = block.getLocation().getBlockY() + 1;
            block.getLocation().setY(newBlockY);
            Location blockLoc = block.getLocation();
            if(event.getAction() == Action.LEFT_CLICK_BLOCK){
                if(!am.getPointStorage().containsKey(pID)){ // Mighty meme!
                    // am.getPointStorage().put(player.getUniqueId(), new ArenaPoints(null, null, null, null, null, null, null));
                    player.sendMessage(prefix + "You are going to need to use the /cwarsarena create command first!");
                    event.setCancelled(true);
                    return;
                }
                if(am.getPointStorage().get(pID).getLobby() == null){
                    am.getPointStorage().get(pID).setLobby(blockLoc);
                    event.setCancelled(true);
                    player.sendMessage(prefix + "Lobby location set!");
                    player.sendMessage(prefix + "Now please select §b§lTeam Blue's spawn location.");
                    return;
                }
                if(am.getPointStorage().get(pID).getBlueSpawn() == null){
                    am.getPointStorage().get(pID).setBlueSpawn(blockLoc);
                    event.setCancelled(true);
                    player.sendMessage(prefix + "Blue Team's spawn has been set!");
                    player.sendMessage(prefix + "Now please select §b§lTeam Blue's cauldron location.");
                    return;
                }
                if(am.getPointStorage().get(pID).getBlueCauldron() == null){
                    if(block.getType() != Material.CAULDRON){
                        player.sendMessage(prefix + "Silly, that's not a cauldron!");
                        event.setCancelled(true);
                        return;
                    }
                    am.getPointStorage().get(pID).setBlueCauldron(blockLoc);
                    event.setCancelled(true);
                    player.sendMessage(prefix + "Blue Team's cauldron location has been set!");
                    player.sendMessage(prefix + "Now please select §c§lTeam Red's spawn location.");
                    return;
                }
                if(am.getPointStorage().get(pID).getRedSpawn() == null){
                    am.getPointStorage().get(pID).setRedSpawn(blockLoc);
                    event.setCancelled(true);
                    player.sendMessage(prefix + "Red Team's spawn has been set!");
                    player.sendMessage(prefix + "Now please select §c§lTeam Red's cauldron location.");
                    return;
                }
                if(am.getPointStorage().get(pID).getRedCauldron() == null){
                    if(block.getType() != Material.CAULDRON){
                        player.sendMessage(prefix + "Silly, that's not a cauldron!");
                        event.setCancelled(true);
                        return;
                    }
                    am.getPointStorage().get(pID).setRedCauldron(blockLoc);
                    event.setCancelled(true);
                    player.sendMessage(prefix + "Red Team's cauldron location has been set!");
                    player.sendMessage(prefix + "Now please select the §7§lfirst bound for bottle spawning.");
                    return;
                }
                if(am.getPointStorage().get(pID).getBounds1() == null){
                    am.getPointStorage().get(pID).setBounds1(block.getLocation());
                    event.setCancelled(true);
                    player.sendMessage(prefix + "First bound has been set!");
                    player.sendMessage(prefix + "Now please select the §7§lsecond bound for bottle spawning.");
                    return;
                }
                if(am.getPointStorage().get(pID).getBounds2() == null){
                    am.getPointStorage().get(pID).setBounds2(block.getLocation());
                    event.setCancelled(true);
                    player.sendMessage(prefix + "Second bound has been set!");
                    player.sendMessage(prefix + "Mission complete! Make sure to type /cwarsarena confirm so that no data is lost.");
                    return;
                } else {
                    player.sendMessage(prefix + "All points have been set! Please use /cwarsarena confirm so that no data is lost.");
                    event.setCancelled(true);
                    return;
                }
            }
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(event.getHand() == EquipmentSlot.HAND || event.getHand() == EquipmentSlot.OFF_HAND) {
                    player.sendMessage(prefix + "Please use your LEFT click to set locations.");
                }
            }
        }
        if(!player.getInventory().getItemInMainHand().equals(pointWand)){
            // Do nothing
        }

    }

}
