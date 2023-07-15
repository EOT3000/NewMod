package me.fly.newmod.books;

import me.fly.newmod.BookTypes;
import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ModItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;

public class BookListener implements Listener {
    // The base behaviours - add a book when the bark is in your hand, remove it when it's not, and apply data when it's finished

    @EventHandler
    public void onHotbarSwitch(PlayerItemHeldEvent event) {
        PlayerInventory inv = event.getPlayer().getInventory();

        ItemStack stack = inv.getItem(event.getNewSlot());

        if (BookUtils.writableBark(stack)) {
            BookUtils.putBook(inv);
        } else if (BookUtils.isBook(inv.getItemInOffHand())) {
            inv.setItemInOffHand(null);
        }
    }

    @EventHandler
    public void onBookEdit(PlayerEditBookEvent event) {
        if(event.getNewBookMeta().getPersistentDataContainer().getOrDefault(BookUtils.OFFHAND_ONLY, PersistentDataType.BOOLEAN, false)) {
            PlayerInventory inv = event.getPlayer().getInventory();

            if(!BookUtils.writableBark(inv.getItemInMainHand())) {
                Bukkit.getScheduler().runTaskLater(NewMod.get(), () -> {
                    if(BookUtils.isBook(inv.getItemInOffHand())) {
                        inv.setItemInOffHand(null);
                    }
                }, 1);
                return;
            }

            if(inv.getItemInMainHand().getAmount() == 1) {
                BookUtils.finishWrite(inv, event.getNewBookMeta());
            } else {
                ItemStack add = BookUtils.finishWriteAdd(inv, event.getNewBookMeta());

                inv.getItemInMainHand().setAmount(inv.getItemInMainHand().getAmount()-1);

                Collection<ItemStack> drop = inv.addItem(add).values();

                if(!drop.isEmpty()) {
                    for(ItemStack stack : drop) {
                        event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), stack);
                    }
                }
            }
        }
    }

    // Other behaviours, in case something goes wrong, or a player tries to dupe items

    @EventHandler
    public void onHandSwitch(PlayerSwapHandItemsEvent event) {
        if(BookUtils.isBook(event.getMainHandItem()) || BookUtils.isBook(event.getOffHandItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if(BookUtils.isBook(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(BookUtils.isBook(event.getCurrentItem())) {
            if(event.getSlot() == 40 && event.getClickedInventory() instanceof PlayerInventory) {
                event.setCancelled(true);

                Bukkit.getScheduler().runTaskLater(NewMod.get(), () -> {
                    if (!BookUtils.writableBark(event.getWhoClicked().getInventory().getItemInMainHand())
                            && BookUtils.isBook(event.getWhoClicked().getInventory().getItemInOffHand())) {
                        event.getWhoClicked().getInventory().setItemInOffHand(null);
                    }
                }, 1);
            }
        } else {
            ItemStack placed = event.getCursor();

            if(BookUtils.isBook(placed)) {
                if(event.getClickedInventory() == null) {
                    return;
                }

                Bukkit.getScheduler().runTaskLater(NewMod.get(), () -> {
                    HumanEntity p = event.getWhoClicked();

                    ItemStack stack = p.getOpenInventory().getItem(event.getRawSlot());

                    if(BookUtils.isBook(stack)) {
                        p.getOpenInventory().setItem(event.getRawSlot(), null);
                    }

                }, 1);
            }
        }

        if(BookUtils.isBook(event.getWhoClicked().getInventory().getItemInOffHand())) {
            Bukkit.getScheduler().runTaskLater(NewMod.get(), () -> {
                if (!BookUtils.writableBark(event.getWhoClicked().getInventory().getItemInMainHand())) {
                    event.getWhoClicked().getInventory().setItemInOffHand(null);
                }
            }, 1);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        //Thank you intellij idea for this beautiful code
        event.getDrops().removeIf(BookUtils::isBook);
    }
}
