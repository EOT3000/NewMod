package me.fly.newmod.listener;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import me.fly.newmod.BookTypes;
import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.ModBlock;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemStack;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.util.PersistentDataUtils;
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
import org.bukkit.block.Furnace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.world.GenericGameEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
    public void onPreSmelt(FurnaceStartSmeltEvent event) {
        if(event.getRecipe() instanceof ModFurnaceRecipe recipe) {
            if(!recipe.canBeUsed(new ModBlock(event.getBlock()).getType())) {
                Furnace furnace = (Furnace) event.getBlock().getState();

                furnace.setBurnTime((short) -1);

                furnace.update();

                return;
            }
        }

        ModItemType type = NewMod.get().getItemManager().getType(event.getSource());

        if(type == null) {
            return;
        }

        if(!FurnaceRecipeMatcher.matches(event.getRecipe(), event.getSource())) {
            Furnace furnace = (Furnace) event.getBlock().getState();

            furnace.setBurnTime((short) -1);

            furnace.update();
        }
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        event.getInventory().setRepairCost((int) Math.ceil(Math.tanh(event.getInventory().getRepairCost()/39.0)*39));
    }

    @EventHandler
    public void onSmith(PrepareSmithingEvent event) {
        event.getInventory().getRecipe();
    }

    private boolean getSigned(ItemStack stack) {
        return stack.getItemMeta().getPersistentDataContainer().getOrDefault(SIGNED, PersistentDataType.BOOLEAN, false);
    }

    private long getId(ItemStack stack) {
        return stack.getItemMeta().getPersistentDataContainer().get(ID, PersistentDataType.LONG);
    }

    private boolean isBark(ItemStack stack) {
        ModItemType type = NewMod.get().getItemManager().getType(stack);

        return BookTypes.BIRCH_BARK.equals(type);
    }

    private boolean isBook(ItemStack stack) {
        if(stack == null || !stack.hasItemMeta()) {
            return false;
        }

        return stack.getItemMeta().getPersistentDataContainer().getOrDefault(OFFHAND_ONLY, PersistentDataType.BOOLEAN, false);
    }

    private boolean bookBarkMismatch(PlayerInventory inventory) {
        if(isBark(inventory.getItemInMainHand())) {
            if(isBook(inventory.getItemInOffHand())) {
                return getId(inventory.getItemInOffHand()) != getId(inventory.getItemInMainHand());
            } else {
                return true;
            }
        }

        return false;
    }

    private void putBook(PlayerInventory inv) {
        ItemStack writableBook = new ItemStack(Material.WRITABLE_BOOK);
        BookMeta meta = (BookMeta) writableBook.getItemMeta();

        meta.pages(Component.text(""),
                Component.text("Writing on this or subsequent pages will not be saved. Only write on page 1.").color(TextColor.color(0xFF0000)));
        meta.getPersistentDataContainer().set(OFFHAND_ONLY, PersistentDataType.BOOLEAN, true);

        writableBook.setItemMeta(meta);

        inv.setItemInOffHand(writableBook);
    }

    //TODO: SIMPLIFY THIS AND NEW CLASS

    /**
     * An event
     * @param             event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
    //It's ok

        //System.out.println(event.getCurrentItem());

        if(isBook(event.getCurrentItem())) {
            if(event.getSlot() == 40 && event.getClickedInventory() instanceof PlayerInventory) {
                event.setCancelled(true);
            }
        } else {
            ItemStack placed = event.getCursor();

            if(isBook(placed)) {
                if(event.getClickedInventory() == null) {
                    return;
                }

                Bukkit.getScheduler().runTaskLater(NewMod.get(), () -> {
                    HumanEntity p = event.getWhoClicked();

                    ItemStack stack = p.getOpenInventory().getItem(event.getRawSlot());

                    if(isBook(stack)) {
                        p.getOpenInventory().setItem(event.getRawSlot(), null);
                    }

                }, 1);
            }
        }

        if(isBook(event.getWhoClicked().getInventory().getItemInOffHand())) {
            Bukkit.getScheduler().runTaskLater(NewMod.get(), () -> {
                if (bookBarkMismatch(event.getWhoClicked().getInventory())) {
                    putBook(event.getWhoClicked().getInventory());
                }
            }, 1);
        }
    }

    //TODO: seperate class and use clever packets
    @EventHandler
    public void onHotbarSwitch(PlayerItemHeldEvent event) {
        PlayerInventory inv = event.getPlayer().getInventory();

        ItemStack stack = inv.getItem(event.getNewSlot());
        ModItemType type = NewMod.get().getItemManager().getType(stack);

        if (BookTypes.BIRCH_BARK.equals(type) && !getSigned(stack)) {
            if (inv.getItemInOffHand().getType().equals(Material.AIR) || isBook(inv.getItemInOffHand())) {
                putBook(inv);
            }
        } else if (isBook(inv.getItemInOffHand())) {
            inv.setItemInOffHand(null);
        }
    }

    @EventHandler
    public void onSwitchHand(PlayerSwapHandItemsEvent event) {
        if(isBook(event.getMainHandItem()) || isBook(event.getOffHandItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBookEdit(PlayerEditBookEvent event) {
        if(event.getNewBookMeta().getPersistentDataContainer().getOrDefault(OFFHAND_ONLY, PersistentDataType.BOOLEAN, false)) {
            PlayerInventory inv = event.getPlayer().getInventory();
            ItemManager manager = NewMod.get().getItemManager();

            if(BookTypes.BIRCH_BARK.equals(manager.getType(inv.getItemInMainHand()))) {
                inv.setItemInOffHand(null);

                ItemMeta meta = inv.getItemInMainHand().getItemMeta();

                meta.getPersistentDataContainer().set(PAGES, PersistentDataUtils.COMPONENT, event.getNewBookMeta().page(1));
                meta.getPersistentDataContainer().set(SIGNED, PersistentDataType.BOOLEAN, true);

                inv.getItemInMainHand().setItemMeta(meta);
            }
        }
    }
    
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(isBook(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }
}
