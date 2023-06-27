package me.fly.newmod.armor.model;

import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DamageableModItemMeta;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.texture.DefaultMetaFlags;
import me.fly.newmod.api.item.texture.MetaModifier;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class ModArmorItemType extends ModItemType implements ArmorItemType {
    private ModArmorMaterial material;

    private int dura;
    private int defense;
    private int toughness;

    private final ArmorSection section;

    public ModArmorItemType(ArmorSection section, Material defaultMaterial, NamespacedKey id) {
        super(defaultMaterial, id);

        this.section = section;
    }

    public ModArmorItemType(ArmorSection section, Material defaultMaterial, NamespacedKey id, boolean craftable) {
        super(defaultMaterial, id, craftable);

        this.section = section;
    }

    public ModArmorItemType(ArmorSection section, Material defaultMaterial, NamespacedKey id, Class<? extends ModItemMeta> meta) {
        super(defaultMaterial, id, meta);

        this.section = section;
    }

    public ModArmorItemType(ArmorSection section, Material defaultMaterial, NamespacedKey id, Class<? extends ModItemMeta> meta, boolean craftable) {
        super(defaultMaterial, id, meta, craftable);

        this.section = section;
    }

    public ModArmorItemType data(int dura, int defense, int toughness) {
        if(finished) {
            throw new IllegalStateException("Cannot modify a final Item");
        }

        this.dura = dura;
        this.defense = defense;
        this.toughness = toughness;

        if(dura < 0) {
            addModifier(new MetaModifier<>(true, DefaultMetaFlags.UNBREAKABLE_MODIFIER));
        }

        return this;
    }

    public ModArmorItemType material(ModArmorMaterial material) {
        this.material = material;

        return this;
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
}
