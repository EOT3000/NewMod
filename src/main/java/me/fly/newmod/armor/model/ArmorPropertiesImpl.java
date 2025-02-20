package me.fly.newmod.armor.model;

import me.fly.newmod.api.item.VanillaOrModItem;
import me.fly.newmod.api.item.properties.ItemProperties;

public class ArmorPropertiesImpl implements ItemProperties, ArmorProperties {
    private ArmorMaterial material;

    private int dura;
    private int defense;
    private int toughness;

    private final ArmorSection section;

    public ArmorPropertiesImpl(ArmorMaterial material, ArmorSection section, int dura, int defense, int toughness) {
        this.material = material;

        this.dura = dura;
        this.defense = defense;
        this.toughness = toughness;

        this.section = section;
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

    public void setDurability(int dura) {
        this.dura = dura;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }
}
