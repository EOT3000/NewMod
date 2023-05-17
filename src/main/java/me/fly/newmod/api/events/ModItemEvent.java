package me.fly.newmod.api.events;

import me.fly.newmod.api.item.ModItemStack;
import org.bukkit.inventory.ItemStack;

public interface ModItemEvent {
    ModItemStack getModItem();

    ItemStack getItem();
}
