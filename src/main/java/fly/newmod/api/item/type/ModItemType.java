package fly.newmod.api.item.type;

import fly.newmod.NewMod;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.BlockEventsListener;
import fly.newmod.api.event.ItemEventsListener;
import fly.newmod.api.item.ItemManager;
import fly.newmod.api.item.ModItemStack;
import fly.newmod.api.item.meta.DefaultModItemMeta;
import fly.newmod.api.item.meta.ModItemMeta;
import fly.newmod.api.item.texture.MetaModifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fly.newmod.api.item.texture.DefaultMetaFlags.NAME_MODIFIER;

public class ModItemType {
    private ItemEventsListener listener;

    private final List<MetaModifier<?>> modifiers = new ArrayList<>();

    protected final Material defaultMaterial;
    protected final NamespacedKey id;

    protected final Class<? extends ModItemMeta> meta;

    protected final boolean craftable;

    private ModBlockType block;

    public static ModItemType createAndRegister(Material material, Plugin plugin, String id, String name, int color) {
        ItemManager manager = NewMod.get().getItemManager();
        ModItemType item = new ModItemType(material, new NamespacedKey(plugin, id)).name(name, color);

        manager.registerItem(item);

        return item;
    }

    public ModItemType(Material defaultMaterial, NamespacedKey id) {
        this(defaultMaterial, id, DefaultModItemMeta.class);
    }

    public ModItemType(Material defaultMaterial, NamespacedKey id, Class<? extends ModItemMeta> meta) {
        this.defaultMaterial = defaultMaterial;
        this.id = id;

        this.meta = meta;

        this.craftable = false;
    }

    public ModItemType(Material defaultMaterial, NamespacedKey id, Class<? extends ModItemMeta> meta, boolean craftable) {
        this.defaultMaterial = defaultMaterial;
        this.id = id;

        this.meta = meta;

        this.craftable = craftable;
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

    public ModItemType setListener(ItemEventsListener listener) {
        this.listener = listener;

        return this;
    }

    public ModItemType setBlock(ModBlockType block) {
        this.block = block;

        block.setItem(this);

        return this;
    }

    public ModBlockType getBlock() {
        return block;
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

    public ModItemType addModifier(MetaModifier<?> modifier) {
        modifiers.add(modifier);

        return this;
    }

    public ModItemType name(String string, int color) {
        return name(string, TextColor.color(color));
    }

    public ModItemType name(String string, TextColor color) {
        return addModifier(new MetaModifier<>(Component.text(string, color).color(color), NAME_MODIFIER));
    }

    public ModItemType shapelessRecipe(int count, ItemStack... ingredients) {
        ItemStack result = new ModItemStack(this).create();

        result.setAmount(count);

        ShapelessRecipe recipe = new ShapelessRecipe(id, result);

        for(ItemStack ingredient : ingredients) {
            recipe.addIngredient(ingredient);
        }

        Bukkit.addRecipe(recipe);

        return this;
    }

    // Getter


    public boolean isCraftable() {
        return craftable;
    }
}
