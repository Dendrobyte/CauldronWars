package com.redstoneoinkcraft.me.arenas;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.timers.GameEndTimer;
import com.redstoneoinkcraft.me.timers.GameStartTimer;
import com.redstoneoinkcraft.me.timers.GatesOpenTimer;
import com.redstoneoinkcraft.me.timers.ItemAboveHeadSpawner;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Cauldron;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Mark on 3/21/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class RunningArenaManager {

    private ArrayList<RunningArena> runningArenas = new ArrayList<>();
    private int runningArenasSize = 0;
    ItemAboveHeadSpawner iahs;
    private HashMap<Integer, Sign> arenaJoinSigns = new HashMap<>();

    private static RunningArenaManager ram;
    String prefix = Main.getInstance().getPrefix();

    private RunningArenaManager() {}

    public static RunningArenaManager getManager(){
        if(ram == null){
            ram = new RunningArenaManager();
        }

        return ram;
    }

    public RunningArena getRunningArena(int i){
        for (RunningArena ra : this.runningArenas){
            if(ra.getId() == i){
                return ra;
            }
        }
        return null; // If it is null, the arena does not exist!
    }

    public void addPlayer(Player player, int id){
        RunningArena ra = this.getRunningArena(id);
        if(ra == null){
            player.sendMessage(prefix + "§cSorry, that arena could not be found!");
            return;
        }

        ra.getPlayers().add(player);
        Location lobby = ra.getLobby();

        int playerAmount = ra.getPlayers().size();
        ram.getArenaJoinSigns().get(ra.getId()).setLine(3, (ra.getPlayers().size()) + " Players");
        ram.getArenaJoinSigns().get(ra.getId()).update();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setSaturation(20);
        for(PotionEffect potionEffect : player.getActivePotionEffects()){
            player.removePotionEffect(potionEffect.getType());
        }
        if(playerAmount % 2 == 0){ // If the amount of players is even...
            ra.getTeamRed().add(player);
            player.sendTitle(prefix, "§cYou have been added to the red team!", 40, 80, 40);
            ItemStack redWool = new ItemStack(Material.RED_WOOL, 1);
            player.getInventory().setHelmet(redWool);
            for(Player player1 : ra.getPlayers()){
                if(player.equals(player1)){

                } else {
                    player1.sendMessage(prefix + player.getName() + " has joined the §c§lred team!");
                }
            }
        }
        if(playerAmount % 2  != 0){ // If the amount of players is odd...
            ra.getTeamBlue().add(player);
            player.sendTitle(prefix, "§bYou have been added to the blue team!", 40, 80, 40);
            ItemStack blueWool = new ItemStack(Material.RED_WOOL, 1);
            player.getInventory().setHelmet(blueWool);
            for(Player player1 : ra.getPlayers()){
                if(player.equals(player1)){

                } else {
                    player1.sendMessage(prefix + player.getName() + " has joined the §b§lblue team!");
                }
            }
        }
        if(!ra.getPlayerKits().containsKey(player)){
            ra.getPlayerKits().put(player, null);
        }
        player.teleport(lobby);
            for(String string : ram.mapInfo(ra.getName())){
                player.sendMessage(string);
            }
        player.sendMessage(prefix + "If bottles spawn unreachably, they respawn.");
        player.sendMessage(prefix + "Teammates have colored wool above their heads.");
        if(playerAmount >= 6){
            if(ram.getRunningArena(id).getGameState().equals(RunningArena.GameState.STARTING)){
                return;
            }
            ram.start(id);
            for(Player player1 : ra.getPlayers()){
                player1.sendMessage(prefix + "§dEnough people have joined, the game will begin shortly!");
                // Countdown started in the start() method
            }
        }
        return;
    }

    public void removePlayer(Player player){
        RunningArena ra;
        if(isInGame(player) == null){
            player.sendMessage(prefix + "§cPlayer does not exist, or player is not in an arena!");
            return;
        }
        else {
            ra = isInGame(player);
        }
        ram.getArenaJoinSigns().get(ra.getId()).setLine(3, (ra.getPlayers().size() - 1) + " Players");
        ram.getArenaJoinSigns().get(ra.getId()).update();

        if(!ra.getGameState().equals(RunningArena.GameState.ENDING)){
            ra.getPlayers().remove(player);
        }
        if(ra.getTeamBlue().contains(player)){
            ra.getTeamBlue().remove(player);
        }
        else if(ra.getTeamRed().contains(player)){
            ra.getTeamRed().remove(player);
        }

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20);
        player.setSaturation(20);
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        if(!ra.getPlayerKits().containsKey(player)){
            player.sendMessage(prefix + "Please contact an administrator. Something went wrong with removing your kit.");
        }
        if(!ra.getGameState().equals(RunningArena.GameState.OVER)) {
            ra.getPlayerKits().remove(player);
        }

        if(ra.getPlayers().size() > 0 && ra.getGameState().equals(RunningArena.GameState.WAITING)) {
            if (ra.getTeamBlue().contains(player)) {
                Player lastPlayer = ra.getPlayers().get(ra.getPlayers().size() - 1);
                if (ra.getTeamBlue().contains(lastPlayer)) {
                    // Do nothing
                }
                if (ra.getTeamRed().contains(lastPlayer)) {
                    ra.getTeamRed().remove(lastPlayer);
                    ra.getTeamBlue().add(lastPlayer);
                    lastPlayer.sendMessage(prefix + "You have been moved to balance teams.");
                    player.sendTitle(prefix, "§bYou have been added to the blue team!", 40, 80, 40);
                    ItemStack blueWool = new ItemStack(Material.BLUE_WOOL, 1);
                    player.getInventory().setHelmet(blueWool);
                }
            }
            if (ra.getTeamRed().contains(player)) {
                Player lastPlayer = ra.getPlayers().get(ra.getPlayers().size() - 1);
                if (ra.getTeamRed().contains(lastPlayer)) {
                    // Do nothing
                }
                if (ra.getTeamBlue().contains(lastPlayer)) {
                    ra.getTeamBlue().remove(lastPlayer);
                    ra.getTeamRed().add(lastPlayer);
                    lastPlayer.sendMessage(prefix + "You have been moved to balance teams.");
                    player.sendTitle(prefix, "§cYou have been added to the red team!", 40, 80, 40);
                    ItemStack redWool = new ItemStack(Material.RED_WOOL, 1);
                    player.getInventory().setHelmet(redWool);
                }
            }
        }
        int playerAmount = ra.getPlayers().size();
        if(playerAmount < 6){
            if(ra.getGameState().equals(RunningArena.GameState.STARTING)){
                ra.setGameState(RunningArena.GameState.WAITING);
                for(Player player1 : ra.getPlayers()){
                    player1.sendMessage(prefix + "A player has left your arena! The game has gone back to waiting mode.");
                }
                ra.getGst().cancel();
                ra.setGst(null);
                ram.getArenaJoinSigns().get(ra.getId()).setLine(3, ra.getPlayers().size() + " Players");
                ram.getArenaJoinSigns().get(ra.getId()).update();
            }
            if(ra.getGameState().equals(RunningArena.GameState.IN_PROGRESS)){
                for(Player player1 : ra.getPlayers()){
                    player1.sendMessage(prefix + player.getName() + " has left the game!");
                    if(playerAmount > 2) {
                        player1.sendMessage(prefix + "Someone has left your arena, but the game will continue!");
                    }
                }
                if(playerAmount < 2){
                    for(Player player1 : ra.getPlayers()){
                        player1.sendMessage(prefix + "There are too few players to continue the game.");
                        if(ra.getTeamBlue().contains(player1)){
                            ra.setWinningTeam(true);
                        }
                        else {
                            ra.setWinningTeam(false);
                        }
                    }
                    end(ra.getId());
                    return;
                }
                if(ra.getTeamBlue().size() == 0){
                    for(Player player1 : ra.getPlayers()){
                        player1.sendMessage(prefix + "No one is left on the blue team. The game will now end.");
                    }
                    ra.setWinningTeam(false);
                    end(ra.getId());
                }
                if(ra.getTeamRed().size() == 0){
                    for(Player player1 : ra.getPlayers()){
                        player1.sendMessage(prefix + "No one is left on the red team. The game will now end.");
                    }
                    ra.setWinningTeam(true);
                    end(ra.getId());
                }
            }

        }
    }

    public RunningArena isInGame(Player player){
        for(RunningArena runningArena : runningArenas){
            if(runningArena.getPlayers().contains(player)){
                return runningArena; // True if in game...
            }
        }
        return null; // False if they're not
    }

    public void create(String arenaName){
        if(!Main.getInstance().getConfig().getStringList("list-of-arenas").contains(arenaName)){
            Bukkit.getLogger().log(Level.WARNING, prefix + "The arena " + arenaName + " could not be started.");
            return;
        }
        Main.getInstance().getConfig().get("arenas." + arenaName);
        runningArenasSize++;
        RunningArena newRunningArena = new RunningArena(arenaName, runningArenasSize);
        runningArenas.add(newRunningArena);
        return;
    }

    @SuppressWarnings("deprecation")
    public void start(int arenaID){ // Can also just pass in a running arena (for this and end)...
        for(RunningArena ra : ram.getRunningArenas()){
            if(ra.getId() == arenaID){
                ra.setGameState(RunningArena.GameState.STARTING);

                // Change sign
                ram.getArenaJoinSigns().get(ra.getId()).setLine(3, "In Progress");
                ram.getArenaJoinSigns().get(ra.getId()).update();

                // Cauldron initiation! 3 == full, 0 == empty
                /* Cauldron blueCauldron = (Cauldron) ra.getBlueCauldron().getState().getData();
                 *Cauldron redCauldron = (Cauldron) ra.getRedCauldron().getState().getData();
                 */

                Block blueCauldron = ra.getBlueCauldron();
                Levelled blueCauldronData = (Levelled) blueCauldron.getBlockData();
                blueCauldronData.setLevel(blueCauldronData.getMaximumLevel());
                blueCauldron.setBlockData(blueCauldronData);

                Block redCauldron = ra.getRedCauldron();
                Levelled redCauldronData = (Levelled) redCauldron.getBlockData();
                redCauldronData.setLevel(blueCauldronData.getMaximumLevel());
                redCauldron.setBlockData(redCauldronData);

                GameStartTimer gst = new GameStartTimer();
                ra.setGst(gst);
                ra.getGst().setRa(ra);
                ra.getGst().runTaskTimer(Main.getInstance(), 0, 20);

                this.iahs = new ItemAboveHeadSpawner(ra);
                this.iahs.runTaskTimer(Main.getInstance(), 0, 5);
            }
            else{
                Bukkit.getLogger().log(Level.WARNING, prefix + "An arena, with ID " + arenaID + ", could not be started.");
            }
        }
    }

    public void end(int arenaID){
        RunningArena ra = ram.getRunningArena(arenaID);
        this.iahs.cancel();
            if(ra.winningTeam){ // Blue won
                for(Player player : Bukkit.getServer().getOnlinePlayers()){
                    player.sendMessage(prefix + "Congratulations to §b§lTeam Blue§r§7 for winning a round of Cauldron Wars!");
                }
            }
            if(!ra.winningTeam){ // Red won
                for(Player player : Bukkit.getServer().getOnlinePlayers()){
                    player.sendMessage(prefix + "Congratulations to §c§lTeam Red§r§7 for winning a round of Cauldron Wars!");
                }
            }
            for(Player player : ra.getPlayers()){
                player.sendMessage(prefix + "You will be returned to spawn in 5 seconds.");
            }
        ra.setGameState(RunningArena.GameState.ENDING);
        GameEndTimer get = new GameEndTimer(ra);
        get.runTaskTimer(Main.getInstance(), 0, 20L);

        // Clear all fallen items, specifically bottles
        World world = ra.getBounds1().getWorld();
        List<Entity> entList = world.getEntities();
        for(Entity current : entList) {
            if (current instanceof Item) { // Don't delete any mobs or players! :P
                current.remove();
            }
        }

        ram.getArenaJoinSigns().get(ra.getId()).setLine(2, "-1");
        ram.getArenaJoinSigns().get(ra.getId()).setLine(3, "Start New Game Above");
        ram.getArenaJoinSigns().get(ra.getId()).update();
    }

    public ArrayList<String> mapInfo(String arenaName){
        ArrayList<String> mapDesc = new ArrayList<>();
        mapDesc.add(0, "§d§l-+Welcome to Cauldron Wars-+");
        mapDesc.add(1, "§3§oIdea by §b§o@AerZork");
        mapDesc.add(2, "§7-+§bMap §cInformation§7+-");
        mapDesc.add(3, "Improperly configured");
        mapDesc.add(4, "Improperly configured");
        mapDesc.add(5, "§7§lWondering How to Play? Use §a/cwars help§7 for more info");
        if(!Main.getInstance().getConfig().getStringList("list-of-arenas").contains(arenaName)){
            //player.sendMessage(prefix + "§cSorry, the arena §c§l'" + args[1] + "'§r§c does not exist!");
            return null;
        }
        if(Main.getInstance().getConfig().getStringList("list-of-arenas").contains(arenaName)){

            if(arenaName.contains("rc_skyisland")){
                mapDesc.set(3, "§7§oMap Name: §r§cSky Island House");
                mapDesc.set(4, "§7§oMap Author: §r§cRedCloud_Ninja");
                return mapDesc;
            }
            if(arenaName.contains("rc_jungle")){
                mapDesc.set(3, "§7§oMap Name: §r§cMelon Jungle");
                mapDesc.set(4, "§7§oMap Author: §r§cRedCloud_Ninja");
                return mapDesc;
            }
            if(arenaName.contains("mgxc_bridge")){
                mapDesc.set(3, "§7§oMap Name: §r§cUnder the Bridge");
                mapDesc.set(4, "§7§oMap Authors: §r§cthat_melon_gamer");
                return mapDesc;
            }
            if(arenaName.contains("smax_imbalance")){
                mapDesc.set(3, "§7§oMap Name: §r§cImbalance");
                mapDesc.set(4, "§7§oMap Author: §r§cstarmax1000");
                return mapDesc;
            }
            if(arenaName.contains("za_weather")){
                mapDesc.set(3, "§7§oMap Name: §r§cWeather Station");
                mapDesc.set(4, "§7§oMap Author: §r§cZaphoo");
                return mapDesc;
            }
            if(arenaName.contains("testmap") || arenaName.contains("testing")){
                mapDesc.set(3, "Test map!");
                mapDesc.set(4, "Test map!");
                return mapDesc;
            }
        }
            return mapDesc;
    }

    public int getRunningArenasSize(){
        return this.runningArenasSize;
    }

    public ArrayList<RunningArena> getRunningArenas(){ return this.runningArenas; }

    public HashMap<Integer, Sign> getArenaJoinSigns() { return this.arenaJoinSigns; }

}
