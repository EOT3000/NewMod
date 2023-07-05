package me.fly.newmod.technology;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.texture.MetaModifier;
import me.fly.newmod.technology.producers.SolarGeneratorItemType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.List;

public class EnergyManagerItemType extends ModItemType {
    public EnergyManagerItemType() {
        super(Material.DAYLIGHT_DETECTOR, new NamespacedKey("", "energy_manager"), DefaultModItemMeta.class, false,
                new EnergyManagerBlockType(), new ItemEventsListener() {}, Component.text(name).color(TextColor.color(color)));
    }
}
