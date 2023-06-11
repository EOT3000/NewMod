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

    private final Material[] trims = {
            Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
            Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE,
    };

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

        for(Material material : trims) {
            preventCraft(material, Material.DIAMOND);
        }
    }
}
