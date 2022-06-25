package fly.newmod.api.event.block;

import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.ModEventWrapper;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.List;

public class ModStructureGrowEvent extends ModEventWrapper implements Cancellable, ModBlockEvent {
    private boolean cancelled;

    private final Location location;
    private final TreeType species;
    private final boolean bonemeal;
    private final Player player;
    private final List<BlockState> blocks;

    private final ModBlock modBlock;

    public ModStructureGrowEvent(StructureGrowEvent event, ModBlock modBlock) {
        this.cancelled = event.isCancelled();

        this.location = event.getLocation();
        this.species = event.getSpecies();
        this.bonemeal = event.isFromBonemeal();
        this.player = event.getPlayer();

        this.blocks = event.getBlocks();
        this.modBlock = modBlock;
    }

    @Override
    public ModBlock getModBlock() {
        return modBlock;
    }

    @Override
    public Block getBlock() {
        return location.getBlock();
    }

    public TreeType getSpecies() {
        return species;
    }

    public boolean isFromBonemeal() {
        return bonemeal;
    }

    public Player getPlayer() {
        return player;
    }

    public List<BlockState> getBlocks() {
        return blocks;
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
