package fly.newmod.api.event.block;

import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.ModEventWrapper;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockGrowEvent;

public class ModBlockGrowEvent extends ModEventWrapper implements Cancellable, ModBlockEvent {
    private final Block block;
    private final ModBlock modBlock;
    private final BlockState newState;

    private boolean cancelled;

    public ModBlockGrowEvent(BlockGrowEvent event, ModBlock modBlock) {
        this.block = event.getBlock();
        this.modBlock = modBlock;
        this.newState = event.getNewState();

        cancelled = event.isCancelled();
    }

    @Override
    public ModBlock getModBlock() {
        return modBlock;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    public BlockState getNewState() {
        return newState;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
