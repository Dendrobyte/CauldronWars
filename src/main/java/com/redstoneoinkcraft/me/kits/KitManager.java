package com.redstoneoinkcraft.me.kits;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArena;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

/**
 * Created by Mark on 4/18/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class KitManager {

    public KitManager() {}
    private static KitManager km;
    RunningArenaManager ram = RunningArenaManager.getManager();
    private ArrayList<KitBase> currentKits = new ArrayList<>();

    public static KitManager getManager(){
        if(km == null){
            km = new KitManager();
        }

        return km;
    }


    public void addKit(KitBase kit, Player player){
        RunningArena ra = ram.isInGame(player);
        if(ra == null){
            player.sendMessage(Main.getInstance().getPrefix() + "You need to be in a game to select a kit! What sorcery is this?!?");
            return;
        }

        if(!ra.getPlayerKits().containsKey(player)){
            //player.sendMessage(Main.getInstance().getPrefix() + "There seems to have been an issue with your kit... please contact an administrator.");
            ra.getPlayerKits().put(player, kit);
        } else if (ra.getPlayerKits().containsKey(player)){
            ra.getPlayerKits().replace(player, kit);
        }

        // Add the armor - MUST go Helmet > Chestplate > Leggings > Boots
        ItemStack helmet = kit.getArmorItems().get(0);
        ItemStack chestplate = kit.getArmorItems().get(1);
        ItemStack leggings = kit.getArmorItems().get(2);
        ItemStack boots = kit.getArmorItems().get(3);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        // Give every player particles

        // Add in the wool that is their team color.
        if(ra.getTeamBlue().contains(player)){
            ItemStack blueWool = new ItemStack(Material.WOOL, 1, (byte)11);
            ItemMeta blueWoolMeta = blueWool.getItemMeta();
            blueWoolMeta.setDisplayName("§b§lYou are on the blue team!");
            blueWool.setItemMeta(blueWoolMeta);
            player.getInventory().setItem(8, blueWool);
        }
        if(ra.getTeamRed().contains(player)){
            ItemStack redWool = new ItemStack(Material.WOOL, 1, (byte)14);
            ItemMeta redWoolMeta = redWool.getItemMeta();
            redWoolMeta.setDisplayName("§c§lYou are on the red team!");
            redWool.setItemMeta(redWoolMeta);
            player.getInventory().setItem(8, redWool);
        }

        // Add items to inventory
        for(ItemStack item : kit.getItems()){ // Give items
            player.getInventory().addItem(item);
        }

        // Add potion effects
        for(PotionEffect effect : kit.getPotionEffects()){
            player.addPotionEffect(effect);
        }

        player.sendMessage(Main.getInstance().getPrefix() + "You have been assigned the " + kit.getName() + " kit.");
        // Debug: System.out.println("PlayerKits HashMap: " + ra.getPlayerKits().toString());
    }

    public void removeKit(Player player){ // Every time a player chooses a kit, remove a kit and set it again.
        RunningArena inArena = ram.isInGame(player);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public KitBase getKitByName(String kitName){
        KitBase kit = null;
        if(kitName.equalsIgnoreCase("Gladiator")){
            kit = new Gladiator();
            return kit;
        }
        if(kitName.equalsIgnoreCase("Archer")){
            kit = new Archer();
            return kit;
        }
        if(kitName.equalsIgnoreCase("Flying_Pig")){
            kit = new FlyingPig();
            return kit;
        }
        if(kitName.equalsIgnoreCase("Boxer")){
            kit = new Boxer();
            return kit;
        }
        if(kitName.equalsIgnoreCase("Ghost")){
            kit = new Ghost();
            return kit;
        }
        if(kitName.equalsIgnoreCase("Monk")){
            kit = new Monk();
            return kit;
        }
        if(kitName.equalsIgnoreCase("Mummy")){
            kit = new Mummy();
            return kit;
        }
        return kit;
    }
}
