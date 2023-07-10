package me.fly.newmod.api.block;

import me.fly.newmod.api.block.data.ModBlockData;
import me.fly.newmod.api.block.data.ModBlockDataSerializer;
import me.fly.newmod.api.util.IntTriple;
import me.fly.newmod.api.util.PersistentDataUtils;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.*;

public class BlockManager {
    public void init() {

    }

    private final Map<Class<? extends ModBlockData>, ModBlockDataSerializer<?>> serializers = new HashMap<>();
    private final Map<NamespacedKey, ModBlockType> blocks = new HashMap<>();

    private final Map<World, WorldBlockStorage> worlds = new HashMap<>();

    public ModBlock deserializeModBlock(Block block) {
        if(getType(block) == null) {
            return null;
        }

        return new ModBlock(getType(block), deserializeData(block), block);
    }

    public Map<World, WorldBlockStorage> getWorlds() {
        return worlds;
    }

    private RegionBlockStorage getRegion(Location location) {
        WorldBlockStorage storage = worlds.get(location.getWorld());

        int x = location.getChunk().getX() >> 5;
        int z = location.getChunk().getZ() >> 5;

        return storage.getRegion(x, z);
    }

    public ModBlockData deserializeData(Block block) {
        ModBlockType type = getType(block);

        return serializers.get(type.getDataType()).getBlockData(getRegion(block.getLocation()).getValues(IntTriple.fromLocation(block.getLocation())));
    }

    @SuppressWarnings("unchecked")
    public Block applyData(Block block, ModBlockData modStack) {
        ((ModBlockDataSerializer<ModBlockData>) serializers.get(modStack.getClass())).applyData(getRegion(block.getLocation()).getValues(IntTriple.fromLocation(block.getLocation())), modStack);

        return block;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> applyData(Map<String, String> map, ModBlockData modStack) {
        ((ModBlockDataSerializer<ModBlockData>) serializers.get(modStack.getClass())).applyData(map, modStack);

        return map;
    }

    public ModBlockType getType(Block block) {
        return block == null ? null : getType(getRegion(block.getLocation()).getValues(IntTriple.fromLocation(block.getLocation())));
    }

    public ModBlockType getType(Location block) {
        return block == null ? null : getType(getRegion(block).getValues(IntTriple.fromLocation(block)));
    }

    public ModBlockType getType(Map<String, String> container) {
        return getType(PersistentDataUtils.namespacedKeyFromPrimitive(container.getOrDefault("id", null)));
    }

    public ModBlockType getType(NamespacedKey key) {
        return blocks.get(key);
    }

    public ModBlockData createDefaultData(ModBlockType type) {
        return serializers.get(type.getDataType()).defaultMeta(type);
    }

    public <T extends ModBlockData> void registerSerializer(Class<T> clazz, ModBlockDataSerializer<T> serializer) {
        serializers.put(clazz, serializer);
    }

    public void registerBlock(ModBlockType type) {
        blocks.put(type.getId(), type);
    }

    public void changeData(Location location, String key, String value) {
        getRegion(location).modifyKey(IntTriple.fromLocation(location), key, value);
    }

    public void purgeData(Location location) {
        getRegion(location).remove(IntTriple.fromLocation(location));
    }

    public Set<String> getAllData(Location location) {
        return getRegion(location).getKeys(IntTriple.fromLocation(location));
    }


    public String getData(Location location, String key) {
        return getRegion(location).getByKey(IntTriple.fromLocation(location), key);
    }

    /*public List<Location> getAllBlocksOfType(String id) {
        List<Location> r = new ArrayList<>();

        for(Map.Entry<Location, Map<String, String>> entry : dataStorage.entrySet()) {
            if(getData(entry.getKey(), "id").equals(id)) {
                r.add(entry.getKey());
            }
        }

        return r;
    }*/

    public List<ModBlockType> getBlocks() {
        return new ArrayList<>(blocks.values());
    }

    public void printData() {
        //System.out.println(dataStorage);
    }
}
