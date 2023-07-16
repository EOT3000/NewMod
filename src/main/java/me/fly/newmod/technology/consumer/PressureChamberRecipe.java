package me.fly.newmod.technology.consumer;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.technology.TechnologyModuleTypes;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

public class PressureChamberRecipe extends CookingRecipe<PressureChamberRecipe> implements ModFurnaceRecipe {
    public PressureChamberRecipe(NamespacedKey key, ItemStack result, RecipeChoice source, float experience, int cookingTime) {
        super(key, result, source, experience, cookingTime);
    }

    @Override
    public boolean canBeUsed(ModBlockType type) {
        return type instanceof PressureChamberBlockType;
    }

    @Override
    public ModItemType primaryBlock() {
        return TechnologyModuleTypes.PRESSURE_CHAMBER;
    }
}
