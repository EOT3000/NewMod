package me.fly.newmod.api.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VanillaOrModItem {
    private final Material vanilla;
    private final ModItemType mod;

    public VanillaOrModItem(ModItemType type) {
        if(type == null) {
            throw new NullPointerException("Type cannot be null");
        }

        this.vanilla = null;
        this.mod = type;
    }

    public VanillaOrModItem(Material type) {
        if(type == null) {
            throw new NullPointerException("Type cannot be null");
        }

        this.vanilla = type;
        this.mod = null;
    }

    public Material getVanilla() {
        return vanilla;
    }

    public ModItemType getMod() {
        return mod;
    }

    public ItemStack create() {
        if(vanilla == null) {
            @SuppressWarnings("all")
            ItemStack stack = mod.create();

            return stack;
        }

        return new ItemStack(vanilla);
    }
}
