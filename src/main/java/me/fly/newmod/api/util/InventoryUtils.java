package me.fly.newmod.api.util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    //public static boolean removeSingleItem(Player player, ItemStack stack) {
    //    return removeSingleItem(player.getInventory(), stack);
    //}

    /*public static boolean removeSingleItem(Inventory inventory, ItemStack stack) {
        if(!inventory.contains(stack, 1)) {
            return false;
        }

        for(int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getContents()[i];

            if(item != null && ItemManager.(stack, item)) {
                return removeOneFromSlot(inventory, i);
            }
        }

        return false;
    }*/

    public static boolean removeOneFromSlot(Inventory inventory, int slot) {
        ItemStack item = inventory.getItem(slot);

        if(item == null) {
            return false;
        }

        if(item.getAmount() > 1) {
            item.setAmount(item.getAmount()-1);

            inventory.setItem(slot, item);
        } else {
            inventory.setItem(slot, new ItemStack(Material.AIR));
        }

        return true;
    }
}
