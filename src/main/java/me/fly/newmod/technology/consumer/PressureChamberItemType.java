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
    public PressureChamberItemType() {
        super(Material.IRON_BLOCK, new NamespacedKey("", "pressure_chamber"), DefaultModItemMeta.class, false,
                new PressureChamberBlockType(), new ItemEventsListener() {}, Component.text("Pressure Chamber").color(TextColor.color(0xEFEFB8)));
    }

    public static class PressureChamberBlockType extends ModBlockType implements EnergyComponent {
        public PressureChamberBlockType() {
            super(Material.IRON_BLOCK, new NamespacedKey("", "pressure_chamber"), PressureChamberBlockData.class);
        }

        @Override
        public EnergyComponentType getType() {
            return null;
        }

        @Override
        public int getCapacity() {
            return 3400;
        }
    }
}
