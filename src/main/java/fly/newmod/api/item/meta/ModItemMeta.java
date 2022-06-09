package fly.newmod.api.item.meta;

import fly.newmod.api.item.type.ModItemType;

public interface ModItemMeta {
    ModItemType getType();

    ModItemMeta cloneItem();

    abstract class AbstractModItemMeta implements ModItemMeta {
        private final ModItemType type;

        protected AbstractModItemMeta(ModItemType type) {
            this.type = type;
        }

        @Override
        public ModItemType getType() {
            return null;
        }
    }
}
