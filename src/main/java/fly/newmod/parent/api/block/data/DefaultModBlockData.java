package fly.newmod.parent.api.block.data;

import fly.newmod.parent.NewMod;
import fly.newmod.parent.api.block.BlockManager;
import fly.newmod.parent.api.block.type.ModBlockType;
import fly.newmod.parent.util.PersistentDataUtils;

import java.util.Map;

public class DefaultModBlockData extends ModBlockData.AbstractModBlockData {
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
        public boolean applyData(Map<String, String> map, DefaultModBlockData defaultModBlockData) {
            map.put("id", PersistentDataUtils.NAMESPACED_KEY.toPrimitive(defaultModBlockData.getType().getId(), null));

            return true;
        }
    }
}
