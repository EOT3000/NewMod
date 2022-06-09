package fly.newmod.listeners;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.block.ModBlockBreakEvent;
import fly.newmod.api.event.block.ModBlockPlaceEvent;
import fly.newmod.api.event.block.ModBlockTickEvent;
import fly.newmod.api.item.ItemManager;
import fly.newmod.api.item.ModItemStack;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.function.Consumer;

public class BlockListener implements Listener {
    @EventHandler
    public void onServerTick(ServerTickStartEvent event) {
        BlockManager manager = new BlockManager();

        for(Location location : manager.getAllLocations()) {
            Block b = location.getBlock();
            ModBlockType type = manager.getType(b);

            if(type.getDefaultMaterial().equals(b.getType())) {
                type.getListener().onBlockTick(new ModBlockTickEvent(event.getTickNumber(), manager.deserializeModBlock(b)));
            } else {
                manager.purgeData(location);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreakLowest(BlockBreakEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();

        ModBlock modBlock = manager.deserializeModBlock(event.getBlock());

        if(modBlock == null) {
            return;
        }

        ModBlockBreakEvent ne = new ModBlockBreakEvent(event, modBlock);

        modBlock.getType().getListener().onBlockBreakLowest(ne);

        event.setCancelled(ne.isCancelled());
        event.setDropItems(ne.isDropModItem());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreakNormal(BlockBreakEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();

        ModBlock modBlock = manager.deserializeModBlock(event.getBlock());

        if(modBlock == null) {
            return;
        }

        ModBlockBreakEvent ne = new ModBlockBreakEvent(event, modBlock);

        modBlock.getType().getListener().onBlockBreakNormal(ne);

        event.setCancelled(ne.isCancelled());
        event.setDropItems(ne.isDropModItem());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreakHighest(BlockBreakEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();

        ModBlock modBlock = manager.deserializeModBlock(event.getBlock());

        if(modBlock == null) {
            return;
        }

        ModBlockBreakEvent ne = new ModBlockBreakEvent(event, modBlock);

        modBlock.getType().getListener().onBlockBreakHighest(ne);

        event.setCancelled(ne.isCancelled());
        event.setDropItems(ne.isDropModItem());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreakMonitor(BlockBreakEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();

        ModBlock modBlock = manager.deserializeModBlock(event.getBlock());

        if(modBlock == null) {
            return;
        }

        if(event.isDropItems() && modBlock.getType().getItem() != null) {
            ItemManager itemManager = NewMod.get().getItemManager();

            Location l = event.getBlock().getLocation();

            l.getWorld().dropItem(l, new ModItemStack(modBlock.getType().getItem()).create());
        }

        ModBlockBreakEvent ne = new ModBlockBreakEvent(event, modBlock);

        modBlock.getType().getListener().onBlockBreakMonitor(ne);
    }



    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlaceLowest(BlockPlaceEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();

        ModBlock modBlock = manager.deserializeModBlock(event.getBlock());

        if(modBlock == null) {
            return;
        }

        ModBlockPlaceEvent ne = new ModBlockPlaceEvent(event, modBlock);

        modBlock.getType().getListener().onBlockPlaceLowest(ne);

        event.setCancelled(ne.isCancelled());
        event.setBuild(ne.canBuild());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlaceNormal(BlockPlaceEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();

        ModBlock modBlock = manager.deserializeModBlock(event.getBlock());

        if(modBlock == null) {
            return;
        }

        ModBlockPlaceEvent ne = new ModBlockPlaceEvent(event, modBlock);

        modBlock.getType().getListener().onBlockPlaceNormal(ne);

        event.setCancelled(ne.isCancelled());
        event.setBuild(ne.canBuild());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlaceHighest(BlockPlaceEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();

        ModBlock modBlock = manager.deserializeModBlock(event.getBlock());

        if(modBlock == null) {
            return;
        }

        ModBlockPlaceEvent ne = new ModBlockPlaceEvent(event, modBlock);

        modBlock.getType().getListener().onBlockPlaceHighest(ne);

        event.setCancelled(ne.isCancelled());
        event.setBuild(ne.canBuild());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlaceMonitor(BlockPlaceEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();

        ModBlock modBlock = manager.deserializeModBlock(event.getBlock());

        if(modBlock == null) {
            return;
        }

        modBlock.update();

        ModBlockPlaceEvent ne = new ModBlockPlaceEvent(event, modBlock);

        modBlock.getType().getListener().onBlockPlaceMonitor(ne);
    }
}
