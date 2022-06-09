package fly.newmod.api.block.data;

import fly.newmod.api.block.BlockManager;
import fly.newmod.NewMod;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Map;

public abstract class ModBlockDataSerializer<T extends ModBlockData> {
    public ModBlockDataSerializer(Class<T> clazz) {
        BlockManager manager = NewMod.get().getBlockManager();

        manager.registerSerializer(clazz, this);
    }

    public abstract T getBlockData(Map<String, String> container);

    public abstract T defaultMeta();

    public abstract boolean applyData(Block block, T t);
}
