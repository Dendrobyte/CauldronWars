package com.redstoneoinkcraft.me.listeners;

import com.redstoneoinkcraft.me.Main;
import com.redstoneoinkcraft.me.arenas.RunningArenaManager;
import com.redstoneoinkcraft.me.kits.*;
import com.redstoneoinkcraft.me.utilities.KitSelectionInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Author - markbacon78
 * File created on 8/20/17
 * This project is property of markbacon78 and was created for single user purposes only.
 * Do not copy or redistribute this document (if you have somehow gotten access to the code...)
 * §
 */
public class InventoryClickListener implements Listener {

    @EventHandler
    public void kitSelectionClick(InventoryClickEvent event){
        Inventory inv = event.getInventory();
        if(!inv.getName().equals(Main.getInstance().getPrefix() + "Kit Selection")) return;

        KitManager km = KitManager.getManager();
        RunningArenaManager ram = RunningArenaManager.getManager();
        Player player = (Player) event.getWhoClicked();

        if(ram.isInGame(player) == null){
            player.sendMessage(Main.getInstance().getPrefix() + "How are you even in this menu...?");
            event.setCancelled(true);
            player.closeInventory();
        }

        ItemStack itemStack = event.getCurrentItem();
        if(itemStack == null) return;
        String itemStackName = itemStack.getItemMeta().getDisplayName();


        // Sorry for all the one-liners... I'm just not feeling the formatting of switch statements :)
        // All the setCancelled and closeInventory could all be after the cases too, if I didn't put break;... Whatever, right? :P
        switch (itemStackName) {
            case "§9§lGladiator Kit": km.removeKit(player); km.addKit(new Gladiator(), player); event.setCancelled(true); player.closeInventory(); break;
            case "§2§lArcher Kit": km.removeKit(player); km.addKit(new Archer(), player); event.setCancelled(true); player.closeInventory(); break;
            case "§6§lMonk Kit": km.removeKit(player); km.addKit(new Monk(), player); event.setCancelled(true); player.closeInventory(); break;
            case "§c§lBoxer Kit": km.removeKit(player); km.addKit(new Boxer(), player); event.setCancelled(true); player.closeInventory(); break;
            case "§b§lFlying Pig Kit": km.removeKit(player); km.addKit(new FlyingPig(), player); event.setCancelled(true); player.closeInventory(); break;
            case "§7§lGhost Kit": km.removeKit(player); km.addKit(new Ghost(), player); event.setCancelled(true); player.closeInventory(); break;
            case "§f§lMummy Kit": km.removeKit(player); km.addKit(new Mummy(), player); event.setCancelled(true); player.closeInventory(); break;
            default: player.sendMessage(Main.getInstance().getPrefix() + "§cThat's not a valid kit...? Contact an Admin."); event.setCancelled(true); player.closeInventory();
        }

    }

}
