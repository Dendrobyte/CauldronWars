package com.redstoneoinkcraft.me;

import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.commands.ArenaCreateCommand;
import com.redstoneoinkcraft.me.commands.CWarsMainCommand;
import com.redstoneoinkcraft.me.commands.ShoutCommand;
import com.redstoneoinkcraft.me.listeners.*;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class Main extends JavaPlugin {

    private static Main instance;

    private String prefix = "§8[§bCauldron §cWars§8]§7 ";

    @Override
    public void onEnable(){
        this.instance = this;

        // Register Events
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BottleSpawnListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CauldronLevelListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CauldronInteractListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerHitListener(), this); // Contains special item code as well
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SignClickListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SignClickKitSelection(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SignCreateListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ArenaPointSettingListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlaceAndBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemDropListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PotionPickupListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BottleDespawnListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PreventCommandsListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerTeleportOutOfWorldEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PreventItemFrameDestroy(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemInteractListeners(), this); // For special items in kits
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this); // For selecting kit items in the selection GUI

        // Register Commands
        getCommand("cauldronwars").setExecutor(new CWarsMainCommand());
        getCommand("cwarsarena").setExecutor(new ArenaCreateCommand());
        getCommand("cwshout").setExecutor(new    ShoutCommand());

        createConfig(); // Create config... duh
        //saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        getLogger().log(Level.INFO, "[Cauldron Wars] Version: " + this.getDescription().getVersion() + " has successfully been initialized.");
    }

    @Override
    public void onDisable(){
        RunningArenaManager ram = RunningArenaManager.getManager();
        for(int i = 1; i < ram.getArenaJoinSigns().size(); i++){
            Sign sign = ram.getArenaJoinSigns().get(i);
            sign.setLine(0, "§8[§bCauldron§cWars§8]");
            sign.setLine(1, "join");
            sign.setLine(2, "-1");
            sign.setLine(3, "NULL");
            sign.update();
        }
        for(RunningArena ra : ram.getRunningArenas()){
            for(Player player : ra.getPlayers()){
                ra.setGameState(RunningArena.GameState.OVER);
                ram.removePlayer(player);
            }

        }

        saveConfig();

        getLogger().log(Level.INFO, "[Cauldron Wars] Version: " + this.getDescription().getVersion() + " has successfully been disabled.");
    }

    public void createConfig(){
        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }
        File file = new File(getDataFolder(), "config.yml");
        if(!file.exists()){
            getLogger().info("[Cauldron Wars] Config.yml not found, creating now...");
            saveDefaultConfig();
            getLogger().info("[Cauldron Wars] Config.yml successfully created!");
        } else {
            getLogger().info("[Cauldron Wars] Config.yml found for Cauldron Wars, loading now...");
        }
    }

    public static Main getInstance(){
        return instance;
    }

    public String getPrefix(){
        return prefix;
    }

    public static void sendActionChatPacket(Player player, String text) {
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer
                .a("{\"text\":\"" + text + " \"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(comp);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public static ItemStack addGlow(ItemStack item){
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null) tag = nmsStack.getTag();
        NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }

}
