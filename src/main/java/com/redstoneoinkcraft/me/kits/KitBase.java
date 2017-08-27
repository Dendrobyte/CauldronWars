package com.redstoneoinkcraft.me.kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
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
public abstract class KitBase {

    /*
     * The first four items in every kit must be armor!
     * 0 = Helmet
     * 1 = Chestplate
     * 2 = Leggings
     * 3 = Boots
     */

    private String name;
    private ArrayList<ItemStack> items = new ArrayList<>();
    private ArrayList<ItemStack> armorItems = new ArrayList<>();
    private ArrayList<PotionEffect> potionEffects = new ArrayList<>();

    public KitBase(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.armorItems = new ArrayList();
        this.potionEffects = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public ArrayList<ItemStack> getItems(){
        return items;
    }

    public ArrayList<ItemStack> getArmorItems() {
        return armorItems;
    }

    public ArrayList<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public KitBase getKit(){
        return this;
    }

    public void addItem(ItemStack itemStack){
        items.add(itemStack);
    }

    public void addArmorItem(ItemStack itemStack) {
        armorItems.add(itemStack);
    }

    public void addPotionEffect(PotionEffect potionEffect) {
        potionEffects.add(potionEffect);
    }

    public ItemStack getPotionItemStack(PotionType type, int level, boolean extend, boolean upgraded, int amount, String displayName){
        ItemStack potion = new ItemStack(Material.POTION, amount);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(type, extend, upgraded));
        potion.setItemMeta(meta);
        return potion;
    }

    public ItemMeta addLeatherMeta(ItemStack leatherArmor, Color color){
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherArmor.getItemMeta();
        leatherArmorMeta.setColor(color);
        return leatherArmorMeta;
    }

}
