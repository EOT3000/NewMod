package me.fly.newmod.api.events.common;

import me.fly.newmod.api.block.ModBlock;
import me.fly.newmod.api.events.ModEventWrapper;
import me.fly.newmod.api.item.ModItemStack;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ModUseEvent extends ModEventWrapper implements Cancellable {
    private final ModItemStack modItemStack;
    private final ModBlock modBlock;

    private final Block clickedBlock;
    private final ItemStack usedStack;

    private final Location interactionPoint;
    private final BlockFace blockFace;

    private final Player player;

    private final EquipmentSlot hand;

    private final Action action;

    private Event.Result useInteractedBlock;
    private Event.Result useItemInHand;

    public ModUseEvent(PlayerInteractEvent event, ModItemStack item, ModBlock block) {
        this.modItemStack = item;
        this.modBlock = block;

        this.clickedBlock = event.getClickedBlock();
        this.usedStack = event.getItem();

        this.interactionPoint = event.getInteractionPoint();
        this.blockFace = event.getBlockFace();

        this.player = event.getPlayer();

        this.hand = event.getHand();

        this.action = event.getAction();

        this.useInteractedBlock = event.useInteractedBlock();
        this.useItemInHand = event.useItemInHand();
    }

    public Block getBlock() {
        return clickedBlock;
    }



    public ModBlock getModBlock() {
        return modBlock;
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

    public Action getAction() {
        return action;
    }

    public ModItemStack getModItem() {
        return modItemStack;
    }



    public ItemStack getItem() {
        return usedStack;
    }


    @Override
    public boolean isCancelled() {
        return this.useInteractedBlock == Event.Result.DENY;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.setUseInteractedBlock(cancel ? Event.Result.DENY : (this.useInteractedBlock == Event.Result.DENY ? Event.Result.DEFAULT : this.useInteractedBlock));
        this.setUseItemInHand(cancel ? Event.Result.DENY : (this.useItemInHand == Event.Result.DENY ? Event.Result.DEFAULT : this.useItemInHand));
    }
}
