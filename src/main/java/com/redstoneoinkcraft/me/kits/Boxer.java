package com.redstoneoinkcraft.me.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class Boxer extends KitBase {

    public Boxer() {
        super("Boxer");
        int effectDuration = 999999;
        // Armor
        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        addArmorItem(helmet);
        addArmorItem(chestplate);
        addArmorItem(leggings);
        addArmorItem(boots);

        // Items
        ItemStack godApples = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
        ItemStack porkchops = new ItemStack(Material.GRILLED_PORK, 16);
        ItemStack regenPotion = getPotionItemStack(PotionType.SPEED, 1, false, false, 1, "§c§lBoxer's Adrenaline");
        addItem(godApples);
        addItem(porkchops);
        addItem(regenPotion);

        // Potion Effects
        PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, effectDuration, 1);
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, effectDuration, 1);
        addPotionEffect(strength);
        addPotionEffect(speed);

    }

}
