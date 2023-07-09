package me.fly.newmod.technology.consumer;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ModItemType;

public interface ModFurnaceRecipe {
    boolean canBeUsed(ModBlockType type);

    ModItemType primaryBlock();
}
