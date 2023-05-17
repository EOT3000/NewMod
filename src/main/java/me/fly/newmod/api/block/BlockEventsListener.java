package me.fly.newmod.api.block;

import me.fly.newmod.api.events.block.*;
import me.fly.newmod.api.events.common.ModUseEvent;
import org.bukkit.event.block.BlockDispenseEvent;

public interface BlockEventsListener {

    default void onBlockTick(ModBlockTickEvent event) {}



    default void onBlockInteractLowest(ModUseEvent event) {}

    default void onBlockInteractNormal(ModUseEvent event) {}

    default void onBlockInteractHighest(ModUseEvent event) {}

    default void onBlockInteractMonitor(ModUseEvent event) {}



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



    default void onStructureGrowLowest(ModBlockStructureGrowEvent event) {}

    default void onStructureGrowNormal(ModBlockStructureGrowEvent event) {}

    default void onStructureGrowHighest(ModBlockStructureGrowEvent event) {}

    default void onStructureGrowMonitor(ModBlockStructureGrowEvent event) {}



    @Deprecated
    default void onDispenseTemp(BlockDispenseEvent event) {}
}
