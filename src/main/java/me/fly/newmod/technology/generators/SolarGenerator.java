package me.fly.newmod.technology.generators;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.technology.EnergyComponent;
import me.fly.newmod.technology.data.EnergyHolderBlockDataImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class SolarGenerator extends ModItemType {
    public SolarGenerator() {
        super(Material.DAYLIGHT_DETECTOR, new NamespacedKey("", "solar_generator"), DefaultModItemMeta.class, false,
                new SolarGeneratorBlock(), new ItemEventsListener() {}, Component.text("Solar Generator").color(TextColor.color(0x806040)));

        throw new NullPointerException();
    }

    public static class SolarGeneratorBlock extends ModBlockType implements EnergyComponent {
        public SolarGeneratorBlock() {
            super(Material.DAYLIGHT_DETECTOR, new NamespacedKey("", "solar_generator"), EnergyHolderBlockDataImpl.class);
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.PRODUCER;
        }

        @Override
        public int getCapacity() {
            return 0;
        }
    }
}
