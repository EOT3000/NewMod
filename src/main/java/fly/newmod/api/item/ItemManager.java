package fly.newmod.api.item;

import fly.newmod.NewMod;
import fly.newmod.api.item.meta.ModItemMeta;
import fly.newmod.api.item.meta.ModItemMetaSerializer;
import fly.newmod.api.item.type.ModItemType;
import fly.newmod.utils.PersistentDataUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {
    public static final NamespacedKey ID = new NamespacedKey(NewMod.get(), "id");

    private final Map<Class<? extends ModItemMeta>, ModItemMetaSerializer<?>> serializers = new HashMap<>();
    private final Map<NamespacedKey, ModItemType> items = new HashMap<>();

    public ModItemStack deserializeModItemStack(ItemStack stack) {
        return new ModItemStack(getType(stack), deserializeMeta(stack), stack);
    }

    public ModItemMeta deserializeMeta(ItemStack stack) {
        ModItemType type = getType(stack);

        return serializers.get(type.getMetaType()).getItemMeta(stack.getItemMeta().getPersistentDataContainer());
    }

    @SuppressWarnings("unchecked")
    public ItemStack applyMeta(ItemStack stack, ModItemMeta modStack) {
        ((ModItemMetaSerializer<ModItemMeta>) serializers.get(modStack.getClass())).applyMeta(stack, modStack);

        return stack;
    }

    public ModItemType getType(ItemStack stack) {
        return getType(stack.getItemMeta().getPersistentDataContainer());
    }

    public ModItemType getType(PersistentDataContainer container) {
        if(container.has(ID, PersistentDataUtils.NAMESPACED_KEY)) {
            return getType(container.get(ID, PersistentDataUtils.NAMESPACED_KEY));
        }

        return null;
    }

    public ModItemType getType(NamespacedKey key) {
        return items.get(key);
    }

    public ModItemMeta createDefaultMeta(ModItemType type) {
        return serializers.get(type.getMetaType()).defaultMeta(type);
    }

    public <T extends ModItemMeta> void registerSerializer(Class<T> clazz, ModItemMetaSerializer<T> serializer) {
        serializers.put(clazz, serializer);
    }

    public void registerItem(ModItemType type) {
        items.put(type.getId(), type);
    }

    public List<ModItemType> getItems() {
        return new ArrayList<>(items.values());
    }
}
