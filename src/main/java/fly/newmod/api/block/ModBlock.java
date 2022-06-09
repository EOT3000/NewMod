package fly.newmod.api.block;

import fly.newmod.NewMod;
import fly.newmod.api.block.data.ModBlockData;
import fly.newmod.api.block.type.ModBlockType;
import org.bukkit.Location;
import org.bukkit.block.Block;

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
        this.data = manager.createDefaultMeta(type);
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

            block.setType(type.getDefaultMaterial());

            manager.applyData(block, data);

            return block;
        } else {
            return representation;
        }
    }

    public void update() {
        if(hasPhysicalRepresentation()) {
            BlockManager manager = NewMod.get().getBlockManager();

            manager.applyData(representation, data);
        }
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
}
