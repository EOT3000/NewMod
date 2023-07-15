package me.fly.newmod.listener;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import me.fly.newmod.BookTypes;
import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.ModBlock;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemStack;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.util.PersistentDataUtils;
import me.fly.newmod.books.BookUtils;
import me.fly.newmod.crafting.FurnaceRecipeMatcher;
import me.fly.newmod.crafting.ShapedRecipeMatcher;
import me.fly.newmod.crafting.ShapelessRecipeMatcher;
import me.fly.newmod.technology.consumer.ModFurnaceRecipe;
import me.fly.newmod.time.nms.bee.CustomBeeHiveTicker;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.block.Beehive;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Furnace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BrewingStartEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.world.GenericGameEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.List;

public class VanillaReplacementListener implements Listener {
    private final int[][] tableItems = new int[][]
                    {{0,0},{0,1},{0,2},
                    {1,0},{1,1},{1,2},
                    {2,0},{2,1},{2,2}};

    private int count = 0;

    public static final NamespacedKey OFFHAND_ONLY = new NamespacedKey(NewMod.get(), "offhand_only");
    public static final NamespacedKey PAGES = new NamespacedKey(NewMod.get(), "pages");
    public static final NamespacedKey ID = new NamespacedKey(NewMod.get(), "book_id");
    public static final NamespacedKey SIGNED = new NamespacedKey(NewMod.get(), "signed");

    public VanillaReplacementListener() {
        System.out.println("created");
    }

    /*@EventHandler(ignoreCancelled = true)
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
    }*/

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
    public void onPreCraft(PrepareItemCraftEvent event) {
        if(event.getRecipe() instanceof ShapedRecipe) {
            if(!ShapedRecipeMatcher.matches(event.getInventory())) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
            }
        } else if(event.getRecipe() instanceof ShapelessRecipe recipe) {
            if(!ShapelessRecipeMatcher.matches(recipe, event.getInventory().getMatrix())) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
            }
        }
    }

    @EventHandler
    public void onPreSmelt(FurnaceBurnEvent event) {
        /*if(event.getRecipe() instanceof ModFurnaceRecipe recipe) {
            if(!recipe.canBeUsed(new ModBlock(event.getBlock()).getType())) {
                Furnace furnace = (Furnace) event.getBlock().getState();

                furnace.setBurnTime((short) -1);

                furnace.update();

                return;
            }
        }*/

        FurnaceInventory inventory = ((Furnace) event.getBlock().getState()).getInventory();

        ModItemType type = NewMod.get().getItemManager().getType(inventory.getSmelting());

        if(type == null) {
            return;
        }

        if(!type.isCraftable()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        event.getInventory().setRepairCost((int) Math.ceil(Math.tanh(event.getInventory().getRepairCost()/39.0)*39));
    }

    //TODO: smithing and brewing namespace stored somewhere

    @EventHandler
    public void onSmith(PrepareSmithingEvent event) {
        ModItemType type = NewMod.get().getItemManager().getType(event.getInventory().getInputMineral());

        if(type != null && !(type.isCraftable() || type.isReplaceableRecipe(new NamespacedKey(NamespacedKey.MINECRAFT, "smithing")))) {
            event.setResult(null);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClick().isShiftClick() && event.getInventory() instanceof BrewerInventory) {
            ModItemType type = NewMod.get().getItemManager().getType(event.getCurrentItem());

            if(type == null) {
                return;
            }

            //if(!type.isCraftable() && !type.isReplaceableRecipe(new NamespacedKey(NamespacedKey.MINECRAFT, "brewing"))) {
                event.setCancelled(true);
            //}
        } else if(event.getClickedInventory() instanceof BrewerInventory) {
            ModItemType type = NewMod.get().getItemManager().getType(event.getCursor());

            if(type == null) {
                return;
            }

            //if(!type.isCraftable() && !type.isReplaceableRecipe(new NamespacedKey(NamespacedKey.MINECRAFT, "brewing"))) {
                event.setCancelled(true);
            //}
        }
    }

    @EventHandler
    public void onBrewStart(BrewingStartEvent event) {
        ModItemType type = NewMod.get().getItemManager().getType(event.getSource());

        if (type == null) {
            return;
        }

        //if (!type.isCraftable() && !type.isReplaceableRecipe(new NamespacedKey(NamespacedKey.MINECRAFT, "brewing"))) {
            BrewingStand stand = (BrewingStand) event.getBlock().getBlockData();

            stand.getInventory().setIngredient(null);

            stand.update();
        //}
    }
}
