package fly.newmod.api.block.data;

import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.item.type.ModItemType;

public interface ModBlockData {
    ModBlockType getType();

    ModBlockData cloneBlock();

    abstract class AbstractModBlockData implements ModBlockData {
        private final ModBlockType type;

        protected AbstractModBlockData(ModBlockType type) {
            this.type = type;
        }

        @Override
        public ModBlockType getType() {
            return type;
        }
    }
}
