package me.fly.newmod.technology.consumer;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.technology.EnergyComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class PressureChamberItemType extends ModItemType {
    public PressureChamberItemType(Material material, int capacity, String id, String name, int color) {
        super(material, new NamespacedKey("", id), DefaultModItemMeta.class, false,
                new PressureChamberBlockType(), new ItemEventsListener() {}, Component.text(name).color(TextColor.color(color)));
    }

    public static class PressureChamberBlockType extends ModBlockType implements EnergyComponent {
        public PressureChamberBlockType() {
            super(Material.IRON_BLOCK, new NamespacedKey("", "pressure_chamber"));
        }

        @Override
        public EnergyComponentType getType() {
            return null;
        }

        @Override
        public int getCapacity() {
            return 0;
        }
    }
}
