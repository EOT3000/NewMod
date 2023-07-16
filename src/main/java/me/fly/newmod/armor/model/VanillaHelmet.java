package me.fly.newmod.armor.model;

import me.fly.newmod.api.item.VanillaOrModItem;
import org.bukkit.Material;

public enum VanillaHelmet implements ArmorProperties {
    TURTLE_HELMET(Material.TURTLE_HELMET, 2);

    private final Material material;

    private final int defense;


    VanillaHelmet(Material material, int defense) {
        this.material = material;
        this.defense = defense;
    }


    @Override
    public VanillaOrModItem get() {
        return new VanillaOrModItem(material);
    }

    @Override
    public ArmorSection getSection() {
        return ArmorSection.HEAD;
    }

    @Override
    public ArmorMaterial getMaterial() {
        return null;
    }

    @Override
    public int getToughness() {
        return 0;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getDurability() {
        return 0;
    }
}
