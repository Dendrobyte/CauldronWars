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
public class Archer extends KitBase {

    public Archer(){
        super("Archer");
        int effectDuration = 999999;
        // Armor
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack leggings = new ItemStack(Material.AIR, 1); // Could change to chainmail if buff is needed
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        addArmorItem(helmet);
        addArmorItem(chestplate);
        addArmorItem(leggings);
        addArmorItem(boots);

        // Items
        ItemStack bowFire = new ItemStack(Material.BOW, 1);
        bowFire.addEnchantment(Enchantment.ARROW_FIRE, 1);
        bowFire.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bowFire.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
        ItemMeta bowFireMeta = bowFire.getItemMeta();
        bowFireMeta.setDisplayName("§c§lFlame Bow");
        bowFire.setItemMeta(bowFireMeta);
        ItemStack bowPunch = new ItemStack(Material.BOW, 1);
        bowPunch.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
        bowPunch.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bowPunch.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
        ItemMeta bowPunchMeta = bowPunch.getItemMeta();
        bowPunchMeta.setDisplayName("§6§lPunch Bow");
        bowPunch.setItemMeta(bowPunchMeta);
        ItemStack arrow = new ItemStack(Material.ARROW, 1);
        ItemStack apples = new ItemStack(Material.APPLE, 24);
        ItemStack godApples = new ItemStack(Material.GOLDEN_APPLE, 2, (short) 1);
        addItem(bowFire);
        addItem(bowPunch);
        addItem(arrow);
        addItem(apples);
        addItem(godApples);

        // Potion Effects
        PotionEffect jump = new PotionEffect(PotionEffectType.JUMP, effectDuration, 2);
        PotionEffect resist = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, effectDuration, 1);
        addPotionEffect(jump);
        addPotionEffect(resist);
    }
}
