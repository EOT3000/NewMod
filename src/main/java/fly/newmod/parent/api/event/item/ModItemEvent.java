package fly.newmod.parent.api.event.item;

import fly.newmod.parent.api.item.ModItemStack;
import org.bukkit.inventory.ItemStack;

public interface ModItemEvent {
    ModItemStack getModItem();

    ItemStack getItem();
}
