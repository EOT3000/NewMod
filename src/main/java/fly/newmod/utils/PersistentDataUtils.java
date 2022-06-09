package fly.newmod.utils;

import org.bukkit.NamespacedKey;
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

    public static NamespacedKey namespacedKeyFromPrimitive(String s) {
        if(s == null) {
            return null;
        }

        String[] spl = s.split(":");

        return new NamespacedKey(spl[0], spl[1]);
    }

    private PersistentDataUtils() {}
}
