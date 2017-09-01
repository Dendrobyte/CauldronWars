package com.redstoneoinkcraft.me.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
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
public class Gladiator extends KitBase {

    public Gladiator(){
        super("Gladiator");
        int effectDuration = 999999;
        // Armor
        ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        helmet.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        chestplate.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leggings.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boots.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2);
        addArmorItem(helmet);
        addArmorItem(chestplate);
        addArmorItem(leggings);
        addArmorItem(boots);

        // Items
        ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemStack speedPotion = getPotionItemStack(PotionType.SPEED, 1, false, false, 1, "§4§lGladiator's Buff");
        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 16);
        addItem(sword);
        addItem(speedPotion);
        addItem(steak);

        // Potion Effects
        PotionEffect fireResist = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, effectDuration, 1);
        addPotionEffect(fireResist);
    }

}
