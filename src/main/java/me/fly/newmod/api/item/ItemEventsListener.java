package me.fly.newmod.api.item;

import me.fly.newmod.api.events.common.ModUseEvent;

public interface ItemEventsListener {

    default void onItemUseLowest(ModUseEvent event) {}

    default void onItemUseNormal(ModUseEvent event) {}

    default void onItemUseHighest(ModUseEvent event) {}

    default void onItemUseMonitor(ModUseEvent event) {}

}
