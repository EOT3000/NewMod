package me.fly.newmod.api.crafting.properties;

import me.fly.newmod.api.item.properties.ItemProperties;
import org.bukkit.NamespacedKey;

import java.util.Set;

public interface CraftingProperties extends ItemProperties {
    NamespacedKey SMELTING = NamespacedKey.minecraft("smelting");
    NamespacedKey FURNACE = NamespacedKey.minecraft("furnace");
    NamespacedKey BLAST_FURNACE = NamespacedKey.minecraft("blast_furnace");
    NamespacedKey SMOKER = NamespacedKey.minecraft("smoker");
    NamespacedKey CAMPFIRE = NamespacedKey.minecraft("campfire");

    NamespacedKey CRAFTING = NamespacedKey.minecraft("crafting");

    NamespacedKey SMITHING = NamespacedKey.minecraft("smithing");
    NamespacedKey SMITHING_TRIM = NamespacedKey.minecraft("smithing_trim");
    NamespacedKey SMITHING_MINERAL = NamespacedKey.minecraft("smithing_mineral");
    NamespacedKey SMITHING_ARMOR = NamespacedKey.minecraft("smithing_armor");

    NamespacedKey BREWING = NamespacedKey.minecraft("brewing");
    NamespacedKey BREWING_FUEL = NamespacedKey.minecraft("brewing_fuel");

    boolean isAllRecipes();

    Set<NamespacedKey> getRecipes();
}
