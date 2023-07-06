package me.fly.newmod.technology;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.technology.data.EnergyHolderBlockDataImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class EnergyManagerItemType extends ModItemType {
    public EnergyManagerItemType(Material material, int capacity, String id, String name, int color) {
        super(material, new NamespacedKey("", id), DefaultModItemMeta.class, false,
                new EnergyManagerBlockType(material, capacity, null, id), new ItemEventsListener() {}, Component.text(name).color(TextColor.color(color)));
    }

    public EnergyManagerItemType(Material material, int capacity, JavaPlugin plugin, String id, String name, int color) {
        super(material, new NamespacedKey(plugin, id), DefaultModItemMeta.class, false,
                new EnergyManagerBlockType(material, capacity, plugin, id), new ItemEventsListener() {}, Component.text(name).color(TextColor.color(color)));
    }

    public static class EnergyManagerBlockType extends ModBlockType implements EnergyComponent {
        private final int capacity;

        public EnergyManagerBlockType(Material material, int capacity, JavaPlugin plugin, String id) {
            super(material, new NamespacedKey(plugin, id), EnergyHolderBlockDataImpl.class);

            this.capacity = capacity;
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.MANAGER;
        }

        @Override
        public int getCapacity() {
            return capacity;
        }
    }
}
