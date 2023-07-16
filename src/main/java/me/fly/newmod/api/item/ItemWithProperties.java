package me.fly.newmod.api.item;

import me.fly.newmod.api.item.properties.ItemProperties;

public interface ItemWithProperties<T extends ItemProperties> {
    VanillaOrModItem get();

    T getProperties();
}
