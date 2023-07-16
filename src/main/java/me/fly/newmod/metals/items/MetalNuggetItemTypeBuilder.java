package me.fly.newmod.metals.items;


import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemStack;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.VanillaOrModItem;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.texture.DefaultMetaFlags;
import me.fly.newmod.api.item.texture.MetaModifier;
import me.fly.newmod.api.util.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MetalNuggetItemTypeBuilder {
    private final Material defaultMaterial;
    private final NamespacedKey id;

    private ItemEventsListener listener = new ItemEventsListener() {};
    private Class<? extends ModItemMeta> meta = DefaultModItemMeta.class;
    private boolean craftable = false;

    private VanillaOrModItem metal;

    private Component customName;

    private final List<MetaModifier<?>> modifiers = new ArrayList<>();

    public MetalNuggetItemTypeBuilder(Material m, String id, JavaPlugin plugin, Material material) {
        this.defaultMaterial = m;
        this.id = new NamespacedKey(plugin, id);

        this.metal = new VanillaOrModItem(material);
    }

    public MetalNuggetItemTypeBuilder(Material m, String id, JavaPlugin plugin, ModItemType<?> material) {
        this.defaultMaterial = m;
        this.id = new NamespacedKey(plugin, id);

        this.metal = material;
    }

    public MetalNuggetItemTypeBuilder customName(String s, TextColor c) {
        addModifier(new MetaModifier<>(Component.text(s, c), DefaultMetaFlags.NAME_MODIFIER));

        return this;
    }

    public MetalNuggetItemTypeBuilder customName(String s, int c) {
        addModifier(new MetaModifier<>(Component.text(s, TextColor.color(c)), DefaultMetaFlags.NAME_MODIFIER));

        return this;
    }

    public MetalNuggetItemTypeBuilder enchantment(Enchantment enchantment, int lvl) {
        addModifier(new MetaModifier<>(new Pair<>(enchantment, lvl), DefaultMetaFlags.ENCHANTMENT_MODIFIER));

        return this;
    }

    public MetalNuggetItemTypeBuilder addModifier(MetaModifier<?> modifier) {
        if(modifier.getFlag().equals(DefaultMetaFlags.NAME_MODIFIER)) {
            customName = (Component) modifier.getInformation();
        }

        modifiers.add(modifier);

        return this;
    }

    public MetalNuggetItemTypeBuilder craftable(boolean craftable) {
        this.craftable = craftable;

        return this;
    }

    public MetalNuggetItemTypeBuilder meta(Class<? extends ModItemMeta> meta) {
        this.meta = meta;

        return this;
    }

    public MetalNuggetItemTypeBuilder listener(ItemEventsListener listener) {
        this.listener = listener;

        return this;
    }

    public MetalNuggetItemType build() {
        return new MetalNuggetItemType(defaultMaterial, id, meta, craftable, modifiers, listener, customName, metal);
    }

    public MetalNuggetItemType buildAndRegister() {
        return new MetalNuggetItemType(defaultMaterial, id, meta, craftable, modifiers, listener, customName, metal).register();
    }
}

