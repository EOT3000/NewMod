package me.fly.newmod.technology.consumer;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.events.block.ModBlockTickEvent;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.technology.EnergyComponent;
import me.fly.newmod.technology.TechnologyPlugin;
import me.fly.newmod.technology.link.SolarLinkBlockType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Furnace;
import org.bukkit.plugin.java.JavaPlugin;

public class FastFurnaceItemType extends ModItemType {
    public FastFurnaceItemType(Material material, int maxSpeed, int maxUsage, int capacity, String id, String name, int color) {
        super(material, new NamespacedKey(TechnologyPlugin.get(), id), DefaultModItemMeta.class, false,
                new SolarLinkBlockType.SolarLinkBlockType(material, capacity, null, id), new ItemEventsListener() {}, Component.text(name).color(TextColor.color(color)));
    }

    public FastFurnaceItemType(Material material, int maxSpeed, int maxUsage, int capacity, JavaPlugin plugin, String id, String name, int color) {
        super(material, new NamespacedKey(plugin, id), DefaultModItemMeta.class, false,
                new SolarLinkBlockType.SolarLinkBlockType(material, capacity, plugin, id), new ItemEventsListener() {}, Component.text(name).color(TextColor.color(color)));
    }

    public static class FastFurnaceBlockType extends ModBlockType implements EnergyComponent {
        private final int maxSpeed, maxUsage, capacity;

        public FastFurnaceBlockType(Material material, int maxSpeed, int maxUsage, int capacity, NamespacedKey id) {
            super(material, id);

            this.maxSpeed = maxSpeed;
            this.maxUsage = maxUsage;
            this.capacity = capacity;
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.CONSUMER;
        }

        @Override
        public int getCapacity() {
            return capacity;
        }

        boolean tick(ModBlockTickEvent event) {
            Furnace furnace = (Furnace) event.getBlock().getState();

            if (furnace.getBurnTime() <= 0) {
                return false;
            }

            furnace.setCookSpeedMultiplier(1);

            furnace.update();

            return true;
        }


    }
}
