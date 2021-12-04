package fly.newmod.setup;

import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import fly.newmod.impl.items.MetalNugget;
import fly.newmod.impl.machines.*;
import fly.newmod.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import static org.bukkit.enchantments.Enchantment.*;
import static org.bukkit.Material.*;

public class Setup {
    public static void init() {
        ShapelessRecipe gunpowder = new ShapelessRecipe(new NamespacedKey(NewMod.get(), "gunpowder"), new ItemStack(GUNPOWDER, 8));

        gunpowder.addIngredient(1, SALTPETER);
        gunpowder.addIngredient(1, SULFUR_NUGGET);
        gunpowder.addIngredient(1, CHARCOAL);

        Bukkit.addRecipe(gunpowder);

        ShapelessRecipe drugs1 = new ShapelessRecipe(new NamespacedKey(NewMod.get(), "drug_potato"), DRUG_POTATO);

        drugs1.addIngredient(POTATO);
        drugs1.addIngredient(GLOWSTONE_DUST);
        drugs1.addIngredient(REDSTONE);

        Bukkit.addRecipe(drugs1);
        
        
    }

    //TODO: Custom colors

    public static final ModItem ZINC_INGOT = new ModItem(IRON_INGOT, "&7Zinc Ingot", "zinc_ingot");
    public static final ModItem ALUMINUM_INGOT = new ModItem(IRON_INGOT, "&8Aluminum Ingot", "aluminum_ingot");
    public static final ModItem TIN_INGOT = new ModItem(IRON_INGOT, "&7Tin Ingot", "tin_ingot");
    public static final ModItem TITANIUM_INGOT = new ModItem(IRON_INGOT, "&8Titanium Ingot", "titanium_ingot");
    public static final ModItem NEODYMIUM_INGOT = new ModItem(IRON_INGOT, "&7Neodymium Ingot", "neodymium_ingot");
    public static final ModItem MAGNESIUM_INGOT = new ModItem(IRON_INGOT, "&7Magnesium Ingot", "magnesium_ingot");
    public static final ModItem CALCIUM_INGOT = new ModItem(IRON_INGOT, "&fCalcium Ingot", "calcium_ingot");
    public static final ModItem SODIUM_INGOT = new ModItem(IRON_INGOT, "&fSodium Ingot", "sodium_ingot");
    public static final ModItem ZIRCONIUM_INGOT = new ModItem(IRON_INGOT, "&7Zirconium Ingot", "zirconium_ingot");
    public static final ModItem POTASSIUM_INGOT  = new ModItem(IRON_INGOT, "&7Potassium Ingot", "potassium_ingot");

    public static final ModItem REDSTONE_INGOT = new ModItem(NETHER_BRICK, "&4Redstone Ingot", "redstone_ingot");
    public static final ModItem SILICON_INGOT = new ModItem(IRON_INGOT, "&fSilicon Ingot", "silicon_ingot");
    
    public static final ModItem CARBON_CHUNK  = new ModItem(COAL, "&8Carbon Chunk", "carbon_chunk");
    
    public static final ModItem ZINC_NUGGET = new MetalNugget(IRON_NUGGET, "&7Zinc Nugget", "zinc_nugget", ZINC_INGOT);
    public static final ModItem ALUMINUM_NUGGET = new MetalNugget(IRON_NUGGET, "&8Aluminum Nugget", "aluminum_nugget", ALUMINUM_INGOT);
    public static final ModItem TIN_NUGGET = new MetalNugget(IRON_NUGGET, "&7Tin Nugget", "tin_nugget", TIN_INGOT);
    public static final ModItem TITANIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&8Titanium Nugget", "titanium_nugget", TITANIUM_INGOT);
    public static final ModItem NEODYMIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&7Neodymium Nugget", "neodymium_nugget", NEODYMIUM_INGOT);
    public static final ModItem MAGNESIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&7Magnesium Nugget", "magnesium_nugget", MAGNESIUM_INGOT);
    public static final ModItem CALCIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&fCalcium Nugget", "calcium_nugget", CALCIUM_INGOT);
    public static final ModItem SODIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&fSodium Nugget", "sodium_nugget", SODIUM_INGOT);
    public static final ModItem ZIRCONIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&7Zirconium Nugget", "zirconium_nugget", ZIRCONIUM_INGOT);
    public static final ModItem POTASSIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&7Potassium Nugget", "potassium_nugget", POTASSIUM_INGOT);
    public static final ModItem SILICON_NUGGET = new MetalNugget(IRON_NUGGET, "&7Silicon Nugget", "silicon_nugget", SILICON_INGOT);

    public static final ModItem COPPER_NUGGET  = new MetalNugget(GOLD_NUGGET, "&6Copper Nugget", "copper_nugget", new ItemStack(COPPER_INGOT));

    public static final ModItem SULFUR_NUGGET  = new ModItem(GOLD_NUGGET, "&6Sulfur Nugget", "sulfur_nugget");
    public static final ModItem CARBON_PIECE  = new MetalNugget(FLINT, "&0Carbon Piece", "carbon_piece", CARBON_CHUNK);

    public static final ModItem IMPURE_URANIUM_DUST = new ModItem(SUGAR, "&7Impure Uranium Dust", "impure_uranium_dust");
    public static final ModItem PURE_URANIUM_NUGGET = new ModItem(SCUTE, "&2Pure Uranium Nugget", "pure_uranium_nugget");

    public static final ModItem DATING_MACHINE = new DatingMachine();

    public static final ModItem SALT = new ModItem(SUGAR, "&fSalt", "salt");
    public static final ModItem SALTPETER = new ModItem(SUGAR, "&fSaltpeter", "saltpeter");

    public static final ModItem DRUG_POTATO = new ModItem(BAKED_POTATO, "&6Drug Potato", "drug_potato");

    public static final ModItem MAGNET = new ModItem(BRICK, "&4Magnet", "magnet");

    public static final ModItem DAMAGE_ITEM = new ModItem(NETHER_BRICK, "&4Damage Item", "damage_item");

    public static ItemStack withName(ItemStack stack, String name) {
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(name);

        stack.setItemMeta(meta);

        return stack;
    }
}
