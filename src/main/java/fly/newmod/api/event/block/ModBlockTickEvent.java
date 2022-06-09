package fly.newmod.api.event.block;

import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.ModEventWrapper;

public class ModBlockTickEvent extends ModEventWrapper implements ModBlockEvent {
    private final int tick;
    private final ModBlock block;

    public ModBlockTickEvent(int tick, ModBlock block) {
        this.tick = tick;
        this.block = block;
    }

    public int getTick() {
        return tick;
    }

    @Override
    public ModBlock getBlock() {
        return block;
    }
}
