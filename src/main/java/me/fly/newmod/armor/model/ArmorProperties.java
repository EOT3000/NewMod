package me.fly.newmod.armor.model;

import me.fly.newmod.api.item.VanillaOrModItem;
import me.fly.newmod.api.item.properties.ModItemProperties;
import me.fly.newmod.armor.damage.DamageType;
import me.fly.newmod.armor.damage.DefaultDamageType;
import org.bukkit.Material;

public interface ArmorProperties extends ModItemProperties {
    ArmorSection getSection();
    ArmorMaterial getMaterial();

    int getToughness();
    int getDefense();
    int getDurability();

    default int getDefenseBoost(DamageType type) {
        if(type instanceof DefaultDamageType) {
            return switch ((DefaultDamageType) type) {
                case SONIC_BOOM, MAGIC, STARVATION -> -getDefense();

                default -> 0;
            };
        }

        return 0;
    }

    default int getToughnessBoost(DamageType type) {
        if(type instanceof DefaultDamageType) {
            return switch ((DefaultDamageType) type) {
                case SONIC_BOOM, MAGIC, STARVATION -> -getToughness();

                default -> 0;
            };
        }

        return 0;
    }

    default void setDefenseBoost(DamageType type, int boost) {}
    default void setToughnessBoost(DamageType type, int boost) {}
}
