package me.fly.newmod.technology.consumer;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.technology.EnergyComponent;
import me.fly.newmod.technology.TechnologyPlugin;
import me.fly.newmod.technology.data.EnergyHolderBlockDataImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class PressureChamberBlockType extends ModBlockType implements EnergyComponent {
    public PressureChamberBlockType() {
        super(Material.BLAST_FURNACE, new NamespacedKey(TechnologyPlugin.get(), "pressure_chamber"), EnergyHolderBlockDataImpl.class);
    }

    @Override
    public EnergyComponentType getType() {
        return EnergyComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return 3400;
    }
}
