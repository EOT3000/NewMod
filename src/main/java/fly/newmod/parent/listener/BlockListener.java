package fly.newmod.parent.listener;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fly.newmod.parent.NewMod;
import fly.newmod.parent.api.block.BlockManager;
import fly.newmod.parent.api.block.ModBlock;
import fly.newmod.parent.api.block.type.ModBlockType;
import fly.newmod.parent.api.event.block.*;
import fly.newmod.parent.api.event.both.ModBlockItemUseEvent;
import fly.newmod.parent.api.item.ItemManager;
import fly.newmod.parent.api.item.ModItemStack;
import fly.newmod.parent.api.item.type.ModItemType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class BlockListener implements Listener {
    @EventHandler
    public void onServerTick(ServerTickStartEvent event) {
        // was originally: new BlockManager()
        // candidate for dumbest fly code mistake
        BlockManager manager = NewMod.get().getBlockManager();

        for(Location location : manager.getAllLocations()) {
            Block b = location.getBlock();
            ModBlockType type = manager.getType(b);

            if(type != null && type.isRightState(b, manager.deserializeModBlock(b))) {
                try {
                    type.getListener().onBlockTick(new ModBlockTickEvent(event.getTickNumber(), manager.deserializeModBlock(b), b));
                } catch (Exception e) {
                    NewMod.get().getLogger().warning("Block " + " (" + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getWorld().getName() + ") error:");

                    e.printStackTrace();
                }
            } else {
                manager.purgeData(location);

                NewMod.get().getLogger().warning("Block " + " (" + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getWorld().getName() + ") has been purged for block mismatch");
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
        event.setDropItems(ne.vanillaDrop());
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
        event.setDropItems(ne.vanillaDrop());
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
        event.setDropItems(ne.vanillaDrop());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreakMonitor(BlockBreakEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();

        ModBlock modBlock = manager.deserializeModBlock(event.getBlock());

        if(modBlock == null) {
            return;
        }

        ModBlockBreakEvent ne = new ModBlockBreakEvent(event, modBlock);

        if(event.isDropItems() && modBlock.getDropStack(ne) != null) {
            Location l = event.getBlock().getLocation();

            l.getWorld().dropItem(l, modBlock.getDropStack(ne));
        }

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
            itemType.getBlock().place(ne.getBlock(), ne.getModBlock());
        }

        bmanager.changeData(event.getBlock().getLocation(), "doNotBreak", "0");
    }



    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockGrowLowest(BlockGrowEvent event) {
        Block b = event.getBlock();
        BlockManager manager = NewMod.get().getBlockManager();

        if(manager.getType(b) != null) {
            ModBlockGrowEvent ne = new ModBlockGrowEvent(event, manager.deserializeModBlock(b));

            manager.getType(b).getListener().onBlockGrowLowest(ne);

            event.setCancelled(ne.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockGrowNormal(BlockGrowEvent event) {
        Block b = event.getBlock();
        BlockManager manager = NewMod.get().getBlockManager();

        if(manager.getType(b) != null) {
            ModBlockGrowEvent ne = new ModBlockGrowEvent(event, manager.deserializeModBlock(b));

            manager.getType(b).getListener().onBlockGrowNormal(ne);

            event.setCancelled(ne.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockGrowHighest(BlockGrowEvent event) {
        Block b = event.getBlock();
        BlockManager manager = NewMod.get().getBlockManager();

        if(manager.getType(b) != null) {
            ModBlockGrowEvent ne = new ModBlockGrowEvent(event, manager.deserializeModBlock(b));

            manager.getType(b).getListener().onBlockGrowHighest(ne);

            event.setCancelled(ne.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockGrowMonitor(BlockGrowEvent event) {
        Block b = event.getBlock();
        BlockManager manager = NewMod.get().getBlockManager();

        if(manager.getType(b) != null) {
            ModBlockGrowEvent ne = new ModBlockGrowEvent(event, manager.deserializeModBlock(b));

            manager.getType(b).getListener().onBlockGrowMonitor(ne);
        }
    }



    @EventHandler(priority = EventPriority.LOWEST)
    public void onStructureGrowLowest(StructureGrowEvent event) {
        Block b = event.getLocation().getBlock();
        BlockManager manager = NewMod.get().getBlockManager();

        if(manager.getType(b) != null) {
            ModStructureGrowEvent ne = new ModStructureGrowEvent(event, manager.deserializeModBlock(b));

            manager.getType(b).getListener().onStructureGrowLowest(ne);

            event.setCancelled(ne.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onStructureGrowNormal(StructureGrowEvent event) {
        Block b = event.getLocation().getBlock();
        BlockManager manager = NewMod.get().getBlockManager();

        if(manager.getType(b) != null) {
            ModStructureGrowEvent ne = new ModStructureGrowEvent(event, manager.deserializeModBlock(b));

            manager.getType(b).getListener().onStructureGrowNormal(ne);

            event.setCancelled(ne.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStructureGrowHighest(StructureGrowEvent event) {
        Block b = event.getLocation().getBlock();
        BlockManager manager = NewMod.get().getBlockManager();

        if(manager.getType(b) != null) {
            ModStructureGrowEvent ne = new ModStructureGrowEvent(event, manager.deserializeModBlock(b));

            manager.getType(b).getListener().onStructureGrowHighest(ne);

            event.setCancelled(ne.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStructureGrowMonitor(StructureGrowEvent event) {
        Block b = event.getLocation().getBlock();
        BlockManager manager = NewMod.get().getBlockManager();

        if(manager.getType(b) != null) {
            ModStructureGrowEvent ne = new ModStructureGrowEvent(event, manager.deserializeModBlock(b));

            manager.getType(b).getListener().onStructureGrowMonitor(ne);
        }
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



    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        BlockManager manager = NewMod.get().getBlockManager();

        if(manager.getType(event.getBlock()) != null) {
            manager.getType(event.getBlock()).getListener().onDispenseTemp(event);
        }
    }
    

}
