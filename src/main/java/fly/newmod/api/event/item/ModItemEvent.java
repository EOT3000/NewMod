package fly.newmod.api.event.item;

import fly.newmod.api.item.ModItemStack;
import org.bukkit.inventory.ItemStack;

public interface ModItemEvent {
    ModItemStack getModItem();

    ItemStack getItem();
}
