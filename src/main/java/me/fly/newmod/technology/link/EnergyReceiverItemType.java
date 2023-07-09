package me.fly.newmod.technology.link;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.technology.EnergyComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class EnergyReceiverItemType extends ModItemType {
    public EnergyReceiverItemType() {
        super(Material.TARGET, new NamespacedKey("", "energy_receiver"), DefaultModItemMeta.class, false,
                new EnergyReceiverBlockType(), new ItemEventsListener() {}, Component.text("Energy Receiver").color(TextColor.color(0xFFF8FA)));

        throw new NullPointerException();
    }

    public static class EnergyReceiverBlockType extends ModBlockType implements EnergyComponent {
        public EnergyReceiverBlockType() {
            super(Material.TARGET, new NamespacedKey("", "energy_receiver"));
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.RECEIVER;
        }

        @Override
        public int getCapacity() {
            return 1000000;
        }
    }
}
