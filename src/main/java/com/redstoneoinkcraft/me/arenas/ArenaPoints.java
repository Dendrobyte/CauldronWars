package com.redstoneoinkcraft.me.arenas;

import org.bukkit.Location;

/**
 * Created by Mark on 3/20/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class ArenaPoints {

    private String name;
    private Location lobby;
    private Location blueSpawn;
    private Location redSpawn;
    private Location blueCauldron;
    private Location redCauldron;

    public ArenaPoints(Location lobby, Location blueSpawn, Location blueCauldron, Location redSpawn, Location redCauldron, Location bounds1, Location bounds2) {
        this.lobby = lobby;
        this.blueSpawn = blueSpawn;
        this.blueCauldron = blueCauldron;
        this.redSpawn = redSpawn;
        this.redCauldron = redCauldron;
        this.bounds1 = bounds1;
        this.bounds2 = bounds2;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBlueSpawn(Location blueSpawn) {
        this.blueSpawn = blueSpawn;
    }

    public void setBlueCauldron(Location blueCauldron) {
        this.blueCauldron = blueCauldron;
    }

    public void setRedSpawn(Location redSpawn) {
        this.redSpawn = redSpawn;
    }

    public void setRedCauldron(Location redCauldron) {
        this.redCauldron = redCauldron;
    }

    public void setBounds1(Location bounds1) {
        this.bounds1 = bounds1;
    }

    public void setBounds2(Location bounds2) {
        this.bounds2 = bounds2;
    }

    public Location getLobby() {
        return lobby;
    }

    public Location getBlueSpawn() {
        return blueSpawn;
    }

    public Location getBlueCauldron() {
        return blueCauldron;
    }

    public Location getRedSpawn() {
        return redSpawn;
    }

    public Location getRedCauldron() {
        return redCauldron;
    }

    public Location getBounds1() {
        return bounds1;
    }

    public Location getBounds2() {
        return bounds2;
    }

    private Location bounds1;
    private Location bounds2;

}
