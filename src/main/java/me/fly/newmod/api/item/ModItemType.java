package me.fly.newmod.api.item;

import com.google.common.collect.Lists;
import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.texture.DefaultMetaFlags;
import me.fly.newmod.api.item.texture.MetaModifier;
import me.fly.newmod.api.util.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

import static me.fly.newmod.api.item.texture.DefaultMetaFlags.ENCHANTMENT_MODIFIER;
import static me.fly.newmod.api.item.texture.DefaultMetaFlags.NAME_MODIFIER;

public class ModItemType {
    private ItemEventsListener listener;

    private final List<MetaModifier<?>> modifiers = new ArrayList<>();

    protected final Material defaultMaterial;
    protected final NamespacedKey id;

    protected final Class<? extends ModItemMeta> meta;

    protected final boolean craftable;

    private ModBlockType block;

    private Component customName;

    private final List<NamespacedKey> allowedRecipeReplacement = new ArrayList<>();

    public ModItemType(Material defaultMaterial, NamespacedKey id, Class<? extends ModItemMeta> meta, boolean craftable,
                       List<MetaModifier<?>> modifiers, ModBlockType block, ItemEventsListener listener, Component customName) {
        this.defaultMaterial = defaultMaterial;
        this.id = id;

        this.meta = meta;

        this.craftable = craftable;

        this.modifiers.addAll(modifiers);
        this.block = block;

        this.listener = listener;

        this.customName = customName;
    }

    public ModItemType(Material defaultMaterial, NamespacedKey id, Class<? extends ModItemMeta> meta, boolean craftable,
                       ModBlockType block, ItemEventsListener listener, Component customName) {
        this(defaultMaterial, id, meta, craftable, Lists.newArrayList(new MetaModifier<>(customName, NAME_MODIFIER)), block, listener, customName);
    }

    public final Material getDefaultMaterial() {
        return defaultMaterial;
    }

    public final NamespacedKey getId() {
        return id;
    }

    public Class<? extends ModItemMeta> getMetaType() {
        return meta;
    }

    public ItemEventsListener getListener() {
        return listener;
    }

    public ModBlockType getBlock() {
        return block;
    }

    public Component getCustomName() {
        return customName;
    }

    public ItemStack create() {
        return new ModItemStack(this).create();
    }

    //

    public ItemStack applyModifiers(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();

        for(MetaModifier<?> modifier : modifiers) {
            modifier.apply(meta);
        }

        stack.setItemMeta(meta);

        return stack;
    }

    // Type initiation

    public ModItemType addReplaceableRecipe(NamespacedKey recipe) {
        allowedRecipeReplacement.add(recipe);

        return this;
    }

    public boolean isReplaceableRecipe(NamespacedKey key) {
        return allowedRecipeReplacement.contains(key);
    }

    public ModItemType shapelessRecipe(int count, ItemStack... ingredients) {
        ItemStack result = new ModItemStack(this).create();

        result.setAmount(count);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getId().getNamespace(), getId().getKey() + "_furnace"), result);

        if(ingredients.length > 9) {
            System.err.println("Error on adding recipe, more than 9 items defined " + getId());
        }

        StringBuilder shape = new StringBuilder("         ");

        for(int i = 0; i < ingredients.length; i++) {
            shape.setCharAt(i, (char) (65+i));
        }

        recipe.shape(shape.substring(0,3), shape.substring(3,6), shape.substring(6,9));

        for(int i = 0; i < ingredients.length; i++) {
            System.out.println((char) (65+i));

            recipe.setIngredient((char) (65+i), ingredients[i]);
        }

        Bukkit.addRecipe(recipe);

        return this;
    }

    public ModItemType furnaceRecipe(ItemStack source, float xp, int time) {
        FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(getId().getNamespace(), getId().getKey() + "_furnace"), create(), new RecipeChoice.ExactChoice(source), xp, time);

        Bukkit.addRecipe(recipe);

        return this;
    }

    public ModItemType smokerRecipe(ItemStack source, float xp, int time) {
        SmokingRecipe recipe = new SmokingRecipe(new NamespacedKey(getId().getNamespace(), getId().getKey() + "_smoking"), create(), new RecipeChoice.ExactChoice(source), xp, time);

        Bukkit.addRecipe(recipe);

        return this;
    }

    public ModItemType blastingRecipe(ItemStack source, float xp, int time) {
        BlastingRecipe recipe = new BlastingRecipe(new NamespacedKey(getId().getNamespace(), getId().getKey() + "_blasting"), create(), new RecipeChoice.ExactChoice(source), xp, time);

        Bukkit.addRecipe(recipe);

        return this;
    }

    // Getter


    public boolean isCraftable() {
        return craftable;
    }

    public ModItemType register() {
        NewMod.get().getItemManager().registerItem(this);

        if(block != null) {
            NewMod.get().getBlockManager().registerBlock(block);
        }

        return this;
    }
}
