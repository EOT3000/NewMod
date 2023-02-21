package fly.newmod.parent.api.event;

import fly.newmod.parent.api.event.block.*;
import fly.newmod.parent.api.event.both.ModBlockItemUseEvent;
import org.bukkit.event.block.BlockDispenseEvent;

public interface BlockEventsListener {

    default void onBlockTick(ModBlockTickEvent event) {}



    default void onBlockInteractLowest(ModBlockItemUseEvent event) {}

    default void onBlockInteractNormal(ModBlockItemUseEvent event) {}

    default void onBlockInteractHighest(ModBlockItemUseEvent event) {}

    default void onBlockInteractMonitor(ModBlockItemUseEvent event) {}



    default void onBlockBreakLowest(ModBlockBreakEvent event) {}

    default void onBlockBreakNormal(ModBlockBreakEvent event) {}

    default void onBlockBreakHighest(ModBlockBreakEvent event) {}

    default void onBlockBreakMonitor(ModBlockBreakEvent event) {}



    default void onBlockPlaceLowest(ModBlockPlaceEvent event) {}

    default void onBlockPlaceNormal(ModBlockPlaceEvent event) {}

    default void onBlockPlaceHighest(ModBlockPlaceEvent event) {}

    default void onBlockPlaceMonitor(ModBlockPlaceEvent event) {}



    default void onBlockGrowLowest(ModBlockGrowEvent event) {}

    default void onBlockGrowNormal(ModBlockGrowEvent event) {}

    default void onBlockGrowHighest(ModBlockGrowEvent event) {}

    default void onBlockGrowMonitor(ModBlockGrowEvent event) {}



    default void onStructureGrowLowest(ModStructureGrowEvent event) {}

    default void onStructureGrowNormal(ModStructureGrowEvent event) {}

    default void onStructureGrowHighest(ModStructureGrowEvent event) {}

    default void onStructureGrowMonitor(ModStructureGrowEvent event) {}



    @Deprecated
    default void onDispenseTemp(BlockDispenseEvent event) {}
}
