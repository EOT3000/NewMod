package me.fly.newmod.api.events.block;

import me.fly.newmod.api.block.ModBlock;
import me.fly.newmod.api.events.ModBlockEvent;
import me.fly.newmod.api.events.ModEventWrapper;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;

public class ModBlockBreakEvent extends ModEventWrapper implements Cancellable, ModBlockEvent {
    private final ModBlock modBlock;
    private final Block block;

    private final Player player;

    private boolean cancelled;
    private boolean vanillaDrop;

    public ModBlockBreakEvent(BlockBreakEvent event, ModBlock modBlock) {
        this.modBlock = modBlock;

        this.block = event.getBlock();

        this.player = event.getPlayer();

        this.cancelled = event.isCancelled();

        this.vanillaDrop = event.isDropItems();
    }

    public ModBlock getModBlock() {
        return modBlock;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean vanillaDrop() {
        return vanillaDrop;
    }

    public void setVanillaDrop(boolean vanillaDrop) {
        this.vanillaDrop = vanillaDrop;
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
