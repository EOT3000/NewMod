package me.fly.newmod.api.block;

import me.fly.newmod.api.block.data.DefaultModBlockData;
import me.fly.newmod.api.block.data.ModBlockData;
import me.fly.newmod.api.events.block.ModBlockBreakEvent;
import me.fly.newmod.api.item.ModItemType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class ModBlockType {
    private BlockEventsListener listener;
    protected final Material defaultMaterial;
    protected final NamespacedKey id;

    private ModItemType item;

    protected final Class<? extends ModBlockData> data;

    public ModBlockType(Material defaultMaterial, NamespacedKey id) {
        this(defaultMaterial, id, DefaultModBlockData.class);
    }

    public ModBlockType(Material defaultMaterial, NamespacedKey id, Class<? extends ModBlockData> data) {
        this.defaultMaterial = defaultMaterial;
        this.id = id;

        this.data = data;

        this.listener = new BlockEventsListener() {};
    }

    public ModBlockType(Material defaultMaterial, NamespacedKey id, Class<? extends ModBlockData> data, BlockEventsListener listener) {
        this.defaultMaterial = defaultMaterial;
        this.id = id;

        this.data = data;

        this.listener = listener;
    }

    public void setListener(BlockEventsListener listener) {
        this.listener = listener;
    }

    public BlockEventsListener getListener() {
        return listener;
    }

    public void setItem(ModItemType item) {
        if(this.item != null) {
            throw new IllegalStateException("Cannot set an item to a block if already set");
        }

        this.item = item;
    }

    public ModItemType getItem() {
        return item;
    }

    public final Material getDefaultMaterial() {
        return defaultMaterial;
    }

    public boolean isRightState(Block block, ModBlock modBlock) {
        return block.getType().equals(defaultMaterial);
    }

    public void place(Block block, ModBlock modBlock) {
        block.setType(getDefaultMaterial());
    }

    public final NamespacedKey getId() {
        return id;
    }

    public Class<? extends ModBlockData> getDataType() {
        return data;
    }

    public ItemStack getDropStack(ModBlockBreakEvent ne) {
        ModItemType item = ne.getModBlock().getType().getItem();

        if(item == null) {
            return null;
        } else {
            return item.create();
        }
    }

    @Override
    public String toString() {
        return "ModBlockType{" +
                "listener=" + listener +
                ", defaultMaterial=" + defaultMaterial +
                ", id=" + id +
                ", item=" + item +
                ", data=" + data +
                '}';
    }
}
