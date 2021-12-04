package fly.newmod.impl.items;

import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChemistryTable extends ModItem {
    private Map<ItemStack, ItemStack[]> recipes = new HashMap<>();

    public ChemistryTable() {
        super(Material.BREWING_STAND, "&5Chemistry Table", "chemistry_table");

        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(NewMod.get(), "chemistry_table"), this);

        recipe.addIngredient(Material.BREWING_STAND);
        recipe.addIngredient(Material.BUCKET);

        this.addRecipe(recipe);
    }

    @Override
    public List<Material> getValidMaterials() {
        return Collections.singletonList(Material.BREWING_STAND);
    }

    public void addOreRecipe(ItemStack key, ItemStack one, ItemStack two, ItemStack three) {
        recipes.put(key, new ItemStack[]{one, two, three});
    }

    @Override
    public void tick(Location location) {
        for(Map.Entry<ItemStack, ItemStack[]> entry : recipes.entrySet()) {

        }
    }
}
