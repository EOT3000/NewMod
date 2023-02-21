package fly.newmod.parent.api.event;

import fly.newmod.parent.api.event.both.ModBlockItemUseEvent;

public interface ItemEventsListener {

    default void onItemUseLowest(ModBlockItemUseEvent event) {}

    default void onItemUseNormal(ModBlockItemUseEvent event) {}

    default void onItemUseHighest(ModBlockItemUseEvent event) {}

    default void onItemUseMonitor(ModBlockItemUseEvent event) {}

}
