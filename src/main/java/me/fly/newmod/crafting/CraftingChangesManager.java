package me.fly.newmod.crafting;

import org.bukkit.Material;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftingChangesManager {
    private final Map<Material, List<Material>> removed = new HashMap<>();
    private final Map<Material, List<Material>> removedFurnace = new HashMap<>();

    public void preventCraft(Material result, Material removed) {
        this.removed.putIfAbsent(result, new ArrayList<>());

        this.removed.get(result).add(removed);
    }

    public void preventSmelt(Material result, Material removed) {
        this.removed.putIfAbsent(result, new ArrayList<>());

        this.removed.get(result).add(removed);
    }

    public boolean shouldPreventCraft(CraftingInventory inventory) {
        if(inventory.getResult() != null && removed.containsKey(inventory.getResult().getType())) {
            for (ItemStack stack : inventory.getMatrix()) {
                if (stack != null && removed.get(inventory.getResult().getType()).contains(stack.getType())) {
                    return true;
                }
            }
        }

        return false;
    }

    public CraftingChangesManager() {
        preventCraft(Material.WOODEN_AXE, Material.BAMBOO_PLANKS);
        preventCraft(Material.WOODEN_HOE, Material.BAMBOO_PLANKS);
        preventCraft(Material.WOODEN_PICKAXE, Material.BAMBOO_PLANKS);
        preventCraft(Material.WOODEN_AXE, Material.BAMBOO_PLANKS);
        preventCraft(Material.WOODEN_SHOVEL, Material.BAMBOO_PLANKS);
        preventCraft(Material.SHIELD, Material.BAMBOO_PLANKS);

        preventCraft(Material.BOWL, Material.BAMBOO_PLANKS);

        preventCraft(Material.CRAFTING_TABLE, Material.BAMBOO_PLANKS);
        preventCraft(Material.CHEST, Material.BAMBOO_PLANKS);
        preventCraft(Material.BARREL, Material.BAMBOO_PLANKS);
        preventCraft(Material.BEEHIVE, Material.BAMBOO_PLANKS);
        preventCraft(Material.GRINDSTONE, Material.BAMBOO_PLANKS);
        preventCraft(Material.PISTON, Material.BAMBOO_PLANKS);
        preventCraft(Material.SMITHING_TABLE, Material.BAMBOO_PLANKS);
        preventCraft(Material.STICK, Material.BAMBOO_PLANKS);

        preventCraft(Material.BARREL, Material.BAMBOO_SLAB);

        preventCraft(Material.STICK, Material.BAMBOO);
    }
}
