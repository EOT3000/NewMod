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
    private final ModBlock modBlock;
    private final Block block;

    private final Player player;

    private final Block blockAgainst;
    private final BlockState blockReplacedState;

    private final EquipmentSlot hand;

    private boolean cancelled;
    protected boolean canBuild;

    private boolean defaultPlace;

    public ModBlockPlaceEvent(BlockPlaceEvent event, ModBlock modBlock) {
        this.item = event.getItemInHand();
        this.modBlock = modBlock;

        this.block = event.getBlock();

        this.player = event.getPlayer();

        this.blockAgainst = event.getBlockAgainst();
        this.blockReplacedState = event.getBlockReplacedState();
        this.hand = event.getHand();

        this.cancelled = event.isCancelled();
        this.canBuild = event.canBuild();

        this.defaultPlace = true;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public ModBlock getModBlock() {
        return modBlock;
    }

    public Block getBlock() {
        return block;
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

    public boolean isDefaultPlace() {
        return defaultPlace;
    }

    public void setDefaultPlace(boolean defaultPlace) {
        this.defaultPlace = defaultPlace;
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
