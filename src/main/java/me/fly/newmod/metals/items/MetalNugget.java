package me.fly.newmod.metals.items;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemStack;
import me.fly.newmod.api.item.ModItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public class MetalNugget extends ModItemType {
    private ItemStack metal;

    public static MetalNugget createAndRegister(Material material, Plugin plugin, String id, String name, int color, ItemStack metal) {
        ItemManager manager = NewMod.get().getItemManager();
        MetalNugget item = (MetalNugget) new MetalNugget(material, new NamespacedKey(plugin, id), metal).name(name, color);

        manager.registerItem(item);

        item.addRecipes();

        return item;
    }

    public static MetalNugget createAndRegister(Material material, Plugin plugin, String id, String name, int color, ModItemType metal) {
        ItemManager manager = NewMod.get().getItemManager();
        MetalNugget item = (MetalNugget) new MetalNugget(material, new NamespacedKey(plugin, id), metal).name(name, color);

        manager.registerItem(item);

        item.addRecipes();

        return item;
    }

    public MetalNugget(Material material, NamespacedKey id, ItemStack metal) {
        super(material, id);

        this.metal = metal;
    }

    private void addRecipes() {
        ItemStack nugget = new ModItemStack(this).create();

        nugget.setAmount(9);

        ShapelessRecipe recipe = new ShapelessRecipe(id, nugget);

        recipe.addIngredient(metal);

        Bukkit.addRecipe(recipe);


        @SuppressWarnings("deprecation")
        ShapelessRecipe recipeReverse = new ShapelessRecipe(new NamespacedKey(id.getNamespace(), id.getKey() + "_reverse"), metal);

        recipeReverse.addIngredient(9, new ModItemStack(this).create());

        Bukkit.addRecipe(recipeReverse);
    }

    public MetalNugget(Material material, NamespacedKey id, ModItemType metal) {
        this(material, id, new ModItemStack(metal).create());
    }

    public ItemStack getMetal() {
        return metal;
    }
}
