package fly.newmod.recipe;

import fly.newmod.NewMod;
import fly.newmod.api.item.type.ModItemType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeManager implements Listener {
    private final Map<Object, List<Recipe>> recipes = new HashMap<>();

    @SuppressWarnings("ConstantConditions")
    public void addRecipe(ModItemType item, Recipe recipe) {
        recipes.putIfAbsent(item, new ArrayList<>()).add(recipe);
    }

    @SuppressWarnings("ConstantConditions")
    public void addRecipe(Material item, Recipe recipe) {
        recipes.putIfAbsent(item, new ArrayList<>()).add(recipe);
    }

    public void openGuide(Player player) {
        for(NewMod.ModExtension extension : NewMod.get().getExtensions()) {

        }
    }
}
