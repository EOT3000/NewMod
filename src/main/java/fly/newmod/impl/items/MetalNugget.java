package fly.newmod.impl.items;

import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class MetalNugget extends ModItem {
    private ItemStack metal;
    
    public MetalNugget(Material material, String name, String id, ItemStack metal) {
        super(material, name, id);
        
        this.metal = metal;
        
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(NewMod.get(), id), this);
        
        recipe.addIngredient(metal);
        
        addRecipe(recipe);
        

        ShapelessRecipe recipeReverse = new ShapelessRecipe(new NamespacedKey(NewMod.get(), id + "_reverse"), metal);

        recipeReverse.addIngredient(9, this);

        addRecipe(recipeReverse);
    }

    public ItemStack getMetal() {
        return metal;
    }
}
