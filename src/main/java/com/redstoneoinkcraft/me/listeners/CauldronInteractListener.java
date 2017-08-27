package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.timers.BottleSpawnTimer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
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
import org.bukkit.material.MaterialData;
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

        ItemStack waterBottle = new ItemStack(Material.POTION);

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

                        Block cauldron = clickedBlock;
                        PotionEffect sideEffect1 = new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1);
                        PotionEffect sideEffect2 = new PotionEffect(PotionEffectType.WEAKNESS, 999999, 4);
                        if (clickedBlock.equals(ra.getBlueCauldron())) {
                            BlockState cauldState = clickedBlock.getState();
                            // Full
                            MaterialData md = cauldState.getData();
                            if (clickedBlock.getData() == (byte)3) {
                                event.setCancelled(true); // I'm controlling the levels
                                if (ra.getTeamBlue().contains(player)) {
                                    player.sendMessage(prefix + "That is your team's cauldron!");
                                    return;
                                }
                                if (ra.getTeamRed().contains(player)) {
                                    cauldState.getData().setData((byte) 2);
                                    cauldState.update();
                                    for (Player player1 : ra.getPlayers()) {
                                        player1.sendMessage(prefix + "§c§l" + player.getName() + " §7emptied §b§lTeam Blue's§r§7 cauldron!");
                                        player1.sendMessage(prefix + "§b§lTeam Blue's§r§7 cauldron is now 2 levels full.");
                                    }
                                    bottleDeposited(player, bottle, ra, sideEffect1, sideEffect2);
                                    return;
                                }
                            }
                            // Nearly full
                            if (cauldron.getData() == (byte) 2) {
                                event.setCancelled(true);
                                if (ra.getTeamBlue().contains(player)) {
                                    player.sendMessage(prefix + "That is your team's cauldron!");
                                    return;
                                }
                                if (ra.getTeamRed().contains(player)) {
                                    cauldState.getData().setData((byte) 1);
                                    cauldState.update();
                                    for (Player player1 : ra.getPlayers()) {
                                        player1.sendMessage(prefix + "§c§l" + player.getName() + " §7emptied §b§lTeam Blue's§r§7 cauldron!");
                                        player1.sendMessage(prefix + "§b§lTeam Blue's§r§7 cauldron is now 1 level full.");
                                    }
                                    bottleDeposited(player, bottle, ra, sideEffect1, sideEffect2);
                                    return;
                                }
                            }
                            // Nearly empty
                            if (cauldron.getData() == (byte) 1) {
                                event.setCancelled(true);
                                if (ra.getTeamBlue().contains(player)) {
                                    player.sendMessage(prefix + "That is your team's cauldron!");
                                    return;
                                }
                                if (ra.getTeamRed().contains(player)) {
                                    cauldState.getData().setData((byte) 0);
                                    cauldState.update();
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
                            if (cauldron.getData() == (byte) 0) {
                                // This is not a possible value to get, seeing as the game would be over if the cauldron was empty
                                return;
                            }
                            return;
                        }
                        if (clickedBlock.equals(ra.getRedCauldron())) {
                            BlockState cauldState = clickedBlock.getState();
                            //System.out.println("Cauldron equals red cauldron!");
                            // Full
                            event.setCancelled(true); // I'm controlling the levels
                            if (clickedBlock.getData() == ((byte) 3)) {
                                if (ra.getTeamBlue().contains(player)) {
                                    cauldState.getData().setData((byte) 2);
                                    cauldState.update();
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
                            if (cauldron.getData() == (byte) 2) {
                                event.setCancelled(true);
                                if (ra.getTeamBlue().contains(player)) {
                                    cauldState.getData().setData((byte) 1);
                                    cauldState.update();
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
                            if (cauldron.getData() == (byte) 1) {
                                event.setCancelled(true);
                                if (ra.getTeamBlue().contains(player)) {
                                    cauldState.getData().setData((byte) 0);
                                    cauldState.update();
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
                            if (cauldron.getData() == (byte) 0) {
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
