package me.fly.newmod.api.item.builders;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.armor.model.ArmorMaterial;
import me.fly.newmod.armor.model.ArmorSection;
import me.fly.newmod.armor.model.ModArmorItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.texture.DefaultMetaFlags;
import me.fly.newmod.api.item.texture.MetaModifier;
import me.fly.newmod.api.util.Pair;
import me.fly.newmod.armor.model.ModArmorMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ModArmorItemTypeBuilder {
    private final Material defaultMaterial;
    private final NamespacedKey id;

    private ModBlockType block = null;

    private ItemEventsListener listener = new ItemEventsListener() {};
    private Class<? extends ModItemMeta> meta = DefaultModItemMeta.class;
    private boolean craftable = false;

    private Component customName;

    private ModArmorMaterial material;

    private int dura;
    private int defense;
    private int toughness;

    private final ArmorSection section;

    private final List<MetaModifier<?>> modifiers = new ArrayList<>();

    public ModArmorItemTypeBuilder(Material m, String id, JavaPlugin plugin, ArmorSection section) {
        this.defaultMaterial = m;
        this.id = new NamespacedKey(plugin, id);

        this.section = section;
    }

    public ModArmorItemTypeBuilder customName(String s, TextColor c) {
        addModifier(new MetaModifier<>(Component.text(s, c), DefaultMetaFlags.NAME_MODIFIER));

        return this;
    }

    public ModArmorItemTypeBuilder customName(String s, int c) {
        addModifier(new MetaModifier<>(Component.text(s, TextColor.color(c)), DefaultMetaFlags.NAME_MODIFIER));

        return this;
    }

    public ModArmorItemTypeBuilder enchantment(Enchantment enchantment, int lvl) {
        addModifier(new MetaModifier<>(new Pair<>(enchantment, lvl), DefaultMetaFlags.ENCHANTMENT_MODIFIER));

        return this;
    }

    public ModArmorItemTypeBuilder unbreakable(boolean ub) {
        addModifier(new MetaModifier<>(ub, DefaultMetaFlags.UNBREAKABLE_MODIFIER));

        return this;
    }

    public ModArmorItemTypeBuilder addModifier(MetaModifier<?> modifier) {
        if(modifier.getFlag().equals(DefaultMetaFlags.NAME_MODIFIER)) {
            customName = (Component) modifier.getInformation();
        }

        modifiers.add(modifier);

        return this;
    }

    public ModArmorItemTypeBuilder craftable(boolean craftable) {
        this.craftable = craftable;

        return this;
    }

    public ModArmorItemTypeBuilder meta(Class<? extends ModItemMeta> meta) {
        this.meta = meta;

        return this;
    }

    public ModArmorItemTypeBuilder listener(ItemEventsListener listener) {
        this.listener = listener;

        return this;
    }

    public ModArmorItemTypeBuilder block(ModBlockType block) {
        this.block = block;

        return this;
    }

    public ModArmorItemTypeBuilder material(ModArmorMaterial material) {
        this.material = material;

        return this;
    }

    public ModArmorItemTypeBuilder durability(int dura) {
        this.dura = dura;

        return this;
    }

    public ModArmorItemTypeBuilder defense(int defense) {
        this.defense = defense;

        return this;
    }

    public ModArmorItemTypeBuilder toughness(int toughness) {
        this.toughness = toughness;

        return this;
    }

    public ModArmorItemType build() {
        return new ModArmorItemType(material, section, dura, defense, toughness, defaultMaterial, id, meta, craftable, modifiers, block, listener, customName);
    }
}
