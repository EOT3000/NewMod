package fly.newmod.api.block.data;

import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.block.type.ModBlockType;
import org.bukkit.block.Block;

import java.util.Map;

public class DefaultModBlockData extends ModBlockData.AbstractModBlockData {
    static{
        new DefaultModBlockDataSerializer();
    }

    protected DefaultModBlockData(ModBlockType type) {
        super(type);
    }

    @Override
    public DefaultModBlockData cloneBlock() {
        return new DefaultModBlockData(getType());
    }

    public static class DefaultModBlockDataSerializer extends ModBlockDataSerializer<DefaultModBlockData> {
        public DefaultModBlockDataSerializer() {
            super(DefaultModBlockData.class);
        }

        @Override
        public DefaultModBlockData getBlockData(Map<String, String> container) {
            BlockManager manager = NewMod.get().getBlockManager();

            return new DefaultModBlockData(manager.getType(container));
        }

        @Override
        public DefaultModBlockData defaultMeta(ModBlockType type) {
            return new DefaultModBlockData(type);
        }

        @Override
        public boolean applyData(Block block, DefaultModBlockData defaultModBlockData) {
            BlockManager manager = NewMod.get().getBlockManager();

            return defaultModBlockData.getType().equals(manager.getType(block));
        }
    }
}
