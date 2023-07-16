package me.fly.newmod.armor.model;

import me.fly.newmod.api.item.ItemWithProperties;
import me.fly.newmod.api.item.ModItemType;
import org.bukkit.inventory.ItemStack;

public interface ArmorMaterial {
    ItemStack material();

    ItemWithProperties<ArmorProperties> boots();

    ItemWithProperties<ArmorProperties> legs();

    ItemWithProperties<ArmorProperties> chest();

    ItemWithProperties<ArmorProperties> head();

    default ItemWithProperties<ArmorProperties> item(ArmorSection section) {
        if (section == null) return null;

        return switch (section) {
            case BOOTS -> boots();
            case LEGS -> legs();
            case CHEST -> chest();
            case HEAD -> head();
        };
    }
}
