package fly.newmod.api.block;

import fly.newmod.NewMod;
import fly.newmod.api.block.data.ModBlockData;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.block.ModBlockBreakEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an item stack with mod properties.
 *
 * Instances of this class should not be stored; instead a new ModItemStack should be created when the need arises
 *
 * @see fly.newmod.api.item.ItemManager#deserializeModItemStack
 */
public final class ModBlock {
    private final Block representation;

    private final ModBlockType type;
    private ModBlockData data;

    public ModBlock(Block representation) {
        BlockManager manager = NewMod.get().getBlockManager();

        this.representation = representation;

        this.type = manager.getType(representation);
        this.data = manager.deserializeData(representation);
    }

    public ModBlock(ModBlockType type) {
        BlockManager manager = NewMod.get().getBlockManager();

        this.representation = null;

        this.type = type;
        this.data = manager.createDefaultData(type);
    }

    public ModBlock(ModBlockType type, ModBlockData data, Block representation) {
        this.representation = representation;

        this.type = type;
        this.data = data;
    }

    public Block create(Location location) {
        if(representation == null) {
            BlockManager manager = NewMod.get().getBlockManager();
            Block block = location.getBlock();

            type.place(block, this);

            manager.applyData(block, data);

            return block;
        } else {
            return representation;
        }
    }

    public Block createInStorage(Location location) {
        BlockManager manager = NewMod.get().getBlockManager();
        Block block = location.getBlock();

        manager.applyData(block, data);

        return block;
    }

    public void update() {
        if(hasPhysicalRepresentation()) {
            BlockManager manager = NewMod.get().getBlockManager();

            manager.applyData(representation, data);
        }
    }

    public ItemStack getDropStack(ModBlockBreakEvent ne) {
        return type.getDropStack(ne);
    }

    public ModBlockType getType() {
        return type;
    }

    public boolean hasPhysicalRepresentation() {
        return representation != null;
    }

    public void setData(ModBlockData data) {
        this.data = data;
    }

    public ModBlockData getData() {
        return data.cloneBlock();
    }

    @Override
    public String toString() {
        return "ModBlock{" +
                "representation=" + representation +
                ", type=" + type +
                ", data=" + data +
                '}';
    }
}
