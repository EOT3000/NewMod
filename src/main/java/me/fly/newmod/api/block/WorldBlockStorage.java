package me.fly.newmod.api.block;

import me.fly.newmod.api.util.IntPair;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class WorldBlockStorage {
    public final World world;

    public WorldBlockStorage(World world) {
        this.world = world;
    }

    private final Map<IntPair, RegionBlockStorage> regions = new HashMap<>();

    public RegionBlockStorage getRegion(int x, int z) {
        IntPair pair = new IntPair(x, z);

        regions.putIfAbsent(pair, new RegionBlockStorage(x, z, world));

        return regions.get(pair);
    }

    public Map<IntPair, RegionBlockStorage> getRegions() {
        return new HashMap<>(regions);
    }
}
