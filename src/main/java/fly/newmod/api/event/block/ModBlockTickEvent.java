package fly.newmod.api.event.block;

import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.ModEventWrapper;
import org.bukkit.block.Block;

public class ModBlockTickEvent extends ModEventWrapper implements ModBlockEvent {
    private final int tick;
    private final Block block;
    private final ModBlock modBlock;

    public ModBlockTickEvent(int tick, ModBlock modBlock, Block block) {
        this.tick = tick;
        this.block = block;
        this.modBlock = modBlock;
    }

    public int getTick() {
        return tick;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public ModBlock getModBlock() {
        return modBlock;
    }
}
