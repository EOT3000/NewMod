package fly.newmod.api.event.block;

import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.ModEventWrapper;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ModBlockPlaceEvent extends ModEventWrapper implements Cancellable, ModBlockEvent {
    private final ItemStack item;
    private final ModBlock clickedBlock;

    private final Player player;

    private final Block blockAgainst;
    private final BlockState blockReplacedState;

    private final EquipmentSlot hand;

    private boolean cancelled;
    protected boolean canBuild;

    public ModBlockPlaceEvent(BlockPlaceEvent event, ModBlock block) {
        this.item = event.getItemInHand();
        this.clickedBlock = block;

        this.player = event.getPlayer();

        this.blockAgainst = event.getBlockAgainst();
        this.blockReplacedState = event.getBlockReplacedState();
        this.hand = event.getHand();

        this.cancelled = event.isCancelled();
        this.canBuild = event.canBuild();
    }

    public ItemStack getItem() {
        return item;
    }

    public ModBlock getBlock() {
        return clickedBlock;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlockAgainst() {
        return blockAgainst;
    }

    public BlockState getBlockReplacedState() {
        return blockReplacedState;
    }

    public EquipmentSlot getHand() {
        return hand;
    }

    public boolean canBuild() {
        return canBuild;
    }

    public void setBuild(boolean canBuild) {
        this.canBuild = canBuild;
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
