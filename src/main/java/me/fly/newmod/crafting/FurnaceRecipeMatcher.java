package me.fly.newmod.crafting;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ModItemType;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

public class FurnaceRecipeMatcher {
    public static boolean matches(CookingRecipe recipe, ItemStack stack) {
        ModItemType type = NewMod.get().getItemManager().getType(stack);
        RecipeChoice choice = recipe.getInputChoice();

        if(choice instanceof RecipeChoice.ExactChoice || type.isCraftable() || type.isReplaceableRecipe(recipe.getKey())) {
            return choice.test(stack);
        }

        return false;
    }
}
