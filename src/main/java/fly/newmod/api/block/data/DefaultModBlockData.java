package fly.newmod.api.block.data;

import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.block.type.ModBlockType;
import org.bukkit.block.Block;

import java.util.Map;

public class DefaultModBlockData extends ModBlockData.AbstractModBlockData {
    protected DefaultModBlockData(ModBlockType type) {
        super(type);
    }

    @Override
    public DefaultModBlockData cloneBlock() {
        return new DefaultModBlockData(getType());
    }

    public static class DefaultModItemMetaSerializer extends ModBlockDataSerializer<DefaultModBlockData> {
        public DefaultModItemMetaSerializer() {
            super(DefaultModBlockData.class);
        }

        @Override
        public DefaultModBlockData getBlockData(Map<String, String> container) {
            return null;
        }

        @Override
        public DefaultModBlockData defaultMeta() {
            return null;
        }

        @Override
        public boolean applyData(Block block, DefaultModBlockData defaultModBlockData) {
            BlockManager manager = NewMod.get().getBlockManager();

            return defaultModBlockData.getType().equals(manager.getType(block));
        }
    }
}
