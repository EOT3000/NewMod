package fly.newmod.api.event;

import fly.newmod.api.event.item.ModItemUseEvent;

public interface ItemEventsListener {

    default void onItemUseLowest(ModItemUseEvent event) {}

    default void onItemUseNormal(ModItemUseEvent event) {}

    default void onItemUseHighest(ModItemUseEvent event) {}

    default void onItemUseMonitor(ModItemUseEvent event) {}

}
