package me.fly.newmod.books;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMetaSerializer;
import me.fly.newmod.api.util.PersistentDataUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class WritableItemMetaImpl extends ModItemMeta.AbstractModItemMeta implements WritableItemMeta {
    private String[] text;
    private boolean signed;

    protected WritableItemMetaImpl(ModItemType type, String[] text, boolean signed) {
        super(type);

        this.text = text.clone();
        this.signed = signed;
    }

    @Override
    public String[] getText() {
        return text;
    }

    @Override
    public void setText(String[] text) {
        this.text = text;
    }

    @Override
    public boolean isSigned() {
        return signed;
    }

    @Override
    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    @Override
    public WritableItemMetaImpl cloneItem() {
        return new WritableItemMetaImpl(getType(), text, signed);
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
            ModItemType type = manager.getType(container);

            return new WritableItemMetaImpl(type, container.getOrDefault(TEXT, PersistentDataUtils.STRING_ARRAY, new String[0]), container.getOrDefault(SIGNED, PersistentDataType.BOOLEAN, false));
        }

        @Override
        public WritableItemMetaImpl defaultMeta(ModItemType type) {
            return new WritableItemMetaImpl(type, new String[0], false);
        }

        @Override
        public boolean applyMeta(ItemStack stack, WritableItemMetaImpl WritableItemMetaImpl) {
            ItemManager manager = NewMod.get().getItemManager();

            return WritableItemMetaImpl.getType().equals(manager.getType(stack));
        }
    }
}
