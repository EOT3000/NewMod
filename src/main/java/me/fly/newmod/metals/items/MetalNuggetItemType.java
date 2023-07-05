package me.fly.newmod.metals.items;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemStack;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.VanillaOrModItem;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.texture.MetaModifier;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.List;

public class MetalNuggetItemType extends ModItemType {
    private final VanillaOrModItem metal;

    public MetalNuggetItemType(Material defaultMaterial, NamespacedKey id, Class<? extends ModItemMeta> meta, boolean craftable,
                               List<MetaModifier<?>> modifiers, ItemEventsListener listener, Component customName, VanillaOrModItem metal) {
        super(defaultMaterial, id, meta, craftable, modifiers, null, listener, customName);

        this.metal = metal;
    }

    private void addRecipes() {
        ItemStack nugget = new ModItemStack(this).create();

        nugget.setAmount(9);

        ShapelessRecipe recipe = new ShapelessRecipe(id, nugget);

        recipe.addIngredient(metal.create());

        Bukkit.addRecipe(recipe);


        ShapelessRecipe recipeReverse = new ShapelessRecipe(new NamespacedKey(id.getNamespace(), id.getKey() + "_reverse"), metal.create());

        recipeReverse.addIngredient(9, new ModItemStack(this).create());

        Bukkit.addRecipe(recipeReverse);
    }

    public VanillaOrModItem getMetal() {
        return metal;
    }

    @Override
    public MetalNuggetItemType register() {
        NewMod.get().getItemManager().registerItem(this);

        return this;
    }
}
