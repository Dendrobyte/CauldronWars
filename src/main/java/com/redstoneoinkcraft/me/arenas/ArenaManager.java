package com.redstoneoinkcraft.me.arenas;

import com.redstoneoinkcraft.me.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class ArenaManager { // Arena manager for creating arenas in the first place. Screwed up along the way and therefore created RunningArenaManager for games.

    private FileConfiguration config = Main.getInstance().getConfig();
    private String prefix = Main.getInstance().getPrefix();
    private ArenaManager() {} // No instantiation for you!
    private static ArenaManager arenaManager;

    private static HashMap<UUID, ArenaPoints> pointStorage = new HashMap<>(); // Player name, arena points
    public static HashMap<UUID, ArenaPoints> getPointStorage(){
        return pointStorage;
    }
    private ArrayList<Arena> arenas = new ArrayList<Arena>();
    private int arenaSize = 0;

    public static ArenaManager getManager() {
        if (arenaManager == null)
            arenaManager = new ArenaManager();

        return arenaManager;
    }

    public ArrayList<Arena> getArenas(){
       return this.arenas;
    }


    public void addArena(String name){ // I kind of want to have arenas by name... but we'll see.
        // Get number of arenas and add 1
        int arenaNumber = config.getInt("existing-arenas");
        arenaNumber++;
        config.set("existing-arenas", arenaNumber);

        List<String> arenaList = config.getStringList("list-of-arenas");
        arenaList.add(name);
        config.set("list-of-arenas", arenaList);

        return;
    }

}
