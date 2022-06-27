package fly.newmod.listeners;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.block.ModBlock;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.event.block.ModBlockBreakEvent;
import fly.newmod.api.event.block.ModBlockPlaceEvent;
import fly.newmod.api.event.block.ModBlockTickEvent;
import fly.newmod.api.event.both.ModBlockItemUseEvent;
import fly.newmod.api.item.ItemManager;
import fly.newmod.api.item.ModItemStack;
import fly.newmod.api.item.type.ModItemType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {
    @EventHandler
    public void onServerTick(ServerTickStartEvent event) {
        // was originally: new BlockManager()
        // candidate for dumbest fly code mistake
        BlockManager manager = NewMod.get().getBlockManager();

        for(Location location : manager.getAllLocations()) {
            Block b = location.getBlock();
            ModBlockType type = manager.getType(b);

            if(type.getDefaultMaterial().equals(b.getType())) {
                type.getListener().onBlockTick(new ModBlockTickEvent(event.getTickNumber(), manager.deserializeModBlock(b), b));
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

    @EventHandler(priority = EventPriority.MONITOR)
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

        manager.purgeData(event.getBlock().getLocation());
    }



    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlaceLowest(BlockPlaceEvent event) {
        ItemManager manager = NewMod.get().getItemManager();
        BlockManager bmanager = NewMod.get().getBlockManager();
        ModItemType itemType = manager.getType(event.getItemInHand());

        if(itemType == null) {
            return;
        }

        if(itemType.getBlock() == null) {
            event.setCancelled(true);
            return;
        }

        ModBlockPlaceEvent ne = new ModBlockPlaceEvent(event, new ModBlock(itemType.getBlock()));

        itemType.getBlock().getListener().onBlockPlaceLowest(ne);

        ne.getModBlock().createInStorage(event.getBlock().getLocation());
        bmanager.changeData(event.getBlock().getLocation(), "doNotBreak", "1");

        event.setCancelled(ne.isCancelled());
        event.setBuild(ne.canBuild());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlaceNormal(BlockPlaceEvent event) {
        ItemManager manager = NewMod.get().getItemManager();
        BlockManager bmanager = NewMod.get().getBlockManager();
        ModItemType itemType = manager.getType(event.getItemInHand());

        if(itemType == null) {
            return;
        }

        if(itemType.getBlock() == null) {
            event.setCancelled(true);
            return;
        }

        ModBlockPlaceEvent ne = new ModBlockPlaceEvent(event, new ModBlock(itemType.getBlock()));

        itemType.getBlock().getListener().onBlockPlaceNormal(ne);

        ne.getModBlock().createInStorage(event.getBlock().getLocation());
        bmanager.changeData(event.getBlock().getLocation(), "doNotBreak", "1");

        event.setCancelled(ne.isCancelled());
        event.setBuild(ne.canBuild());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlaceHighest(BlockPlaceEvent event) {
        ItemManager manager = NewMod.get().getItemManager();
        BlockManager bmanager = NewMod.get().getBlockManager();
        ModItemType itemType = manager.getType(event.getItemInHand());

        if(itemType == null) {
            return;
        }

        if(itemType.getBlock() == null) {
            event.setCancelled(true);
            return;
        }

        ModBlockPlaceEvent ne = new ModBlockPlaceEvent(event, new ModBlock(itemType.getBlock()));

        itemType.getBlock().getListener().onBlockPlaceHighest(ne);

        ne.getModBlock().createInStorage(event.getBlock().getLocation());
        bmanager.changeData(event.getBlock().getLocation(), "doNotBreak", "1");

        event.setCancelled(ne.isCancelled());
        event.setBuild(ne.canBuild());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlaceMonitor(BlockPlaceEvent event) {
        ItemManager manager = NewMod.get().getItemManager();
        BlockManager bmanager = NewMod.get().getBlockManager();
        ModItemType itemType = manager.getType(event.getItemInHand());

        if(itemType == null) {
            return;
        }

        if(itemType.getBlock() == null) {
            event.setCancelled(true);
            return;
        }

        ModBlockPlaceEvent ne = new ModBlockPlaceEvent(event, new ModBlock(itemType.getBlock()));

        itemType.getBlock().getListener().onBlockPlaceMonitor(ne);

        new ModBlock(itemType.getBlock()).createInStorage(event.getBlock().getLocation());

        if(ne.isDefaultPlace()) {
            Block block = event.getBlock();

            if(!block.getType().equals(itemType.getBlock().getDefaultMaterial())) {
                block.setType(itemType.getBlock().getDefaultMaterial());
            }
        }

        bmanager.changeData(event.getBlock().getLocation(), "doNotBreak", "0");
    }



    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockGrowLowest(BlockGrowEvent event) {

    }



    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractLowest(PlayerInteractEvent event) {
        BlockManager bmanager = NewMod.get().getBlockManager();
        ItemManager imanager = NewMod.get().getItemManager();

        if(imanager.getType(event.getItem()) == null && bmanager.getType(event.getClickedBlock()) == null) {
            return;
        }

        ModBlock modBlock = bmanager.deserializeModBlock(event.getClickedBlock());
        ModItemStack modItem = imanager.deserializeModItemStack(event.getItem());

        ModBlockItemUseEvent me = new ModBlockItemUseEvent(event, modItem, modBlock);

        if(modBlock != null) {
            modBlock.getType().getListener().onBlockInteractLowest(me);
        }

        if(modItem != null) {
            modItem.getType().getListener().onItemUseLowest(me);
        }

        event.setUseInteractedBlock(me.getUseInteractedBlock());
        event.setUseItemInHand(me.getUseItemInHand());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractNormal(PlayerInteractEvent event) {
        BlockManager bmanager = NewMod.get().getBlockManager();
        ItemManager imanager = NewMod.get().getItemManager();

        if(imanager.getType(event.getItem()) == null && bmanager.getType(event.getClickedBlock()) == null) {
            return;
        }

        ModBlock modBlock = bmanager.deserializeModBlock(event.getClickedBlock());
        ModItemStack modItem = imanager.deserializeModItemStack(event.getItem());

        ModBlockItemUseEvent me = new ModBlockItemUseEvent(event, modItem, modBlock);

        if(modBlock != null) {
            modBlock.getType().getListener().onBlockInteractNormal(me);
        }

        if(modItem != null) {
            modItem.getType().getListener().onItemUseNormal(me);
        }

        event.setUseInteractedBlock(me.getUseInteractedBlock());
        event.setUseItemInHand(me.getUseItemInHand());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractHighest(PlayerInteractEvent event) {
        BlockManager bmanager = NewMod.get().getBlockManager();
        ItemManager imanager = NewMod.get().getItemManager();

        if(imanager.getType(event.getItem()) == null && bmanager.getType(event.getClickedBlock()) == null) {
            return;
        }

        ModBlock modBlock = bmanager.deserializeModBlock(event.getClickedBlock());
        ModItemStack modItem = imanager.deserializeModItemStack(event.getItem());

        ModBlockItemUseEvent me = new ModBlockItemUseEvent(event, modItem, modBlock);

        if(modBlock != null) {
            modBlock.getType().getListener().onBlockInteractHighest(me);
        }

        if(modItem != null) {
            modItem.getType().getListener().onItemUseHighest(me);
        }

        event.setUseInteractedBlock(me.getUseInteractedBlock());
        event.setUseItemInHand(me.getUseItemInHand());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractMonitor(PlayerInteractEvent event) {
        BlockManager bmanager = NewMod.get().getBlockManager();
        ItemManager imanager = NewMod.get().getItemManager();

        if(imanager.getType(event.getItem()) == null && bmanager.getType(event.getClickedBlock()) == null) {
            return;
        }

        ModBlock modBlock = bmanager.deserializeModBlock(event.getClickedBlock());
        ModItemStack modItem = imanager.deserializeModItemStack(event.getItem());

        ModBlockItemUseEvent me = new ModBlockItemUseEvent(event, modItem, modBlock);

        if(modBlock != null) {
            modBlock.getType().getListener().onBlockInteractMonitor(me);
        }

        if(modItem != null) {
            modItem.getType().getListener().onItemUseMonitor(me);
        }
    }
}
