package com.redstoneoinkcraft.me.kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 4/17/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class Mummy extends KitBase {

    /*
    [9:16:10 PM] Sam Dessino: you could make a mummy kit
    [9:16:32 PM] Sam Dessino: has a chance to give others hunger when they are hit
    [9:16:50 PM] Sam Dessino: and can give the affect of slowing someone by tripping them in bandages (exception for person carrying potion)
    [9:19:23 PM] Merk (Mobkinz): hunger when they get hit? (y)
    [9:19:28 PM] Sam Dessino: yeah
    [9:19:36 PM] Sam Dessino: maybe 5 seconds of hunger 2
    [9:19:46 PM] Sam Dessino: 25% chance to get hunger
     */

    public Mummy(){
        super("Mummy");
        int effectDuration = 999999;

        // Armor
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        helmet.setItemMeta(addLeatherMeta(helmet, Color.fromRGB(255, 232, 187)));
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        chestplate.setItemMeta(addLeatherMeta(chestplate, Color.fromRGB(255, 232, 187)));
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        leggings.setItemMeta(addLeatherMeta(leggings, Color.fromRGB(255, 232, 187)));
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        boots.setItemMeta(addLeatherMeta(boots, Color.fromRGB(255, 232, 187)));
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        addArmorItem(helmet);
        addArmorItem(chestplate);
        addArmorItem(leggings);
        addArmorItem(boots);

        // Items
        ItemStack flesh = new ItemStack(Material.ROTTEN_FLESH, 1);
        flesh.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
        {
            ItemMeta fleshMeta = flesh.getItemMeta();
            fleshMeta.setDisplayName("§c§lSickening Flesh");
            ArrayList<String> fleshLore = new ArrayList<>();
            fleshLore.add("Strike the living with the remnants of the dead!");
            fleshLore.add("25% chance to inflict Hunger II for 5 seconds");
            fleshMeta.setLore(fleshLore);
            flesh.setItemMeta(fleshMeta);
        }
        ItemStack cobweb = new ItemStack(Material.COBWEB);
        cobweb.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        {
            ItemMeta webMeta = cobweb.getItemMeta();
            webMeta.setDisplayName("§7§lLeftover Mummy Wraps");
            ArrayList<String> webLore = new ArrayList<>();
            webLore.add("Wrap 'em up!");
            webLore.add("33% chance to spawn cobwebs around a player for 3 seconds");
            webLore.add("§4§lBe aware of your surroundings!");
            webLore.add("You get boosted into the OPPOSITE direction you are going in!");
            webMeta.setLore(webLore);
            cobweb.setItemMeta(webMeta);
        }
        addItem(flesh);
        addItem(cobweb);

        // Potion Effects
        PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, effectDuration, 1);
        PotionEffect absorption = new PotionEffect(PotionEffectType.ABSORPTION, effectDuration, 2);
        addPotionEffect(slowness);
        addPotionEffect(absorption);
    }

}
