package me.fly.newmod.armor.model;

import org.bukkit.inventory.ItemStack;

public interface ArmorMaterial {
    ItemStack material();

    ArmorProperties boots();

    ArmorProperties legs();

    ArmorProperties chest();

    ArmorProperties head();

    default ArmorProperties item(ArmorSection section) {
        if (section == null) return null;

        return switch (section) {
            case BOOTS -> boots();
            case LEGS -> legs();
            case CHEST -> chest();
            case HEAD -> head();
        };
    }
}
