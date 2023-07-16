package me.fly.newmod.api.crafting.properties;

import me.fly.newmod.api.item.properties.ModItemProperties;
import org.bukkit.NamespacedKey;

import java.util.Set;

public interface CraftingProperties extends ModItemProperties {
    NamespacedKey SMELTING = new NamespacedKey(NamespacedKey.MINECRAFT, "smelting");
    NamespacedKey FURNACE = new NamespacedKey(NamespacedKey.MINECRAFT, "furnace");
    NamespacedKey BLAST_FURNACE = new NamespacedKey(NamespacedKey.MINECRAFT, "blast_furnace");
    NamespacedKey SMOKER = new NamespacedKey(NamespacedKey.MINECRAFT, "smoker");
    NamespacedKey CAMPFIRE = new NamespacedKey(NamespacedKey.MINECRAFT, "campfire");

    NamespacedKey CRAFTING = new NamespacedKey(NamespacedKey.MINECRAFT, "crafting");

    NamespacedKey SMITHING = new NamespacedKey(NamespacedKey.MINECRAFT, "smithing");
    NamespacedKey SMITHING_TRIM = new NamespacedKey(NamespacedKey.MINECRAFT, "smithing_trim");
    NamespacedKey SMITHING_MINERAL = new NamespacedKey(NamespacedKey.MINECRAFT, "smithing_mineral");
    NamespacedKey SMITHING_ARMOR = new NamespacedKey(NamespacedKey.MINECRAFT, "smithing_armor");

    NamespacedKey BREWING = new NamespacedKey(NamespacedKey.MINECRAFT, "brewing");
    NamespacedKey BREWING_FUEL = new NamespacedKey(NamespacedKey.MINECRAFT, "brewing_fuel");

    boolean allRecipes();

    Set<NamespacedKey> getRecipes();
}
