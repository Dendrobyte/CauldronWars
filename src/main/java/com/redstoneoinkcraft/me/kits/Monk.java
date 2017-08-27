package com.redstoneoinkcraft.me.kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class Monk extends KitBase {

    public Monk(){
        super("Monk");
        int effectDuration = 999999;

        // Armor
        ItemStack helmet = null;
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        chestplate.setItemMeta(addLeatherMeta(chestplate, Color.fromRGB(173, 43, 43)));
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        ItemStack leggings = null;
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        boots.setItemMeta(addLeatherMeta(boots, Color.fromRGB(173, 43, 43)));
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        addArmorItem(helmet);
        addArmorItem(chestplate);
        addArmorItem(leggings);
        addArmorItem(boots);

        // Items
        ItemStack knockbackStick = new ItemStack(Material.STICK, 1);
        knockbackStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
        ItemStack healMelon = new ItemStack(Material.SPECKLED_MELON, 1);
        healMelon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        {
            ItemMeta melonMeta = healMelon.getItemMeta();
            melonMeta.setDisplayName("§6§lMonk's Healing Melon");
            ArrayList<String> melonLore = new ArrayList<>();
            melonLore.add("Hit a teammate to heal them!");
            melonLore.add("+1/2 a Heart");
            melonMeta.setLore(melonLore);
            healMelon.setItemMeta(melonMeta);
        }
        ItemStack healingPots = getPotionItemStack(PotionType.INSTANT_HEAL, 3, false, false, 5, "§c§lMonk's First Aid");
        ItemStack regenPots = getPotionItemStack(PotionType.REGEN, 2, false, false, 4, "§d§lMonk's Regen");
        ItemStack speedPots = getPotionItemStack(PotionType.SPEED, 2, false, false, 4, "§b§lMonk's Speed Force");
        ItemStack goldenApples = new ItemStack(Material.GOLDEN_APPLE, 2);
        addItem(knockbackStick);
        addItem(healMelon);
        addItem(healingPots);
        addItem(regenPots);
        addItem(speedPots);
        addItem(goldenApples);

        // Potion Effects
        PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, effectDuration, 2);
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, effectDuration, 2);
        PotionEffect resist = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, effectDuration, 1);
        addPotionEffect(regen);
        addPotionEffect(speed);
        addPotionEffect(resist);

    }

}
