package me.fly.newmod.crafting;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ModItemType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipeMatcher {
    public static boolean matches(ShapelessRecipe recipe, ItemStack[] stacks) {
        //TODO: make it smarter
        //ShapelessRecipe ro = recipe.getChoiceList();
        List<ItemStack> so = new ArrayList<>(List.of(stacks));

        if(recipe.getChoiceList().size() != stacks.length) {
            return false;
        }

        a: for(RecipeChoice choice : recipe.getChoiceList()) {
            for(ItemStack stack : stacks) {
                if(matches(choice, stack, recipe)) {
                    so.remove(stack);

                    continue a;
                }
            }

            return false;
        }

        return true;
    }

    private static boolean matches(RecipeChoice choice, ItemStack stack, ShapelessRecipe recipe) {
        ModItemType type = NewMod.get().getItemManager().getType(stack);

        if(choice instanceof RecipeChoice.ExactChoice || type.isCraftable() || type.isReplaceableRecipe(recipe.getKey())) {
            return choice.test(stack);
        }

        return false;
    }
}
