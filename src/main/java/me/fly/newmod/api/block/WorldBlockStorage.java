package me.fly.newmod.api.block;

import me.fly.newmod.api.util.IntPair;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class WorldBlockStorage {
    public final World world;

    private final Map<IntPair, RegionBlockStorage> regions = new HashMap<>();

    public RegionBlockStorage getRegion(int x, int z) {
        IntPair pair = new IntPair(x, z);

        regions.putIfAbsent(pair, new RegionBlockStorage(this, x, z));

        return regions.get(pair);
    }
}
