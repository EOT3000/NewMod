package fly.newmod.parent.api.block.data;

import fly.newmod.parent.api.block.BlockManager;
import fly.newmod.parent.NewMod;
import fly.newmod.parent.api.block.type.ModBlockType;

import java.util.Map;

public abstract class ModBlockDataSerializer<T extends ModBlockData> {
    public ModBlockDataSerializer(Class<T> clazz) {
        BlockManager manager = NewMod.get().getBlockManager();

        System.out.println("loading");

        manager.registerSerializer(clazz, this);
    }

    public abstract T getBlockData(Map<String, String> container);

    public abstract T defaultMeta(ModBlockType type);

    public abstract boolean applyData(Map<String, String> map, T t);
}
