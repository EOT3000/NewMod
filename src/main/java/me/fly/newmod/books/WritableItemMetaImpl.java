package me.fly.newmod.books;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMetaSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;
import java.util.List;

public class WritableItemMetaImpl extends ModItemMeta.AbstractModItemMeta {
    private List<String> text;

    protected WritableItemMetaImpl(ModItemType type, List<String> text) {
        super(type);

        this.text = new ArrayList<>(text);
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    @Override
    public WritableItemMetaImpl cloneItem() {
        return new WritableItemMetaImpl(getType(), text);
    }

    @Override
    public boolean isAcceptable(ModItemMeta meta) {
        return meta.getType() == getType() || getType().equals(meta.getType());
    }

    public static class WritableItemMetaImplSerializer extends ModItemMetaSerializer<WritableItemMetaImpl> {
        public WritableItemMetaImplSerializer() {
            super(WritableItemMetaImpl.class);
        }

        @Override
        public WritableItemMetaImpl getItemMeta(PersistentDataContainer container) {
            ItemManager manager = NewMod.get().getItemManager();

            return new WritableItemMetaImpl(manager.getType(container));
        }

        @Override
        public WritableItemMetaImpl defaultMeta(ModItemType type) {
            return new WritableItemMetaImpl(type);
        }

        @Override
        public boolean applyMeta(ItemStack stack, WritableItemMetaImpl WritableItemMetaImpl) {
            ItemManager manager = NewMod.get().getItemManager();

            return WritableItemMetaImpl.getType().equals(manager.getType(stack));
        }
    }
}
