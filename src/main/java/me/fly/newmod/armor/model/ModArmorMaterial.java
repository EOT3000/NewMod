package me.fly.newmod.armor.model;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ModArmorMaterial implements ArmorMaterial {
    private final ItemStack material;
    private final ModArmorItemType boots;
    private final ModArmorItemType legs;
    private final ModArmorItemType chest;
    private final ModArmorItemType head;

    public ModArmorMaterial(ItemStack material, ModArmorItemType boots, ModArmorItemType legs, ModArmorItemType chest, ModArmorItemType head) {
        this.material = material;
        this.boots = boots;
        this.legs = legs;
        this.chest = chest;
        this.head = head;

        boots.material(this).finishAndRegister();
        legs.material(this).finishAndRegister();
        chest.material(this).finishAndRegister();
        head.material(this).finishAndRegister();
    }

    @Override
    public ItemStack material() {
        return material;
    }

    @Override
    public ArmorItemType boots() {
        return boots;
    }

    @Override
    public ArmorItemType legs() {
        return legs;
    }

    @Override
    public ArmorItemType chest() {
        return chest;
    }

    @Override
    public ArmorItemType head() {
        return head;
    }
}
