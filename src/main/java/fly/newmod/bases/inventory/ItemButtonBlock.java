package fly.newmod.bases.inventory;

import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class ItemButtonBlock extends ModItem implements Listener {
    private NamespacedKey INVENTORY_ID = new NamespacedKey(NewMod.get(), "inventory-id");

    private Map<Integer, InventorySlot> slots = new HashMap<>();
    private ItemStack fill;

    public ItemButtonBlock(Material material, String name, String id) {
        super(material, name, id);
    }

    public ItemButtonBlock setSlot(int x, InventorySlot s) {
        slots.put(x, s);

        return this;
    }

    public ItemButtonBlock setFill(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();

        meta.getPersistentDataContainer().set(INVENTORY_ID, PersistentDataType.STRING, getId());

        stack.setItemMeta(meta);

        this.fill = stack;

        return this;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) {
            return;
        }

        ItemStack item0 = event.getClickedInventory().getItem(0);

        if(item0 != null && item0.getItemMeta().getPersistentDataContainer().getOrDefault(INVENTORY_ID, PersistentDataType.STRING, "none").equalsIgnoreCase(getId())) {
            InventorySlot slot = slots.get(event.getSlot());

            if(slot == null) {
                event.setCancelled(true);
            } else {
                if(slot instanceof ButtonSlot) {
                    Map<Integer, ItemStack> items = new HashMap<>();

                    for(Map.Entry<Integer, InventorySlot> iter : slots.entrySet()) {
                        if(iter.getValue() instanceof StorageSlot) {
                            items.put(iter.getKey(), iter.getValue().get(event.getClickedInventory()));
                        }
                    }

                    ((ButtonSlot) slot).run(items, event.getClick(), event.getClickedInventory());

                    event.setCancelled(true);
                }
            }
        }
    }

    public final void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, getComponentName());

        for(int x = 0; x < 54; x++) {
            if(x != 0) {
                inventory.setItem(x, slots.getOrDefault(x, (a) -> fill).get(inventory));
            } else {
                inventory.setItem(x, fill);
            }
        }

        player.openInventory(inventory);
    }

    public InventorySlot getInventorySlot(int x) {
        return slots.get(x);
    }

    public interface InventorySlot {
        ItemStack get(Inventory inventory);
    }

    public static abstract class StorageSlot implements InventorySlot {
        private int number;

        public StorageSlot(int number) {
            this.number = number;
        }

        public abstract boolean canHold(ItemStack stack, Inventory context);

        public void set(Inventory inventory, ItemStack stack) {
            if(canHold(stack, inventory)) {
                inventory.setItem(number, stack);
            }
        }

        @Override
        public ItemStack get(Inventory inventory) {
            return inventory.getItem(number);
        }
    }

    public static abstract class ButtonSlot implements InventorySlot {
        public abstract void run(Map<Integer, ItemStack> items, ClickType type, Inventory context);
    }
}
