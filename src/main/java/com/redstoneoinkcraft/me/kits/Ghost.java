package com.redstoneoinkcraft.me.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.List;

/**
 * Created by Mark on 3/14/2017.
 * Written for project CauldronWars
 * Please do not use or edit this code unless permissions has been given.
 * If you would like to use this code for modification and/or editing, do so with giving original credit.
 * Contact me on Twitter, @Mobkinz78
 * §§§§§§§§§§§§§§§
 */
public class Ghost extends KitBase {

    public Ghost(){
        super("Ghost");
        int effectDuration = 999999;
        // Armor
        ItemStack helmet = null;
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        chestplate.addEnchantment(Enchantment.THORNS, 2);
        ItemStack leggings = null;
        ItemStack boots = null;
        addArmorItem(helmet);
        addArmorItem(chestplate);
        addArmorItem(leggings);
        addArmorItem(boots);

        // Items
        ItemStack flameCharge = new ItemStack(Material.FIREBALL, 1);
            ItemMeta flameChargeMeta = flameCharge.getItemMeta();
            flameChargeMeta.setDisplayName("§c§lGhost Ember");
            /* List<String> flameChargeLore = flameChargeMeta.getLore();
            flameChargeLore.add("Burn your opponents!");
            flameChargeLore.add("Fire Aspect 1 | Sharpness 2");
            flameChargeMeta.setLore(flameChargeLore);  */
            flameCharge.setItemMeta(flameChargeMeta);
        flameCharge.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
        flameCharge.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemStack speedPotion = getPotionItemStack(PotionType.SPEED, 3, false, false, 1, "§8§lGhost Phase");
        ItemStack potatos = new ItemStack(Material.BAKED_POTATO, 16);
        addItem(flameCharge);
        addItem(speedPotion);
        addItem(potatos);

        // Potion Effects
        PotionEffect resist = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, effectDuration, 1);
        PotionEffect fireResist = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, effectDuration, 1);
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, effectDuration, 1);
        PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, effectDuration, 1);
        addPotionEffect(resist);
        addPotionEffect(fireResist);
        addPotionEffect(speed);
        addPotionEffect(invis);
    }

}
