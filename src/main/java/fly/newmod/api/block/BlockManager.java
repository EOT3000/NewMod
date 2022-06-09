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
        ((ModBlockDataSerializer<ModBlockData>) serializers.get(modStack.getClass())).applyData(block, modStack);

        return block;
    }

    public ModBlockType getType(Block block) {
        return blocks.get(PersistentDataUtils.namespacedKeyFromPrimitive(dataStorage.getOrDefault(block.getLocation(), new HashMap<>()).getOrDefault("id", null)));
    }

    public ModBlockData createDefaultMeta(ModBlockType type) {
        return serializers.get(type.getDataType()).defaultMeta();
    }

    public <T extends ModBlockData> void registerSerializer(Class<T> clazz, ModBlockDataSerializer<T> serializer) {
        serializers.put(clazz, serializer);
    }

    public void registerBlock(ModBlockType type) {
        blocks.put(type.getId(), type);
    }

    public void changeData(Location location, String key, String value) {
        dataStorage.putIfAbsent(location, new HashMap<>()).put(key, value);
    }

    public void purgeData(Location location) {
        dataStorage.remove(location);
    }

    public List<String> getAllData(Location location) {
        return new ArrayList<>(dataStorage.putIfAbsent(location, new HashMap<>()).values());
    }


    public String getData(Location location, String key) {
        return dataStorage.putIfAbsent(location, new HashMap<>()).get(key);
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

    public List<Location> getAllLocations() {
        return new ArrayList<>(dataStorage.keySet());
    }
}
