package me.fly.newmod;

import me.fly.newmod.api.events.common.ModUseEvent;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.builders.ModArmorItemTypeBuilder;
import me.fly.newmod.api.item.builders.ModItemTypeBuilder;
import org.bukkit.Material;
import org.bukkit.event.block.Action;

public class BookTypes {
    public static void init() {}

    public static final ModItemType BIRCH_BARK = new ModItemTypeBuilder(Material.PAPER, "birch_bark", NewMod.get())
            .customName("Birch Bark", 0xFFFFEF).buildAndRegister();
}
