package me.fly.newmod.api.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
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

    public static final PersistentDataType<String, Component> COMPONENT = new PersistentDataType<>() {
        @Override
        public @NotNull Class<String> getPrimitiveType() {
            return String.class;
        }

        @Override
        public @NotNull Class<Component> getComplexType() {
            return Component.class;
        }

        @Override
        public @NotNull String toPrimitive(@NotNull Component component, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            return GsonComponentSerializer.gson().serialize(component);
        }

        @Override
        public @NotNull Component fromPrimitive(@NotNull String s, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            return GsonComponentSerializer.gson().deserialize(s);
        }
    };

    public static final PersistentDataType<String[], String[]> STRING_ARRAY = new PersistentDataType<String[], String[]>() {
        @Override
        public @NotNull Class<String[]> getPrimitiveType() {
            return String[].class;
        }

        @Override
        public @NotNull Class<String[]> getComplexType() {
            return String[].class;
        }

        @Override
        public String @NotNull [] toPrimitive(String @NotNull [] strings, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            return strings;
        }

        @Override
        public String @NotNull [] fromPrimitive(String @NotNull [] strings, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
            return strings;
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
