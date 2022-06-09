package fly.newmod.listeners;

import fly.newmod.NewMod;
import fly.newmod.api.item.ItemManager;
import fly.newmod.api.item.ModItemStack;
import fly.newmod.api.item.type.ModItemType;
import fly.newmod.api.block.BlockManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.ArrayList;
import java.util.List;

public class VanillaReplacementListener implements Listener {
    private final int[][] tableItems = new int[][]
                    {{0,0},{0,1},{0,2},
                    {1,0},{1,1},{1,2},
                    {2,0},{2,1},{2,2}};

    @EventHandler
    @SuppressWarnings({"ConstantConditions", "PatternVariableCanBeUsed"})
    public void onPreCraftE(PrepareItemCraftEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();
        ItemManager itemManager = NewMod.get().getItemManager();

        if (event.getRecipe() == null) {
            return;
        }

        System.out.println("a");

        if (event.getRecipe() instanceof ShapelessRecipe) {
            System.out.println("z");

            List<ItemStack> vanilla = new ArrayList<>();
            List<ItemStack> mod = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                ItemStack stack = event.getInventory().getMatrix()[i];

                if (stack == null) {
                    continue;
                }

                if (itemManager.getType(stack) != null) {
                    mod.add(stack);
                } else {
                    vanilla.add(stack);
                }
            }

            System.out.println("b");

            if (!matchesRecipe((ShapelessRecipe) event.getRecipe(), mod, vanilla, itemManager)) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
            }

            return;
        }

        for (int i = 0; i < event.getInventory().getSize(); i++) {
            ItemStack stack = event.getInventory().getMatrix()[i];

            if (stack == null) {
                continue;
            }

            ModItemType type = itemManager.getType(stack);

            if (type == null || type.isCraftable()) {
                continue;
            }

            if (event.isRepair() || !(event.getRecipe() instanceof ShapedRecipe)) {
                event.getInventory().setResult(new ItemStack(Material.AIR));

                return;
            }

            ShapedRecipe recipe = (ShapedRecipe) event.getRecipe();

            int[] t = tableItems[i];
            char c = recipe.getShape()[t[0]].charAt(t[1]);

            if (!new ModItemStack(stack).equals(new ModItemStack(recipe.getIngredientMap().get(c)))) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
                return;
            }
        }
    }

    private boolean matchesRecipe(ShapelessRecipe recipe, List<ItemStack> mod, List<ItemStack> vanilla, ItemManager storage) {
        List<ItemStack> remainingIngredients = new ArrayList<>(recipe.getIngredientList());
        List<ItemStack> mar = new ArrayList<>(mod);
        List<ItemStack> var = new ArrayList<>(vanilla);

        System.out.println(mar);
        System.out.println(var);

        for(ItemStack stack : recipe.getIngredientList()) {
            if(storage.getType(stack) != null) {
                ModItemType type = storage.getType(stack);

                for(ItemStack ms : mar) {
                    if(storage.getType(ms).equals(type)) {
                        remainingIngredients.remove(stack);

                        mod.remove(ms);
                    }
                }
            } else {
                for(ItemStack vs : var) {
                    if(vs.isSimilar(stack)) {
                        remainingIngredients.remove(stack);

                        vanilla.remove(vs);
                    }
                }
            }
        }

        System.out.println("checked");
        System.out.println(vanilla);
        System.out.println(mod);

        if(!vanilla.isEmpty()) {
            return false;
        }

        System.out.println("x");

        for(ItemStack stack : mod) {
            System.out.println("k");

            ModItemType type = storage.getType(stack);

            if(type.isCraftable()) {
                vanilla.add(stack);
            } else {
                return false;
            }
        }

        System.out.println("d");

        return remainingIngredients.size() == vanilla.size();
    }

    @EventHandler
    public void onPreSmeltE(FurnaceStartSmeltEvent event) {

    }
}
