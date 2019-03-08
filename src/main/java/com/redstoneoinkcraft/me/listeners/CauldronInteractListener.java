package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.timers.BottleSpawnTimer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Levelled;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Cauldron;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Mark on 4/20/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
@SuppressWarnings("deprecation")
public class CauldronInteractListener implements Listener {

    @EventHandler
    public void onCauldronRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        RunningArenaManager ram = RunningArenaManager.getManager();
        RunningArena ra = ram.isInGame(player);
        if(ra == null){
            return;
        }
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemStack bottle = new ItemStack(Material.GLASS_BOTTLE, 1);
        ItemMeta bottleMeta = bottle.getItemMeta();
        bottleMeta.setDisplayName("§7§lEmpty Bottle");
        bottle.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        bottle.setItemMeta(bottleMeta);


        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(event.getHand().equals(EquipmentSlot.HAND)){
                if(!(clickedBlock.getState().getData() instanceof  Cauldron)){
                    return;
                }
                if(clickedBlock.getState().getData() instanceof  Cauldron){
                    if(!heldItem.equals(bottle)){
                        return;
                    }
                    if(heldItem.equals(bottle)) {
                        String prefix = Main.getInstance().getPrefix();

                        /*

                        Block redCauldron = ra.getRedCauldron();
                        Levelled redCauldronData = (Levelled) redCauldron;
                        redCauldronData.setLevel(blueCauldronData.getMaximumLevel());
                        redCauldron.setBlockData(redCauldronData);

                         */

                        // Cauldron data
                        Block cauldron = clickedBlock;
                        Levelled cauldronData = (Levelled) cauldron.getBlockData();

                        PotionEffect sideEffect1 = new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1);
                        PotionEffect sideEffect2 = new PotionEffect(PotionEffectType.WEAKNESS, 999999, 4);
                        if (clickedBlock.equals(ra.getBlueCauldron())) {

                            // Full
                            if (cauldronData.getLevel() == cauldronData.getMaximumLevel()) {
                                event.setCancelled(true); // I'm controlling the levels
                                if (ra.getTeamBlue().contains(player)) {
                                    player.sendMessage(prefix + "That is your team's cauldron!");
                                    return;
                                }
                                if (ra.getTeamRed().contains(player)) {
                                    cauldronData.setLevel(cauldronData.getMaximumLevel()-1);
                                    cauldron.setBlockData(cauldronData);
                                    for (Player player1 : ra.getPlayers()) {
                                        player1.sendMessage(prefix + "§c§l" + player.getName() + " §7emptied §b§lTeam Blue's§r§7 cauldron!");
                                        player1.sendMessage(prefix + "§b§lTeam Blue's§r§7 cauldron is now 2 levels full.");
                                    }
                                    bottleDeposited(player, bottle, ra, sideEffect1, sideEffect2);
                                    return;
                                }
                            }
                            // Nearly full
                            if (cauldronData.getLevel() == cauldronData.getMaximumLevel()-1) {
                                event.setCancelled(true);
                                if (ra.getTeamBlue().contains(player)) {
                                    player.sendMessage(prefix + "That is your team's cauldron!");
                                    return;
                                }
                                if (ra.getTeamRed().contains(player)) {
                                    cauldronData.setLevel(cauldronData.getMaximumLevel()-2);
                                    cauldron.setBlockData(cauldronData);
                                    for (Player player1 : ra.getPlayers()) {
                                        player1.sendMessage(prefix + "§c§l" + player.getName() + " §7emptied §b§lTeam Blue's§r§7 cauldron!");
                                        player1.sendMessage(prefix + "§b§lTeam Blue's§r§7 cauldron is now 1 level full.");
                                    }
                                    bottleDeposited(player, bottle, ra, sideEffect1, sideEffect2);
                                    return;
                                }
                            }
                            // Nearly empty
                            if (cauldronData.getLevel() == cauldronData.getMaximumLevel()-2) {
                                event.setCancelled(true);
                                if (ra.getTeamBlue().contains(player)) {
                                    player.sendMessage(prefix + "That is your team's cauldron!");
                                    return;
                                }
                                if (ra.getTeamRed().contains(player)) {
                                    cauldronData.setLevel(cauldronData.getMaximumLevel()-3);
                                    cauldron.setBlockData(cauldronData);
                                    for (Player player1 : ra.getPlayers()) {
                                        player1.sendMessage(prefix + "§c§l" + player.getName() + " §7emptied §b§lTeam Blue's§r§7 cauldron!");
                                        player1.sendMessage(prefix + "§b§lTeam Blue's§r§7 cauldron is now empty");
                                    }
                                    bottleDeposited(player, bottle, ra, sideEffect1, sideEffect2);
                                    ra.setWinningTeam(false); // False means red team
                                    ram.end(ra.getId());
                                    return;
                                }
                            }
                            // Empty
                            if (cauldronData.getLevel() == 0) {
                                // This is not a possible value to get, seeing as the game would be over if the cauldron was empty
                                return;
                            }
                            return;
                        }
                        if (clickedBlock.equals(ra.getRedCauldron())) {
                            // Full
                            event.setCancelled(true); // I'm controlling the levels
                            if (cauldronData.getLevel() == cauldronData.getMaximumLevel()) {
                                if (ra.getTeamBlue().contains(player)) {
                                    cauldronData.setLevel(cauldronData.getMaximumLevel()-1);
                                    cauldron.setBlockData(cauldronData);
                                    for (Player player1 : ra.getPlayers()) {
                                        player1.sendMessage(prefix + "§b§l" + player.getName() + " §7emptied §c§lTeam Red's§r§7 cauldron!");
                                        player1.sendMessage(prefix + "§c§lTeam Red's§r§7 cauldron is now 2 levels full.");
                                    }
                                    bottleDeposited(player, bottle, ra, sideEffect1, sideEffect2);
                                    return;
                                }
                                if (ra.getTeamRed().contains(player)) {
                                    player.sendMessage(prefix + "That is your team's cauldron!");
                                    return;
                                }
                            }
                            // Nearly full
                            if (cauldronData.getLevel() == cauldronData.getMaximumLevel()-1) {
                                event.setCancelled(true);
                                if (ra.getTeamBlue().contains(player)) {
                                    cauldronData.setLevel(cauldronData.getMaximumLevel()-2);
                                    cauldron.setBlockData(cauldronData);
                                    for (Player player1 : ra.getPlayers()) {
                                        player1.sendMessage(prefix + "§b§l" + player.getName() + " §7emptied §c§lTeam Red's§r§7 cauldron!");
                                        player1.sendMessage(prefix + "§c§lTeam Red's§r§7 cauldron is now 1 level full.");
                                    }
                                    bottleDeposited(player, bottle, ra, sideEffect1, sideEffect2);
                                    return;
                                }
                                if (ra.getTeamRed().contains(player)) {
                                    player.sendMessage(prefix + "That is your team's cauldron!");
                                    return;
                                }
                            }
                            // Nearly empty
                            if (cauldronData.getLevel() == cauldronData.getMaximumLevel()-2) {
                                event.setCancelled(true);
                                if (ra.getTeamBlue().contains(player)) {
                                    cauldronData.setLevel(cauldronData.getMaximumLevel()-3);
                                    cauldron.setBlockData(cauldronData);
                                    for (Player player1 : ra.getPlayers()) {
                                        player1.sendMessage(prefix + "§b§l" + player.getName() + " §7emptied §c§lTeam Red's§r§7 cauldron!");
                                        player1.sendMessage(prefix + "§c§lTeam Red's§r§7 cauldron is empty.");
                                    }
                                    bottleDeposited(player, bottle, ra, sideEffect1, sideEffect2);
                                    ra.setWinningTeam(true); // True means blue team
                                    ram.end(ra.getId());
                                    return;
                                }
                                if (ra.getTeamRed().contains(player)) {
                                    player.sendMessage(prefix + "That is your team's cauldron!");
                                    return;
                                }
                            }
                            // Empty
                            if (cauldronData.getLevel() == 0) {
                                // This is not a possible value to get, seeing as the game would be over if the cauldron was empty
                                return;
                            }
                        } else {
                            System.out.println("Woopsy doopsy!");
                        }
                    }
                }
            }
        }

    }

    public void bottleDeposited(Player player, ItemStack bottle, RunningArena ra, PotionEffect sideEffect1, PotionEffect sideEffect2){
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName("§7§lCauldron Locator");
        compass.setItemMeta(compassMeta);
        player.getInventory().remove(compass);
        player.getInventory().remove(bottle);

        player.removePotionEffect(sideEffect1.getType());
        player.removePotionEffect(sideEffect2.getType());
        BottleSpawnTimer bst = new BottleSpawnTimer(ra);
        bst.runTaskTimer(Main.getInstance(), 0, 20L);
    }

}
