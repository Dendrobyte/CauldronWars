package com.redstoneoinkcraft.me.arenas;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class Arena { // This is for arena creation and blah. Screwed along the way but it works so therefore RunningArena for creating arena instances for a game.

    // Your generic arena setup...
    private int id;
    private Location spawn;
    private String name;
    private ArrayList<UUID> players = new ArrayList<UUID>();

    // Game specific things
    private Location blueSpawn;
    private Location redSpawn;
    private Location blueCauldron;
    private Location redCauldron;
    private Location lobby;
    private Location bounds1; // First point
    private Location bounds2; // Second point

    public Arena(Location spawn, int id, String name){
        this.spawn = spawn;
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<UUID> getPlayers(){
        return this.players;
    }

    public Location getSpawn(){
        return this.spawn;
    }

    public Location getBlueSpawn(){
        return this.blueSpawn;
    }

    public Location getRedSpawn(){
        return this.redSpawn;
    }

    public Location getBlueCauldron(){
        return this.blueCauldron;
    }

    public Location getRedCauldron(){
        return this.redCauldron;
    }

    public Location getLobby(){
        return this.lobby;
    }

    public Location getBounds1(){
        return this.bounds1;
    }

    public Location getBounds2(){
        return this.bounds2;
    }

}
