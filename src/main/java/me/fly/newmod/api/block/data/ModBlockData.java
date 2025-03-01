package me.fly.newmod.api.block.data;

import me.fly.newmod.api.block.ModBlockType;

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
