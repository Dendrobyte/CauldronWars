package com.redstoneoinkcraft.me.arenas;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.kits.KitBase;
import com.redstoneoinkcraft.me.timers.GameStartTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Mark on 3/21/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class RunningArena {

    public enum GameState {
        WAITING, STARTING, IN_PROGRESS, ENDING, OVER
    }

    FileConfiguration config = Main.getInstance().getConfig();
    RunningArenaManager ram = RunningArenaManager.getManager();

    // Each arena will have....
    private int id;
    private String name;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> teamBlue = new ArrayList<>();
    private ArrayList<Player> teamRed = new ArrayList<>();
    private HashMap<Player, KitBase> playerKits = new HashMap<>();
    private Location blueSpawn;
    private Location redSpawn;
    private Block blueCauldron;
    private Block redCauldron;
    private Location lobby;
    private Location bounds1; // First point
    private Location bounds2; // Second point
    private GameState gameState;
    private GameStartTimer gst;
    boolean winningTeam = true;

    public RunningArena(String arenaName, int id){
        this.name = arenaName;
        this.id = id;
        World world = Bukkit.getServer().getWorld(Main.getInstance().getConfig().getString("arenas." + arenaName + ".world"));
        double lobbyX = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".lobby.x");
        double lobbyY = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".lobby.y") + 2;
        double lobbyZ = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".lobby.z");
        Location lobby = new Location(world, lobbyX, lobbyY, lobbyZ);
        this.lobby = lobby;
        double blueSpawnX = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".spawnpoints.teamBlue.x");
        double blueSpawnY = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".spawnpoints.teamBlue.y") + 2;
        double blueSpawnZ = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".spawnpoints.teamBlue.z");
        Location blueSpawn = new Location(world, blueSpawnX, blueSpawnY, blueSpawnZ);
        this.blueSpawn = blueSpawn;
        double redSpawnX = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".spawnpoints.teamRed.x");
        double redSpawnY = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".spawnpoints.teamRed.y") + 2;
        double redSpawnZ = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".spawnpoints.teamRed.z");
        Location redSpawn = new Location(world, redSpawnX, redSpawnY, redSpawnZ);
        this.redSpawn = redSpawn;
        double blueCauldX = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".cauldrons.teamBlue.x");
        double blueCauldY = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".cauldrons.teamBlue.y");
        double blueCauldZ = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".cauldrons.teamBlue.z");
        Location blueCauld = new Location(world, blueCauldX, blueCauldY, blueCauldZ);
        this.blueCauldron = blueCauld.getBlock();
        double redCauldX = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".cauldrons.teamRed.x");
        double redCauldY = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".cauldrons.teamRed.y");
        double redCauldZ = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".cauldrons.teamRed.z");
        Location redCauld = new Location(world, redCauldX, redCauldY, redCauldZ);
        this.redCauldron = redCauld.getBlock();
        double firstBoundX = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".bounds.firstbound.x");
        double firstBoundY = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".bounds.firstbound.y");
        double firstBoundZ = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".bounds.firstbound.z");
        Location firstBound = new Location(world, firstBoundX, firstBoundY, firstBoundZ);
        this.bounds1 = firstBound;
        double secondBoundX = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".bounds.secondbound.x");
        double secondBoundY = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".bounds.secondbound.y");
        double secondBoundZ = Main.getInstance().getConfig().getDouble("arenas." + arenaName + ".bounds.secondbound.z");
        Location secondBound = new Location(world, secondBoundX, secondBoundY, secondBoundZ);
        this.bounds2 = secondBound;
        this.setGameState(GameState.WAITING);
        this.playerKits = new HashMap<>();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public ArrayList<Player> getTeamBlue() {
        return this.teamBlue;
    }

    public ArrayList<Player> getTeamRed() {
        return this.teamRed;
    }

    public Location getBlueSpawn() {
        return this.blueSpawn;
    }

    public Location getRedSpawn() {
        return this.redSpawn;
    }

    public Block getBlueCauldron() {
        return this.blueCauldron;
    }

    public Block getRedCauldron() {
        return this.redCauldron;
    }

    public Location getLobby() {
        return this.lobby;
    }

    public Location getBounds1() {
        return this.bounds1;
    }

    public Location getBounds2() {
        return this.bounds2;
    }

    public GameState getGameState(){
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public HashMap<Player, KitBase> getPlayerKits(){
        return this.playerKits;
    }

    public boolean setWinningTeam(Boolean bool){ // True = blue, false = red
        this.winningTeam = bool;
        return this.winningTeam;
    }

    public GameStartTimer getGst(){
        return this.gst;
    }

    public void setGst(GameStartTimer gst){
        this.gst = gst;
    }
}
