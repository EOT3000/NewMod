package me.fly.newmod.armor.model;

import org.bukkit.inventory.ItemStack;

public interface ArmorMaterial {
    ItemStack material();

    ArmorItemType boots();

    ArmorItemType legs();

    ArmorItemType chest();

    ArmorItemType head();

    default ArmorItemType item(ArmorSection section) {
        if (section == null) return null;

        return switch (section) {
            case BOOTS -> boots();
            case LEGS -> legs();
            case CHEST -> chest();
            case HEAD -> head();
        };
    }
}
