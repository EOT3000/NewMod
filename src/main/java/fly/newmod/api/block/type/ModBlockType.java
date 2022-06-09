package fly.newmod.api.block.type;

import fly.newmod.api.block.data.ModBlockData;
import fly.newmod.api.event.BlockEventsListener;
import fly.newmod.api.item.type.ModItemType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class ModBlockType {
    private BlockEventsListener listener;
    protected final Material defaultMaterial;
    protected final NamespacedKey id;

    private ModItemType item;

    protected final Class<? extends ModBlockData> data;

    public ModBlockType(Material defaultMaterial, NamespacedKey id) {
        this(defaultMaterial, id, ModBlockData.class);
    }

    public ModBlockType(Material defaultMaterial, NamespacedKey id, Class<? extends ModBlockData> data) {
        this.defaultMaterial = defaultMaterial;
        this.id = id;

        this.data = data;

        this.listener = new BlockEventsListener() {};
    }

    public BlockEventsListener getListener() {
        return listener;
    }

    public ModBlockType setListener(BlockEventsListener listener) {
        this.listener = listener;

        return this;
    }

    public void setItem(ModItemType item) {
        this.item = item;
    }

    public ModItemType getItem() {
        return item;
    }

    public final Material getDefaultMaterial() {
        return defaultMaterial;
    }

    public final NamespacedKey getId() {
        return id;
    }

    public Class<? extends ModBlockData> getDataType() {
        return data;
    }
}
