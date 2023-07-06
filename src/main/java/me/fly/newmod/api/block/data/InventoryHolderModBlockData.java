package me.fly.newmod.api.block.data;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public interface InventoryHolderModBlockData extends ModBlockData {
    Map<Integer, ItemStack> getStoredItems();
    void setStoredItem(int index, ItemStack stack);

    int getSize();
}
