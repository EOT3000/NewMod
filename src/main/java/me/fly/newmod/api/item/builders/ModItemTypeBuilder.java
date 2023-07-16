package me.fly.newmod.api.item.builders;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.properties.DefaultModItemProperties;
import me.fly.newmod.api.item.properties.ModItemProperties;
import me.fly.newmod.api.item.texture.DefaultMetaFlags;
import me.fly.newmod.api.item.texture.MetaModifier;
import me.fly.newmod.api.util.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ModItemTypeBuilder {
    private final Material defaultMaterial;
    private final NamespacedKey id;

    private ModBlockType block = null;

    private ItemEventsListener listener = new ItemEventsListener() {};
    private Class<? extends ModItemMeta> meta = DefaultModItemMeta.class;
    private ModItemProperties properties = new DefaultModItemProperties();

    private Component customName;

    private final List<MetaModifier<?>> modifiers = new ArrayList<>();

    public ModItemTypeBuilder(Material m, String id, JavaPlugin plugin) {
        this.defaultMaterial = m;
        this.id = new NamespacedKey(plugin, id);
    }

    public ModItemTypeBuilder customName(String s, TextColor c) {
        addModifier(new MetaModifier<>(Component.text(s, c), DefaultMetaFlags.NAME_MODIFIER));

        return this;
    }

    public ModItemTypeBuilder customName(String s, int c) {
        addModifier(new MetaModifier<>(Component.text(s, TextColor.color(c)), DefaultMetaFlags.NAME_MODIFIER));

        return this;
    }

    public ModItemTypeBuilder enchantment(Enchantment enchantment, int lvl) {
        addModifier(new MetaModifier<>(new Pair<>(enchantment, lvl), DefaultMetaFlags.ENCHANTMENT_MODIFIER));

        return this;
    }

    public ModItemTypeBuilder addModifier(MetaModifier<?> modifier) {
        if(modifier.getFlag().equals(DefaultMetaFlags.NAME_MODIFIER)) {
            customName = (Component) modifier.getInformation();
        }

        modifiers.add(modifier);

        return this;
    }

    public ModItemTypeBuilder properties(ModItemProperties properties) {
        this.properties = properties;

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
        return new ModItemType(defaultMaterial, id, meta, properties, modifiers, block, listener, customName);
    }

    public ModItemType buildAndRegister() {
        return new ModItemType(defaultMaterial, id, meta, properties, modifiers, block, listener, customName).register();
    }
}
