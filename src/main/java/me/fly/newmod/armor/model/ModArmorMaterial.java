package me.fly.newmod.armor.model;

import me.fly.newmod.api.item.builders.ModArmorItemTypeBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ModArmorMaterial implements ArmorMaterial {
    private final ItemStack material;
    private final ModArmorItemType boots;
    private final ModArmorItemType legs;
    private final ModArmorItemType chest;
    private final ModArmorItemType head;

    public ModArmorMaterial(ItemStack material, ModArmorItemTypeBuilder boots, ModArmorItemTypeBuilder legs, ModArmorItemTypeBuilder chest, ModArmorItemTypeBuilder head) {
        this.material = material;

        this.boots = boots.material(this).build().register();
        this.legs = legs.material(this).build().register();
        this.chest = chest.material(this).build().register();
        this.head = head.material(this).build().register();
    }

    @Override
    public ItemStack material() {
        return material;
    }

    @Override
    public ModArmorItemType boots() {
        return boots;
    }

    @Override
    public ModArmorItemType legs() {
        return legs;
    }

    @Override
    public ModArmorItemType chest() {
        return chest;
    }

    @Override
    public ModArmorItemType head() {
        return head;
    }
}
