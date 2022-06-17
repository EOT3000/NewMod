package fly.newmod.api.item.meta;

import fly.newmod.NewMod;
import fly.newmod.api.item.ItemManager;
import fly.newmod.api.item.type.ModItemType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public class DefaultModItemMeta extends ModItemMeta.AbstractModItemMeta {
    protected DefaultModItemMeta(ModItemType type) {
        super(type);
    }

    @Override
    public DefaultModItemMeta cloneItem() {
        return new DefaultModItemMeta(getType());
    }

    @Override
    public boolean isAcceptable(ModItemMeta meta) {
        return meta.getType() == getType() || getType().equals(meta.getType());
    }

    public static class DefaultModItemMetaSerializer extends ModItemMetaSerializer<DefaultModItemMeta> {
        public DefaultModItemMetaSerializer() {
            super(DefaultModItemMeta.class);
        }

        @Override
        public DefaultModItemMeta getItemMeta(PersistentDataContainer container) {
            ItemManager manager = NewMod.get().getItemManager();

            return new DefaultModItemMeta(manager.getType(container));
        }

        @Override
        public DefaultModItemMeta defaultMeta(ModItemType type) {
            return new DefaultModItemMeta(type);
        }

        @Override
        public boolean applyMeta(ItemStack stack, DefaultModItemMeta defaultModItemMeta) {
            ItemManager manager = NewMod.get().getItemManager();

            return defaultModItemMeta.getType().equals(manager.getType(stack));
        }
    }
}
