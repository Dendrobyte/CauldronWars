package com.redstoneoinkcraft.me.utilities;

import com.redstoneoinkcraft.me.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Author - markbacon78
 * File created on 8/20/17
 * This project is property of markbacon78 and was created for single user purposes only.
 * Do not copy or redistribute this document (if you have somehow gotten access to the code...)
 * §
 */
public class KitSelectionInventory {

    ArrayList<ItemStack> kitSelectionStacks = new ArrayList<>();

    public KitSelectionInventory KitSelectionInventory(){
        return this;
    }

    public Inventory getKitInv() {
        Inventory inventory = Bukkit.createInventory(null, 18, Main.getInstance().getPrefix() + "Kit Selection");

        inventory.setItem(0, gladiator); // 0, 2, 4, 6, 8, 12, 14
        inventory.setItem(2, archer);
        inventory.setItem(4, monk);
        inventory.setItem(6, boxer);
        inventory.setItem(8, flyingpig);
        inventory.setItem(12, ghost);
        inventory.setItem(14, mummy);

        return inventory;
    }

    ItemStack gladiator = new ItemStack(Material.DIAMOND_HELMET, 1);
    {
        gladiator.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        ItemMeta gladiatorMeta = gladiator.getItemMeta();
        gladiatorMeta.setDisplayName("§9§lGladiator Kit");
        gladiator.setItemMeta(gladiatorMeta);
    }

    ItemStack archer = new ItemStack(Material.BOW, 1);
    {
        archer.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        ItemMeta archerMeta = archer.getItemMeta();
        archerMeta.setDisplayName("§2§lArcher Kit");
        archer.setItemMeta(archerMeta);
    }

    ItemStack monk = new ItemStack(Material.SPECKLED_MELON, 1);
    {
        monk.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemMeta monkMeta = monk.getItemMeta();
        monkMeta.setDisplayName("§6§lMonk Kit");
        monk.setItemMeta(monkMeta);
    }

    ItemStack boxer = new ItemStack(Material.STRING, 1);
    {
        boxer.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
        ItemMeta boxerMeta = boxer.getItemMeta();
        boxerMeta.setDisplayName("§c§lBoxer Kit");
        ArrayList<String> boxerLore = new ArrayList<>();
        boxerLore.add("It's string because it's the closest things to hand wraps I could find");
        boxerLore.add("And hand wraps were the closest thing I could think of for MC");
        boxerLore.add("I'm sorry, okay? =(");
        boxerMeta.setLore(boxerLore);
        boxer.setItemMeta(boxerMeta);
    }

    ItemStack flyingpig = new ItemStack(Material.FEATHER, 1);
    {
        flyingpig.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 4);
        ItemMeta flyingpigMeta = flyingpig.getItemMeta();
        flyingpigMeta.setDisplayName("§b§lFlying Pig Kit");
        flyingpig.setItemMeta(flyingpigMeta);
    }

    ItemStack ghost = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
    {
        ghost.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
        ItemMeta ghostMeta = ghost.getItemMeta();
        ghostMeta.setDisplayName("§7§lGhost Kit");
        ghost.setItemMeta(ghostMeta);
    }

    ItemStack mummy = new ItemStack(Material.WEB, 1);
    {
        mummy.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
        ItemMeta mummyMeta = mummy.getItemMeta();
        mummyMeta.setDisplayName("§f§lMummy Kit");
        mummy.setItemMeta(mummyMeta);
    }

}
