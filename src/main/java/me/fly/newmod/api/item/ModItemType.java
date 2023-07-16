package me.fly.newmod.api.item;

import com.google.common.collect.Lists;
import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.properties.DefaultModItemProperties;
import me.fly.newmod.api.item.properties.ItemProperties;
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

public class ModItemType<T extends ItemProperties> implements ItemWithProperties<T>, VanillaOrModItem {
    private ItemEventsListener listener;

    private final List<MetaModifier<?>> modifiers = new ArrayList<>();

    protected final Material defaultMaterial;
    protected final NamespacedKey id;

    protected final Class<? extends ModItemMeta> meta;

    private final ModBlockType block;

    private final Component customName;

    private final T properties;

    public ModItemType(Material defaultMaterial, NamespacedKey id, Class<? extends ModItemMeta> meta, T properties,
                       List<MetaModifier<?>> modifiers, ModBlockType block, ItemEventsListener listener, Component customName) {
        this.defaultMaterial = defaultMaterial;
        this.id = id;

        this.meta = meta;

        this.properties = properties;

        this.modifiers.addAll(modifiers);
        this.block = block;

        this.listener = listener;

        this.customName = customName;
    }

    public ModItemType(Material defaultMaterial, NamespacedKey id, Class<? extends ModItemMeta> meta, T properties,
                       ModBlockType block, ItemEventsListener listener, Component customName) {
        this(defaultMaterial, id, meta, properties, Lists.newArrayList(new MetaModifier<>(customName, NAME_MODIFIER)), block, listener, customName);
    }

    @Override
    public VanillaOrModItem get() {
        return this;
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

    @Override
    public T getProperties() {
        return properties;
    }

    @Override
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

    public ModItemType<T> shapelessRecipe(int count, ItemStack... ingredients) {
        ItemStack result = new ModItemStack(this).create();

        result.setAmount(count);

        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(getId().getNamespace(), getId().getKey() + "_shapeless"), result);

        if(ingredients.length > 9) {
            System.err.println("Error on adding recipe, more than 9 items defined " + getId());
        }

        for(ItemStack stack : ingredients) {
            recipe.addIngredient(stack);
        }

        Bukkit.addRecipe(recipe);

        return this;
    }

    public ModItemType<T> furnaceRecipe(ItemStack source, float xp, int time) {
        FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(getId().getNamespace(), getId().getKey() + "_furnace"), create(), new RecipeChoice.ExactChoice(source), xp, time);

        Bukkit.addRecipe(recipe);

        return this;
    }

    public ModItemType<T> smokerRecipe(ItemStack source, float xp, int time) {
        SmokingRecipe recipe = new SmokingRecipe(new NamespacedKey(getId().getNamespace(), getId().getKey() + "_smoking"), create(), new RecipeChoice.ExactChoice(source), xp, time);

        Bukkit.addRecipe(recipe);

        return this;
    }

    public ModItemType<T> blastingRecipe(ItemStack source, float xp, int time) {
        BlastingRecipe recipe = new BlastingRecipe(new NamespacedKey(getId().getNamespace(), getId().getKey() + "_blasting"), create(), new RecipeChoice.ExactChoice(source), xp, time);

        Bukkit.addRecipe(recipe);

        return this;
    }

    // Getter

    public ModItemType<T> register() {
        NewMod.get().getItemManager().registerItem(this);

        if(block != null) {
            NewMod.get().getBlockManager().registerBlock(block);
        }

        return this;
    }
}
