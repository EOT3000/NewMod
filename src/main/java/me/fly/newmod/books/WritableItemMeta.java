package me.fly.newmod.books;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.meta.ModItemMeta;
import org.bukkit.NamespacedKey;

public interface WritableItemMeta extends ModItemMeta {
    NamespacedKey TEXT = new NamespacedKey(NewMod.get(), "text");
    NamespacedKey SIGNED = new NamespacedKey(NewMod.get(), "signed");

    String[] getText();

    void setText(String[] text);

    boolean isSigned();

    void setSigned(boolean signed);
}
