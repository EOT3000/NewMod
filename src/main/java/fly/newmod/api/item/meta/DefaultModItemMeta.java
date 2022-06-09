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
        DefaultModItemMeta meta = new DefaultModItemMeta(getType());

        return meta;
    }

    public static class DefaultModItemMetaSerializer extends ModItemMetaSerializer<DefaultModItemMeta> {
        public DefaultModItemMetaSerializer() {
            super(DefaultModItemMeta.class);
        }

        @Override
        public DefaultModItemMeta getItemMeta(PersistentDataContainer container) {
            return null;
        }

        @Override
        public DefaultModItemMeta defaultMeta() {
            return null;
        }

        @Override
        public boolean applyMeta(ItemStack stack, DefaultModItemMeta defaultModItemMeta) {
            ItemManager manager = NewMod.get().getItemManager();

            return defaultModItemMeta.getType().equals(manager.getType(stack));
        }
    }
}
