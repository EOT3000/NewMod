package me.fly.newmod.armor.model;

import me.fly.newmod.api.item.ModItemType;
import org.bukkit.inventory.ItemStack;

public class ModArmorMaterial implements ArmorMaterial {
    private final ItemStack material;
    private final ModItemType<ArmorProperties> boots;
    private final ModItemType<ArmorProperties> legs;
    private final ModItemType<ArmorProperties> chest;
    private final ModItemType<ArmorProperties> head;

    public ModArmorMaterial(ItemStack material, ModItemType<ArmorProperties> boots, ModItemType<ArmorProperties> legs, ModItemType<ArmorProperties> chest, ModItemType<ArmorProperties> head) {
        this.material = material;

        this.boots = boots.register();
        this.legs = legs.register();
        this.chest = chest.register();
        this.head = head.register();
    }

    @Override
    public ItemStack material() {
        return material;
    }

    @Override
    public ModItemType<ArmorProperties> boots() {
        return boots;
    }

    @Override
    public ModItemType<ArmorProperties> legs() {
        return legs;
    }

    @Override
    public ModItemType<ArmorProperties> chest() {
        return chest;
    }

    @Override
    public ModItemType<ArmorProperties> head() {
        return head;
    }
}
