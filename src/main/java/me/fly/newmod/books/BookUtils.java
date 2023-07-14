package me.fly.newmod.books;

import me.fly.newmod.BookTypes;
import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.util.PersistentDataUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BookUtils {
    public static final NamespacedKey OFFHAND_ONLY = new NamespacedKey(NewMod.get(), "offhand_only");
    public static final NamespacedKey PAGES = new NamespacedKey(NewMod.get(), "pages");
    public static final NamespacedKey ID = new NamespacedKey(NewMod.get(), "book_id");
    public static final NamespacedKey SIGNED = new NamespacedKey(NewMod.get(), "signed");

    public static void finishWrite(PlayerInventory inventory, BookMeta newBook) {
        Bukkit.getScheduler().runTaskLater(NewMod.get(), () -> {
            inventory.setItemInOffHand(null);
        }, 1);

        ItemMeta meta = inventory.getItemInMainHand().getItemMeta();

        meta.getPersistentDataContainer().set(PAGES, PersistentDataUtils.COMPONENT, newBook.page(1));
        meta.getPersistentDataContainer().set(SIGNED, PersistentDataType.BOOLEAN, true);

        inventory.getItemInMainHand().setItemMeta(meta);
    }

    public static ItemStack finishWriteAdd(PlayerInventory inventory, BookMeta newBook) {
        Bukkit.getScheduler().runTaskLater(NewMod.get(), () -> {
            inventory.setItemInOffHand(null);
        }, 1);

        ItemStack stack = new ItemStack(inventory.getItemInMainHand());

        stack.setAmount(1);

        ItemMeta meta = stack.getItemMeta();

        meta.getPersistentDataContainer().set(PAGES, PersistentDataUtils.COMPONENT, newBook.page(1));
        meta.getPersistentDataContainer().set(SIGNED, PersistentDataType.BOOLEAN, true);

        stack.setItemMeta(meta);

        return stack;
    }

    public static boolean writableBark(ItemStack stack) {
        if(stack == null || !stack.hasItemMeta()) {
            return false;
        }

        ModItemType type = NewMod.get().getItemManager().getType(stack);

        if(BookTypes.BIRCH_BARK.equals(type)) {
            return !stack.getItemMeta().getPersistentDataContainer().getOrDefault(SIGNED, PersistentDataType.BOOLEAN, false);
        }

        return false;
    }

    public static boolean isBook(ItemStack stack) {
        if(stack == null || !stack.hasItemMeta()) {
            return false;
        }

        return stack.getType().equals(Material.WRITABLE_BOOK) &&
                stack.getItemMeta().getPersistentDataContainer().getOrDefault(OFFHAND_ONLY, PersistentDataType.BOOLEAN, false);
    }

    public static void putBook(PlayerInventory inventory) {
        Material type = inventory.getItemInOffHand().getType();

        if(type.equals(Material.AIR) || isBook(inventory.getItemInOffHand())) {
            ItemStack writableBook = new ItemStack(Material.WRITABLE_BOOK);
            BookMeta meta = (BookMeta) writableBook.getItemMeta();

            meta.pages(Component.text(""),
                    Component.text("Writing on this or subsequent pages will not be saved. Only write on page 1.").color(TextColor.color(0xFF0000)));
            meta.getPersistentDataContainer().set(OFFHAND_ONLY, PersistentDataType.BOOLEAN, true);

            writableBook.setItemMeta(meta);

            inventory.setItemInOffHand(writableBook);
        }
    }
}
