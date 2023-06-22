package me.fly.newmod.save;

import me.fly.newmod.api.block.BlockManager;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataSaver {
    public static void save(BlockManager blockManager, File saveDir) {
        blockManager.printData();

        YamlConfiguration configuration = new YamlConfiguration();

        for(Location location : blockManager.getAllLocations()) {
            Map<String, String> section = new HashMap<>();

            for(String key : blockManager.getAllData(location)) {
                section.put(key, blockManager.getData(location, key));
            }

            configuration.set(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "," + location.getWorld().getName(), section);
        }

        try {
            configuration.save(saveDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
