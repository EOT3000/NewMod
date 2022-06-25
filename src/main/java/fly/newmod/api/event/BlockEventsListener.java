package fly.newmod.api.event;

import fly.newmod.api.event.block.*;

public interface BlockEventsListener {

    default void onBlockTick(ModBlockTickEvent event) {}



    default void onBlockInteractLowest(ModBlockInteractEvent event) {}

    default void onBlockInteractNormal(ModBlockInteractEvent event) {}

    default void onBlockInteractHighest(ModBlockInteractEvent event) {}

    default void onBlockInteractMonitor(ModBlockInteractEvent event) {}



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
}
