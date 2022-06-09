package fly.newmod.api.event.item;

import fly.newmod.api.event.ModEventWrapper;
import fly.newmod.api.item.ModItemStack;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ModItemUseEvent extends ModEventWrapper implements Cancellable, ModItemEvent {
    static {
        new ModItemUseEventListener();
    }

    private final ModItemStack item;
    private final Block clickedBlock;

    private final Location interactionPoint;
    private final BlockFace blockFace;

    private final Player player;

    private final EquipmentSlot hand;

    private Event.Result useInteractedBlock;
    private Event.Result useItemInHand;

    public ModItemUseEvent(PlayerInteractEvent event, ModItemStack item) {
        this.item = item;
        this.clickedBlock = event.getClickedBlock();

        this.interactionPoint = event.getInteractionPoint();
        this.blockFace = event.getBlockFace();

        this.player = event.getPlayer();

        this.hand = event.getHand();

        this.useInteractedBlock = event.useInteractedBlock();
        this.useItemInHand = event.useItemInHand();
    }

    public Block getBlock() {
        return clickedBlock;
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

    @Override
    public ModItemStack getItem() {
        return item;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    private static class ModItemUseEventListener extends ModEventListener {
        private static ModItemUseEventListener INSTANCE;

        private ModItemUseEventListener() {
            if(INSTANCE != null) {
                throw new RuntimeException("ModItemUseEventListener already initialized");
            }

            INSTANCE = this;
        }
    }
}
