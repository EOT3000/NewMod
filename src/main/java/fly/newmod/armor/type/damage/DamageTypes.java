package fly.newmod.armor.type.damage;

import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

public final class DamageTypes {
    public Map<NamespacedKey, DamageType> types = new HashMap<>();

    public void addFlag(NamespacedKey key, DamageType type) {
        types.put(key, type);
    }

    public DamageType getFlag(NamespacedKey key) {
        return types.get(key);
    }
}
