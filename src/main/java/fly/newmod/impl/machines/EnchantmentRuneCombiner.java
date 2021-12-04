package fly.newmod.impl.machines;

import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import fly.newmod.bases.inventory.ItemButtonBlock;
import fly.newmod.bases.magic.Rune;
import fly.newmod.bases.magic.Runes;
import fly.newmod.impl.magic.EnchantingRune;
import fly.newmod.setup.Setup;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class EnchantmentRuneCombiner extends ItemButtonBlock {
    public EnchantmentRuneCombiner() {
        super(Material.ENCHANTING_TABLE, "&4Enchanting Rune Combiner", "enchantment_rune_combiner");

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(NewMod.get(), getId()), this);

        recipe.shape("IRI", "CEC", "INI");

        recipe.setIngredient('I', Setup.TITANIUM_INGOT);
        recipe.setIngredient('R', Setup.BLANK_RUNE);
        recipe.setIngredient('C', new ItemStack(Material.COPPER_INGOT));
        recipe.setIngredient('E', new ItemStack(Material.ENCHANTING_TABLE));
        recipe.setIngredient('N', new ItemStack(Material.NETHERITE_INGOT));

        addRecipe(recipe);

        setFill(newStack(" ", Material.BLACK_STAINED_GLASS_PANE))
                .setSlot(1, new StorageSlot(1) {
                    @Override
                    public boolean canHold(ItemStack stack, Inventory context) {
                        Rune rune = Runes.getRune(stack);

                        System.out.println(rune != null ? "1: " + rune.getRuneType() : "fail 1");

                        return rune != null && rune.getRuneType().equals(Rune.RuneType.ENCHANTMENT_TYPE);
                    }
                })
                .setSlot(2, new StorageSlot(2) {
                    @Override
                    public boolean canHold(ItemStack stack, Inventory context) {
                        Rune rune = Runes.getRune(stack);

                        System.out.println(rune != null ? "2: " + rune.getRuneType() : "fail 2");

                        return rune != null && rune.getRuneType().equals(Rune.RuneType.ENCHANTMENT_LEVEL);
                    }
                })
                .setSlot(3, new StorageSlot(3) {
                    @Override
                    public boolean canHold(ItemStack stack, Inventory context) {
                        Rune rune = Runes.getRune(getInventorySlot(1).get(context));

                        System.out.println(rune != null ? "3: " + rune.getType() : "fail 3");

                        return rune instanceof EnchantingRune && ((EnchantingRune) rune).getValidAdditions().contains(stack.getType());
                    }
                })
                .setSlot(4, new StorageSlot(4) {
                    @Override
                    public boolean canHold(ItemStack stack, Inventory context) {
                        return false;
                    }
                })
                .setSlot(5, new ButtonSlot() {
                    @Override
                    public void run(Map<Integer, ItemStack> items, ClickType type, Inventory context) {
                        ((StorageSlot) getInventorySlot(4)).set(context, Runes.combine(items.get(1), items.get(2), items.get(3)));

                        ((StorageSlot) getInventorySlot(1)).set(context, new ItemStack(Material.AIR));
                        ((StorageSlot) getInventorySlot(2)).set(context, new ItemStack(Material.AIR));
                        ((StorageSlot) getInventorySlot(3)).set(context, new ItemStack(Material.AIR));
                    }

                    @Override
                    public ItemStack get(Inventory inventory) {
                        return newStack("&4Click to merge", Material.LIME_STAINED_GLASS_PANE);
                    }
                });
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        ItemStack a = event.getInventory().getFirstItem();
        ItemStack b = event.getInventory().getSecondItem();

        Rune rune = Runes.getRune(b);

        if(a == null || rune == null || b.getItemMeta().getEnchants().size() == 0) {
            return;
        }

        a = a.clone();

        ItemMeta meta = a.getItemMeta();

        for(Map.Entry<Enchantment, Integer> entry : b.getItemMeta().getEnchants().entrySet()) {
            meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }

        a.setItemMeta(meta);

        event.setResult(a);

        event.getInventory().setRepairCost(100);
    }
}