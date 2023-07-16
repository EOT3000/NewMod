package me.fly.newmod.books;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.events.common.ModUseEvent;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.builders.ModArmorItemTypeBuilder;
import me.fly.newmod.api.item.builders.ModItemTypeBuilder;
import org.bukkit.Material;
import org.bukkit.event.block.Action;

public class BookTypes {
    public static void init() {}

    public static final ModItemType BIRCH_BARK = new ModItemTypeBuilder(Material.PAPER, "birch_bark", NewMod.get()).properties(new WritablePropertiesImpl(1))
            .customName("Birch Bark", 0xFFFFD8).buildAndRegister();

    public static final ModItemType WRITABLE_PAPER = new ModItemTypeBuilder(Material.PAPER, "writable_paper", NewMod.get())
            .customName("Paper and Ink", 0xD8DFFF).buildAndRegister();
}
