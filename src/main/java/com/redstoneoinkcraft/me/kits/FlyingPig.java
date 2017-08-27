package com.redstoneoinkcraft.me.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class FlyingPig extends KitBase {

    public FlyingPig(){
        super("FlyingPig");
        int effectDuration = 999999;
        // Armor
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET, 1);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1); // Could change to chainmail if buff is needed
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
        addArmorItem(helmet);
        addArmorItem(chestplate);
        addArmorItem(leggings);
        addArmorItem(boots);

        // Items
        ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        sword.addEnchantment(Enchantment.FIRE_ASPECT, 1);
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE, 1);
        axe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemStack feather = new ItemStack(Material.FEATHER, 1); // Will be named something and give them extra jump powers on right click (or something)
        {
            ItemMeta featherMeta = feather.getItemMeta();
            featherMeta.setDisplayName("§9§lPig's Wings");
            ArrayList<String> featherLore = new ArrayList<>();
            featherLore.add("The Flying Pig's flight source!");
            featherLore.add("Right click to super jump! (Main hand)");
            featherLore.add("§a§lCooldown: 3 Seconds");
            featherMeta.setLore(featherLore);
            feather.setItemMeta(featherMeta);
        }
        ItemStack pie = new ItemStack(Material.PUMPKIN_PIE, 16);
        addItem(sword);
        addItem(axe);
        addItem(feather);
        addItem(pie);

        // Potion Effects
        PotionEffect jump = new PotionEffect(PotionEffectType.JUMP, effectDuration, 1);
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, effectDuration, 2);
        PotionEffect haste = new PotionEffect(PotionEffectType.FAST_DIGGING, effectDuration, 1);
        addPotionEffect(jump);
        addPotionEffect(speed);
        addPotionEffect(haste);
    }
}
