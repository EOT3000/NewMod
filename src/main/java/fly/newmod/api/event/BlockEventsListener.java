package fly.newmod.api.event;

import fly.newmod.api.event.block.ModBlockBreakEvent;
import fly.newmod.api.event.block.ModBlockInteractEvent;
import fly.newmod.api.event.block.ModBlockPlaceEvent;
import fly.newmod.api.event.block.ModBlockTickEvent;

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

}
