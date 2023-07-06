package me.fly.newmod.api.block.data;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.BlockManager;
import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.util.PersistentDataUtils;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryHolderModBlockDataImpl extends ModBlockData.AbstractModBlockData implements InventoryHolderModBlockData {
    private final int size;

    private final Map<Integer, ItemStack> items;

    protected InventoryHolderModBlockDataImpl(ModBlockType type, int size) {
        super(type);

        this.size = size;
        this.items = new HashMap<>(size);
    }

    @Override
    public Map<Integer, ItemStack> getStoredItems() {
        return new HashMap<>(items);
    }

    @Override
    public void setStoredItem(int index, ItemStack stack) {
        items.put(index, stack);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public DefaultModBlockData cloneBlock() {
        return new DefaultModBlockData(getType());
    }

    public static class InventoryHolderModBlockDataSerializer extends ModBlockDataSerializer<InventoryHolderModBlockDataImpl> {
        public InventoryHolderModBlockDataSerializer() {
            super(InventoryHolderModBlockDataImpl.class);
        }

        @Override
        public InventoryHolderModBlockDataImpl getBlockData(Map<String, String> container) {
            BlockManager manager = NewMod.get().getBlockManager();

            return new InventoryHolderModBlockDataImpl(manager.getType(container), 1);
        }

        @Override
        public InventoryHolderModBlockDataImpl defaultMeta(ModBlockType type) {
            return new InventoryHolderModBlockDataImpl(type, 1);
        }

        @Override
        public boolean applyData(Map<String, String> map, InventoryHolderModBlockDataImpl data) {
            map.put("id", PersistentDataUtils.NAMESPACED_KEY.toPrimitive(data.getType().getId(), null));
            //map.put("Items");

            //TODO: do this

            return true;
        }
    }
}
