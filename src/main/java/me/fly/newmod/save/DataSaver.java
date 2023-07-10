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

    //TODO: figure this out

    public static void load(File dir) {
        for(File w : dir.listFiles()) {
            World world = Bukkit.getWorld(w.getName());

            if(world != null) {
                WorldBlockStorage storage = new WorldBlockStorage(world);

                for (File r : w.listFiles()) {
                    if(r.isFile() && r.getName().endsWith(".yml")) {
                        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(r);

                        if (configuration.contains("x") && configuration.contains("z")) {
                            int x = configuration.getInt("x");
                            int z = configuration.getInt("z");

                            RegionBlockStorage regionBlockStorage = new RegionBlockStorage(x, z, world);

                            regionBlockStorage.load(configuration, r.getPath());

                            storage.putRegion(regionBlockStorage);
                        }

                    }
                }
            }
        }
    }

    public static void loadWorld(BlockManager manager, World world) {
        if (manager.getWorlds().containsKey(world)) {
            return;
        }

        File file = new File("/plugins/NewMod/save/" + world.getName());

        if (file.exists()) {
            return;
        }

        WorldBlockStorage storage = new WorldBlockStorage(world);

        for (File r : file.listFiles()) {
            if (r.isFile() && r.getName().endsWith(".yml")) {

                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(r);

                if (configuration.contains("x") && configuration.contains("z")) {
                    int x = configuration.getInt("x");
                    int z = configuration.getInt("z");

                    RegionBlockStorage regionBlockStorage = new RegionBlockStorage(x, z, world);

                    regionBlockStorage.load(configuration, r.getPath());

                    storage.putRegion(regionBlockStorage);
                }

            }
        }
    }
}
