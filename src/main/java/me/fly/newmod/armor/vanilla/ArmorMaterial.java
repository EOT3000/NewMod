package me.fly.newmod.armor.vanilla;

import org.bukkit.inventory.ItemStack;

public interface ArmorMaterial {

    ItemStack material();

    ItemStack feet();

    ItemStack legs();

    ItemStack chest();

    ItemStack head();

    default ItemStack item(ArmorSection section) {
        if (section == null) return null;

        return switch (section) {
            case FEET -> feet();
            case LEGS -> legs();
            case CHEST -> chest();
            case HEAD -> head();
        };
    }
}
