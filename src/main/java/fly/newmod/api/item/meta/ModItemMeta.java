package fly.newmod.api.item.meta;

import fly.newmod.api.item.type.ModItemType;

import java.util.Objects;

public interface ModItemMeta {
    ModItemType getType();

    ModItemMeta cloneItem();

    boolean isAcceptable(ModItemMeta meta);

    abstract class AbstractModItemMeta implements ModItemMeta {
        private final ModItemType type;

        protected AbstractModItemMeta(ModItemType type) {
            this.type = type;

            //System.out.println(type);

            if(type == null) {
                //new RuntimeException().printStackTrace();
            }
        }

        @Override
        public ModItemType getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AbstractModItemMeta that = (AbstractModItemMeta) o;
            return Objects.equals(type, that.type);
        }
    }
}
