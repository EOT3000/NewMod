package me.fly.newmod.api.block;

import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// A region of 32 by 32 chunks
public class RegionBlockStorage {
    public final int x;
    public final int z;
    public final World world;

    private final Map<Vector, Map<String, String>> data = new HashMap<>();

    public void remove(Vector vector) {
        data.remove(vector);
    }

    public Set<String> getKeys(Vector vector) {
        return data.getOrDefault(vector, new HashMap<>()).keySet();
    }

    public String getByKey(Vector vector, String key) {
        return data.getOrDefault(vector, new HashMap<>()).get(key);
    }

    public void modifyKey(Vector vector, String key, String value) {
        data.putIfAbsent(vector, new HashMap<>());

        data.get(vector).put(key, value);
    }

    public void removeKey(Vector vector, String key) {
        if(data.containsKey(vector)) {
            data.get(vector).remove(key);
        }
    }
}
