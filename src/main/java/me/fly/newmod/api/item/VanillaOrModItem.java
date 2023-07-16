package me.fly.newmod.api.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface VanillaOrModItem {
    default Material getVanilla() {
        return null;
    }

    default ModItemType<?> getMod() {
        return null;
    }

    ItemStack create();
}
