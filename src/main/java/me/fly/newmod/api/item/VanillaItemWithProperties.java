package me.fly.newmod.api.item;

import me.fly.newmod.api.item.properties.ItemProperties;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VanillaItemWithProperties<T extends ItemProperties> implements VanillaItem, ItemWithProperties<T> {
    private final Material material;
    private final T properties;

    public VanillaItemWithProperties(Material material, T properties) {
        this.material = material;
        this.properties = properties;
    }

    @Override
    public VanillaOrModItem get() {
        return this;
    }

    @Override
    public Material getVanilla() {
        return material;
    }

    @Override
    public T getProperties() {
        return properties;
    }

    @Override
    public ItemStack create() {
        return new ItemStack(material);
    }
}
