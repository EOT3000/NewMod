package fly.newmod.impl.machines;

import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import fly.newmod.bases.magic.Runes;
import fly.newmod.setup.BlockStorage;
import fly.newmod.setup.Setup;
import fly.newmod.utils.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.Rotatable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.material.Directional;

import java.util.Collections;
import java.util.List;

public class EnchantmentRemover extends ModItem implements Listener {
    public EnchantmentRemover() {
        super(Material.DROPPER, "&4Enchantment Remover", "enchantment_remover");

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(NewMod.get(), getId()), this);

        recipe.shape("IGI", "CRP", "INI");

        recipe.setIngredient('I', Setup.TITANIUM_INGOT);
        recipe.setIngredient('G', new ItemStack(Material.GOLD_INGOT));
        recipe.setIngredient('C', new ItemStack(Material.COPPER_INGOT));
        recipe.setIngredient('R', Setup.BLANK_RUNE);
        recipe.setIngredient('P', new ItemStack(Material.PAPER));
        recipe.setIngredient('N', new ItemStack(Material.NETHERITE_INGOT));

        addRecipe(recipe);
    }

    @EventHandler
    public void dispenseEvent(BlockDispenseEvent event) {
        BlockStorage storage = NewMod.get().getBlockStorage();

        if(storage.getData(event.getBlock().getLocation(), "id").equalsIgnoreCase(getId())) {
            Dropper dropper = ((Dropper) event.getBlock().getState());
            Inventory inventory = dropper.getInventory();

            Location check = dropper.getLocation().add(((Directional) dropper.getData()).getFacing().getDirection());

            if(storage.getData(check, "id").equalsIgnoreCase("block_rune")) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) event.getItem().getItemMeta();

                boolean done = false;

                for(Pair<Enchantment, Integer> pair : Runes.getRuneTypes().keySet()) {
                    if (meta.getStoredEnchants().containsKey(pair.getKey()) && meta.getStoredEnchantLevel(pair.getKey()) == pair.getValue()) {
                        inventory.clear();

                        check.getBlock().setType(Material.AIR);
                        check.getWorld().dropItem(check, Runes.getRune(pair.getKey(), pair.getValue()));

                        done = true;
                    }
                }

                if(!done) {
                    inventory.clear();

                    check.getBlock().setType(Material.AIR);
                    check.getWorld().dropItem(check, Setup.LEVEL_RUNE1);
                }
            }

            dropper.update();

            event.setItem(new ItemStack(Material.BOOK));
        }
    }

    @Override
    public void tick(Location location) {
        Dropper dropper = ((Dropper) location.getBlock().getState());
        Inventory inventory = dropper.getInventory();

        boolean done = false;

        for(int count = 0; count < inventory.getContents().length; count++) {
            ItemStack stack = inventory.getContents()[count];

            if(stack != null) {
                if(stack.getType() != Material.ENCHANTED_BOOK || done) {
                    inventory.setItem(count, new ItemStack(Material.AIR));

                    location.getWorld().dropItem(location.clone().add(0, 1, 0), stack);
                } else {
                    done = true;
                }
            }
        }
    }

    @Override
    public List<Material> getValidMaterials() {
        return Collections.singletonList(Material.DROPPER);
    }
}
