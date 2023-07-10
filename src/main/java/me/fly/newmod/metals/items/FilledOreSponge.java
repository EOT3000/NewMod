/*package me.fly.newmod.metals.items;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.BlockManager;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemStack;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.texture.DefaultMetaFlags;
import me.fly.newmod.api.item.texture.MetaModifier;
import me.fly.newmod.metals.MetalTextures;
import me.fly.newmod.metals.MetalsModuleTypes;
import me.fly.newmod.metals.MetalsPlugin;
import me.fly.newmod.metals.items.meta.FilledOreSpongeMeta;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Material.*;

public class FilledOreSponge extends ModItemType {
    private final static Map<Material, String> VARIANTS = new HashMap<>();

    public FilledOreSponge() {
        super(PLAYER_HEAD, new NamespacedKey(MetalsPlugin.get(), "filled_ore_sponge"), FilledOreSpongeMeta.class);

        name("Filled Ore Sponge", 0x7d5e36);
        addModifier(new MetaModifier<>(MetalTextures.FILLED_ORE_SPONGE.getRawTexture(), DefaultMetaFlags.SKULL_MODIFIER));

        NewMod.get().getItemManager().registerItem(this);

        addVariant(AIR, "errored");
    }

    public void addVariant(Material ore, String name) {
        VARIANTS.put(ore, name);

        ModItemStack modStack = new ModItemStack(this);

        FilledOreSpongeMeta meta = (FilledOreSpongeMeta) modStack.getMeta();

        meta.setMaterial(ore);

        modStack.setMeta(meta);

        ItemStack stack = modStack.create();

        stack.setAmount(8);

        stack.getItemMeta();

        @SuppressWarnings("deprecation")
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(getId().getNamespace(), getId().getKey() + "_" + ore.name().toLowerCase()), stack);

        shapedRecipe.shape("AAA", "ABA", "AAA");

        shapedRecipe.setIngredient('A', new ModItemStack(MetalsModuleTypes.ORE_SPONGE).create());
        shapedRecipe.setIngredient('B', new ItemStack(ore));

        Bukkit.addRecipe(shapedRecipe);

        @SuppressWarnings("deprecation")
        NamespacedKey nk = new NamespacedKey(getId().getNamespace(), getId().getKey() + "_" + ore.name().toLowerCase() + "_furnace");

        System.out.println(nk);

        BlastingRecipe furnaceRecipe = new BlastingRecipe(nk, new ModItemStack(MetalsModuleTypes.HARD_CARBON_CHUNK).create(), new RecipeChoice.ExactChoice(modStack.create()), 2.0f, 600);

        Bukkit.addRecipe(furnaceRecipe);
    }

    public static String getVariantName(Material ore) {
        return VARIANTS.get(ore);
    }

    public static class SpongeListener implements Listener {
        @EventHandler
        public void onFurnaceSmelt(FurnaceSmeltEvent event) {
            Location l = event.getBlock().getLocation().add(0, -1, 0);
            BlockManager bs = NewMod.get().getBlockManager();
            ItemManager is = NewMod.get().getItemManager();

            if (is.getType(event.getResult()) == MetalsModuleTypes.HARD_CARBON_CHUNK && l.getBlock().getType().equals(HOPPER)) {
                ModItemStack stack = new ModItemStack(((RecipeChoice.ExactChoice) event.getRecipe().getInputChoice()).getItemStack());
                FilledOreSpongeMeta sponge = (FilledOreSpongeMeta) stack.getMeta();

                ItemStack item = MetalsModuleTypes.refine(sponge.getMaterial());

                Hopper dropper = (Hopper) l.getBlock().getState();

                dropper.getInventory().addItem(item);
            }
        }
    }
}
*/