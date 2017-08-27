package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * Created by Mark on 8/16/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class ItemInteractListeners implements Listener {

    RunningArenaManager ram = RunningArenaManager.getManager();
    RunningArena ra;

    @EventHandler
    private void onItemRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(ram.isInGame(player) == null){
            return;
        }
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            return;
        }

        // Begin the checks
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // FlyingPig Jump
        if(itemInHand.getType().equals(Material.FEATHER) && itemInHand.getItemMeta().getDisplayName().equals("§c§lCooling down...")){
            player.sendMessage(Main.getInstance().getPrefix() + "§cYou're still on cooldown from using this item!");
        }
        if(itemInHand.getType().equals(Material.FEATHER) && itemInHand.getItemMeta().getDisplayName().equals("§9§lPig's Wings")){
            player.setVelocity(new Vector(0, .9, 0));
            player.getInventory().remove(Material.FEATHER);
            ItemStack tempFeather = new ItemStack(Material.FEATHER, 1);
            ItemMeta tempMeta = tempFeather.getItemMeta();
            tempMeta.setDisplayName("§c§lCooling down...");
            tempFeather.setItemMeta(tempMeta);
            player.getInventory().addItem(tempFeather);
            FlyingPigFeatherTimer fpft = new FlyingPigFeatherTimer(3, player);
            fpft.runTaskTimer(Main.getInstance(), 0, 20);
            return;
        } else {
            return;
        }
    }

}

class FlyingPigFeatherTimer extends BukkitRunnable {
    Player player;
    int counter;
    ItemStack tempFeather;
    ItemStack permFeather;


    public FlyingPigFeatherTimer(int seconds, Player player){
        this.player = player;
        this.counter = seconds;
        ItemStack tempFeather = new ItemStack(Material.FEATHER, 1);
        ItemMeta tempMeta = tempFeather.getItemMeta();
        tempMeta.setDisplayName("§c§lCooling down...");
        tempFeather.setItemMeta(tempMeta);
        this.tempFeather = tempFeather;

        ItemStack feather = new ItemStack(Material.FEATHER, 1); // Will be named something and give them extra jump powers on right click (or something)
        ItemMeta featherMeta = feather.getItemMeta();
        featherMeta.setDisplayName("§9§lPig's Wings");
        ArrayList<String> featherLore = new ArrayList<>();
        featherLore.add("The Flying Pig's flight source!");
        featherLore.add("Right click to super jump! (Main hand)");
        featherLore.add("§a§lCooldown: 3 Seconds");
        featherMeta.setLore(featherLore);
        feather.setItemMeta(featherMeta);
        this.permFeather = feather;
    }

    @Override
    public void run(){
        if(this.counter == 0) {
            if(player.getInventory().contains(tempFeather)){
                player.getInventory().remove(tempFeather);
                player.getInventory().addItem(permFeather);
            }
            this.cancel();
        }
        this.counter--;
    }
}
