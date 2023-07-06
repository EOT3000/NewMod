package me.fly.newmod.api.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class PersistentDataUtils {
    public static final PersistentDataType<String, NamespacedKey> NAMESPACED_KEY = new PersistentDataType<>() {
        @Override
        public @NotNull Class<String> getPrimitiveType() {
            return String.class;
        }

        @Override
        public @NotNull Class<NamespacedKey> getComplexType() {
            return NamespacedKey.class;
        }

        @Override
        public @NotNull String toPrimitive(@NotNull NamespacedKey namespacedKey, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            return namespacedKey.getNamespace() + ":" + namespacedKey.getKey();
        }

        @Override
        public @NotNull NamespacedKey fromPrimitive(@NotNull String s, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            String[] spl = s.split(":");

            return new NamespacedKey(spl[0], spl[1]);
        }
    };

    public static final PersistentDataType<String, ItemStack[]> ITEM_ARRAY = new PersistentDataType<>() {
        @Override
        public @NotNull Class<String> getPrimitiveType() {
            return String.class;
        }

        @Override
        public @NotNull Class<ItemStack[]> getComplexType() {
            return ItemStack[].class;
        }

        @Override
        public @NotNull String toPrimitive(@NotNull ItemStack[] list, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            return NMSUtils.toBase64List(list);
        }

        @Override
        public @NotNull ItemStack[] fromPrimitive(@NotNull String s, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            return NMSUtils.fromBase64List(s);
        }
    };

    public static NamespacedKey namespacedKeyFromPrimitive(String s) {
        if(s == null) {
            return null;
        }

        String[] spl = s.split(":");

        return new NamespacedKey(spl[0], spl[1]);
    }

    private PersistentDataUtils() {}
}
