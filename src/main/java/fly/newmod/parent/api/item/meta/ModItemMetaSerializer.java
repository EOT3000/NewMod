package fly.newmod.parent.api.item.meta;

import fly.newmod.parent.NewMod;
import fly.newmod.parent.api.item.ItemManager;
import fly.newmod.parent.api.item.type.ModItemType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public abstract class ModItemMetaSerializer<T extends ModItemMeta> {
    public ModItemMetaSerializer(Class<T> clazz) {
        ItemManager manager = NewMod.get().getItemManager();

        manager.registerSerializer(clazz, this);
    }

    public abstract T getItemMeta(PersistentDataContainer container);

    public abstract T defaultMeta(ModItemType type);

    public abstract boolean applyMeta(ItemStack stack, T t);
}
