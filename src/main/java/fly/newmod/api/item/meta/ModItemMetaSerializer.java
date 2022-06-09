package fly.newmod.api.item.meta;

import fly.newmod.NewMod;
import fly.newmod.api.item.ItemManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public abstract class ModItemMetaSerializer<T extends ModItemMeta> {
    public ModItemMetaSerializer(Class<T> clazz) {
        ItemManager manager = NewMod.get().getItemManager();

        manager.registerSerializer(clazz, this);
    }

    public abstract T getItemMeta(PersistentDataContainer container);

    public abstract T defaultMeta();

    public abstract boolean applyMeta(ItemStack stack, T t);
}
