package me.fly.newmod.api.crafting.properties;

import org.bukkit.NamespacedKey;

import java.util.HashSet;
import java.util.Set;

public class CraftingPropertiesImpl implements CraftingProperties {
    private final boolean all;

    private final Set<NamespacedKey> recipes = new HashSet<>();

    public CraftingPropertiesImpl(boolean all) {
        this.all = all;
    }

    @Override
    public boolean allRecipes() {
        return all;
    }

    @Override
    public Set<NamespacedKey> getRecipes() {
        return new HashSet<>(recipes);
    }

    public CraftingPropertiesImpl addRecipe(NamespacedKey key) {
        recipes.add(key);

        return this;
    }
}
