package me.fly.newmod.listener;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemStack;
import me.fly.newmod.api.item.ModItemType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class VanillaReplacementListener implements Listener {
    private final int[][] tableItems = new int[][]
                    {{0,0},{0,1},{0,2},
                    {1,0},{1,1},{1,2},
                    {2,0},{2,1},{2,2}};

    private int count = 0;

    @EventHandler
    @SuppressWarnings({"ConstantConditions", "PatternVariableCanBeUsed"})
    public void onPreCraftE(PrepareItemCraftEvent event) {
        try {
            ItemManager manager = NewMod.get().getItemManager();

            if (event.getRecipe() instanceof ShapedRecipe) {
                if (!event.getInventory().getType().equals(InventoryType.WORKBENCH)) {
                    checkForAny(event);
                    return;
                }

                ShapedRecipe recipe = (ShapedRecipe) event.getRecipe();

                for (int i = 0; i < 9; i++) {
                    char letter = recipe.getShape()[tableItems[i][0]].charAt(tableItems[i][1]);
                    ItemStack stack = recipe.getIngredientMap().get(letter);

                    ModItemType rtype = manager.getType(event.getInventory().getMatrix()[i]);
                    ModItemType stype = manager.getType(stack);

                    if (rtype == null) {
                        if (stype != null && !stype.isCraftable()) {
                            event.getInventory().setResult(new ItemStack(Material.AIR));
                            return;
                        }
                    } else {
                        if (stype != null) {
                            ModItemStack rstack = new ModItemStack(event.getInventory().getMatrix()[i]);
                            ModItemStack sstack = new ModItemStack(stack);

                            if (!rstack.getMeta().isAcceptable(sstack.getMeta())) {
                                event.getInventory().setResult(new ItemStack(Material.AIR));
                                return;
                            }
                        } else {
                            event.getInventory().setResult(new ItemStack(Material.AIR));
                            return;
                        }
                    }

                }
            } else {
                checkForAny(event);
            }
        } catch (Exception e) {
            if(count++ % 50 == 0) {
                e.printStackTrace();
            }
        }
    }

    private void checkForAny(PrepareItemCraftEvent event) {
        ItemManager manager = NewMod.get().getItemManager();

        for(ItemStack stack : event.getInventory().getMatrix()) {
            ModItemType type = manager.getType(stack);

            if(type != null && !type.isCraftable()) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
                return;
            }
        }
    }

    @EventHandler
    public void onPreSmeltE(FurnaceStartSmeltEvent event) {

    }
}
