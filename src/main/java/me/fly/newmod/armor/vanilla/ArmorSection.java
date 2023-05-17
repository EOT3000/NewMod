package me.fly.newmod.armor.vanilla;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public enum ArmorSection {
    HEAD(1.4, EntityEquipment::getHelmet),
    CHEST(1.2, EntityEquipment::getChestplate),
    LEGS(0.8, EntityEquipment::getLeggings),
    FEET(0.6, EntityEquipment::getBoots);

    public final double modifier;
    private final Function<EntityEquipment, ItemStack> function;

    ArmorSection(double modifier, Function<EntityEquipment, ItemStack> function) {
        this.modifier = modifier;
        this.function = function;
    }

    public ItemStack getItem(LivingEntity entity) {
        return function.apply(entity.getEquipment());
    }


}
