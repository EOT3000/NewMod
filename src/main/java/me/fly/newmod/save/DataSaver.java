package me.fly.newmod.save;

import me.fly.newmod.api.block.BlockManager;
import me.fly.newmod.api.block.RegionBlockStorage;
import me.fly.newmod.api.block.WorldBlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataSaver {
    private static int count = 0;

    public static void saveFinally(BlockManager blockManager) {
        for(World world : Bukkit.getWorlds()) {
            WorldBlockStorage wbs = blockManager.getWorlds().get(world);

            if(wbs != null) {
                for(RegionBlockStorage storage : wbs.getRegions().values()) {
                    storage.save();
                }
            }
        }
    }

    public static void save(BlockManager blockManager) {
        for(World world : Bukkit.getWorlds()) {
            if(count++ + world.hashCode() % 3 != 0) {
                return;
            }

            WorldBlockStorage wbs = blockManager.getWorlds().get(world);

            if(wbs != null) {
                for(RegionBlockStorage storage : wbs.getRegions().values()) {
                    storage.saveIfDirty();
                }
            }
        }
    }
}
