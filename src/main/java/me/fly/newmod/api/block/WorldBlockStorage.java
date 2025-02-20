package me.fly.newmod.api.block;

import me.fly.newmod.api.util.IntPair;
import me.fly.newmod.api.util.IntTriple;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.*;

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

    public void putRegion(RegionBlockStorage storage) {
        regions.put(new IntPair(storage.x, storage.z), storage);
    }

    public Map<IntPair, RegionBlockStorage> getRegions() {
        return new HashMap<>(regions);
    }

    public Set<Location> getAllLocations() {
        Set<Location> ret = new HashSet<>();

        for(RegionBlockStorage region : regions.values()) {
            for(IntTriple location : region.getAllLocations()) {
                ret.add(new Location(world, location.x, location.y, location.z));
            }
        }

        return ret;
    }
}
