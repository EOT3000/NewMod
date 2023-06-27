package me.fly.newmod.api.item.builders;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.texture.DefaultMetaFlags;
import me.fly.newmod.api.item.texture.MetaModifier;
import me.fly.newmod.api.util.Pair;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ModItemTypeBuilder {
    private Material defaultMaterial;
    private NamespacedKey id;

    private ModBlockType block = null;

    private ItemEventsListener listener = new ItemEventsListener() {};
    private Class<? extends ModItemMeta> meta = DefaultModItemMeta.class;
    private boolean craftable = false;

    private List<MetaModifier<?>> modifiers = new ArrayList<>();

    public ModItemTypeBuilder(Material m, String id, JavaPlugin plugin) {
        this.defaultMaterial = m;
        this.id = new NamespacedKey(plugin, id);
    }

    public ModItemTypeBuilder customName(Component component) {
        addModifier(new MetaModifier<>(component, DefaultMetaFlags.NAME_MODIFIER));

        return this;
    }

    public ModItemTypeBuilder enchantment(Enchantment enchantment, int lvl) {
        addModifier(new MetaModifier<>(new Pair<>(enchantment, lvl), DefaultMetaFlags.ENCHANTMENT_MODIFIER));

        return this;
    }

    public ModItemTypeBuilder addModifier(MetaModifier<?> modifier) {
        modifiers.add(modifier);

        return this;
    }

    public ModItemTypeBuilder craftable(boolean craftable) {
        this.craftable = craftable;

        return this;
    }

    public ModItemTypeBuilder meta(Class<? extends ModItemMeta> meta) {
        this.meta = meta;

        return this;
    }

    public ModItemTypeBuilder listener(ItemEventsListener listener) {
        this.listener = listener;

        return this;
    }

    public ModItemTypeBuilder block(ModBlockType block) {
        this.block = block;

        return this;
    }

    public ModItemType build() {
        return new ModItemType(defaultMaterial, id, meta, craftable, modifiers, block, listener);
    }
}
