package fly.newmod.api.event.block;

import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.ModEventWrapper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ModBlockBreakEvent extends ModEventWrapper implements Cancellable, ModBlockEvent {
    private final ModBlock modBlock;
    private final Block block;

    private final Player player;

    private boolean cancelled;
    private boolean dropModItem = true;

    public ModBlockBreakEvent(BlockBreakEvent event, ModBlock modBlock) {
        this.modBlock = modBlock;

        this.block = event.getBlock();

        this.player = event.getPlayer();

        this.cancelled = event.isCancelled();
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

    public boolean isDropModItem() {
        return dropModItem;
    }

    public void setDropModItem(boolean dropModItem) {
        this.dropModItem = dropModItem;
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
