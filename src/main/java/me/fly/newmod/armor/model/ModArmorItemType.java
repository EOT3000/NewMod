package me.fly.newmod.armor.model;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DamageableModItemMeta;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.texture.DefaultMetaFlags;
import me.fly.newmod.api.item.texture.MetaModifier;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.List;

public class ModArmorItemType extends ModItemType implements ArmorItemType {
    private ModArmorMaterial material;

    private int dura;
    private int defense;
    private int toughness;

    private final ArmorSection section;

    public ModArmorItemType(ModArmorMaterial material, ArmorSection section, int dura, int defense, int toughness,
                            Material defaultMaterial, NamespacedKey id, Class<? extends ModItemMeta> meta, boolean craftable, List<MetaModifier<?>> modifiers,
                            ModBlockType block, ItemEventsListener listener, Component customName) {
        super(defaultMaterial, id, meta, craftable, modifiers, block, listener, customName);

        this.material = material;

        this.dura = dura;
        this.defense = defense;
        this.toughness = toughness;

        this.section = section;
    }

    @Override
    public Material get() {
        return this.defaultMaterial;
    }

    @Override
    public ArmorSection getSection() {
        return section;
    }

    @Override
    public ArmorMaterial getMaterial() {
        return material;
    }

    @Override
    public int getToughness() {
        return toughness;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getDurability() {
        return dura;
    }

    public ModArmorItemType register() {
        NewMod.get().getItemManager().registerItem(this);

        return this;
    }
}
