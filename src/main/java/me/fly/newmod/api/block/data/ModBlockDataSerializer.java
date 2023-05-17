package me.fly.newmod.api.block.data;

import me.fly.newmod.api.block.BlockManager;
import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.ModBlockType;

import java.util.Map;

public abstract class ModBlockDataSerializer<T extends ModBlockData> {
    public ModBlockDataSerializer(Class<T> clazz) {
        BlockManager manager = NewMod.get().getBlockManager();

        System.out.println("loading");

        manager.registerSerializer(clazz, this);
    }

    public abstract T getBlockData(Map<String, String> container);

    public abstract T defaultMeta(ModBlockType type);

    public abstract boolean applyData(Map<String, String> map, T t);
}
