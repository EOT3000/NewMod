package fly.newmod.api.event.block;

import fly.newmod.api.block.ModBlock;
import fly.newmod.api.event.ModEventWrapper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ModBlockInteractEvent extends ModEventWrapper implements Cancellable, ModBlockEvent {
    static {
        new ModBlockInteractEventListener();
    }

    private final ItemStack item;
    private final ModBlock modBlock;
    private final Block block;

    private final Location interactionPoint;
    private final BlockFace blockFace;

    private final Player player;

    private final EquipmentSlot hand;

    private Event.Result useInteractedBlock;
    private Event.Result useItemInHand;

    public ModBlockInteractEvent(PlayerInteractEvent event, ModBlock modBlock) {
        this.item = event.getItem();
        this.modBlock = modBlock;
        this.block = event.getClickedBlock();

        this.interactionPoint = event.getInteractionPoint();
        this.blockFace = event.getBlockFace();

        this.player = event.getPlayer();

        this.hand = event.getHand();

        this.useInteractedBlock = event.useInteractedBlock();
        this.useItemInHand = event.useItemInHand();
    }

    public ItemStack getItem() {
        return item;
    }

    public ModBlock getModBlock() {
        return modBlock;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    public Location getInteractionPoint() {
        return interactionPoint.clone();
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public Player getPlayer() {
        return player;
    }

    public EquipmentSlot getHand() {
        return hand;
    }

    public Event.Result getUseInteractedBlock() {
        return useInteractedBlock;
    }

    public void setUseInteractedBlock(Event.Result useInteractedBlock) {
        this.useInteractedBlock = useInteractedBlock;
    }

    public Event.Result getUseItemInHand() {
        return useItemInHand;
    }

    public void setUseItemInHand(Event.Result useItemInHand) {
        this.useItemInHand = useItemInHand;
    }

    //TODO: add

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    private static class ModBlockInteractEventListener extends ModEventListener {
        private static ModBlockInteractEventListener INSTANCE;

        private ModBlockInteractEventListener() {
            if(INSTANCE != null) {
                throw new RuntimeException("ModBlockInteractEventListener already initialized");
            }

            INSTANCE = this;
        }
    }
}
