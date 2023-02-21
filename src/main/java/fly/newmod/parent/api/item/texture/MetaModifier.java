package fly.newmod.parent.api.item.texture;

import org.bukkit.inventory.meta.ItemMeta;

public final class MetaModifier<T> {
    private final T information;
    private final MetaModifierType<T> flag;

    public MetaModifier(T information, MetaModifierType<T> flag) {
        this.information = information;
        this.flag = flag;
    }

    public MetaModifierType<T> getFlag() {
        return flag;
    }

    public T getInformation() {
        return information;
    }

    public ItemMeta apply(ItemMeta meta) {
        return flag.apply(information, meta);
    }

    public interface MetaModifierType<T> {
        ItemMeta apply(T t, ItemMeta meta);
    }
}
