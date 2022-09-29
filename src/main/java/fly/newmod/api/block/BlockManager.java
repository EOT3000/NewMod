package fly.newmod.api.block;

import fly.newmod.NewMod;
import fly.newmod.api.block.data.ModBlockData;
import fly.newmod.api.block.data.ModBlockDataSerializer;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.utils.PersistentDataUtils;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockManager {
    public void init() {

    }

    private final Map<Class<? extends ModBlockData>, ModBlockDataSerializer<?>> serializers = new HashMap<>();
    private final Map<NamespacedKey, ModBlockType> blocks = new HashMap<>();

    private final Map<Location, Map<String, String>> dataStorage = new HashMap<>();

    public ModBlock deserializeModBlock(Block block) {
        if(getType(block) == null) {
            return null;
        }

        return new ModBlock(getType(block), deserializeData(block), block);
    }

    public ModBlockData deserializeData(Block block) {
        ModBlockType type = getType(block);

        return serializers.get(type.getDataType()).getBlockData(dataStorage.getOrDefault(block.getLocation(), new HashMap<>()));
    }

    @SuppressWarnings("unchecked")
    public Block applyData(Block block, ModBlockData modStack) {
        dataStorage.putIfAbsent(block.getLocation(), new HashMap<>());

        ((ModBlockDataSerializer<ModBlockData>) serializers.get(modStack.getClass())).applyData(dataStorage.get(block.getLocation()), modStack);

        return block;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> applyData(Map<String, String> map, ModBlockData modStack) {
        ((ModBlockDataSerializer<ModBlockData>) serializers.get(modStack.getClass())).applyData(map, modStack);

        return map;
    }

    public ModBlockType getType(Block block) {
        return block == null ? null : getType(dataStorage.getOrDefault(block.getLocation(), new HashMap<>()));
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
        dataStorage.putIfAbsent(location, new HashMap<>());

        dataStorage.get(location).put(key, value);
    }

    public void purgeData(Location location) {
        dataStorage.remove(location);
    }

    public List<String> getAllData(Location location) {
        dataStorage.putIfAbsent(location, new HashMap<>());

        return new ArrayList<>(dataStorage.get(location).keySet());
    }


    public String getData(Location location, String key) {
        dataStorage.putIfAbsent(location, new HashMap<>());

        return dataStorage.get(location).get(key);
    }

    public List<Location> getAllBlocksOfType(String id) {
        List<Location> r = new ArrayList<>();

        for(Map.Entry<Location, Map<String, String>> entry : dataStorage.entrySet()) {
            if(getData(entry.getKey(), "id").equals(id)) {
                r.add(entry.getKey());
            }
        }

        return r;
    }

    public List<ModBlockType> getBlocks() {
        return new ArrayList<>(blocks.values());
    }

    public List<Location> getAllLocations() {
        return new ArrayList<>(dataStorage.keySet());
    }

    public void printData() {
        //System.out.println(dataStorage);
    }
}
