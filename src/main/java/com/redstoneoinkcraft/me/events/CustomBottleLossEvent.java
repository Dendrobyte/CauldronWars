package com.redstoneoinkcraft.me.events;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.timers.BottleSpawnTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mark on 4/21/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class CustomBottleLossEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private ItemStack bottle;
    private Player player;
    RunningArena ra;

    public CustomBottleLossEvent(ItemStack bottle, Player player, RunningArena ra){
        this.bottle = bottle;
        this.player = player;
        this.ra = ra;
    }

    public void spawnNewBottle(RunningArena ra){
        BottleSpawnTimer bst = new BottleSpawnTimer(ra);
        bst.runTaskTimer(Main.getInstance(), 0, 20L);
    }

    public ItemStack getBottle() {
        return bottle;
    }

    public void setBottle(ItemStack bottle) {
        this.bottle = bottle;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public RunningArena getRa() {
        return ra;
    }

    public void setRa(RunningArena ra) {
        this.ra = ra;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
