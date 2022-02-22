package fly.newmod.setup;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;


public class BlockStorage implements Listener {
    private Map<String, ModItem> items = new LinkedHashMap<>();
    private Map<Location, Map<String, String>> blocks = new HashMap<>();

    public void init() {

    }

    public static boolean isSimilar(ItemStack stack, ItemStack stack2) {
        return getID(stack).equalsIgnoreCase(getID(stack2));
    }

    public static String getID(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();

        return meta.getPersistentDataContainer().get(ModItem.ITEM_ID, PersistentDataType.STRING);
    }

    public ItemStack getTypeE(String string) {
        return string.equalsIgnoreCase("OTHER") ? null : getType(string);
    }
    
    public ItemStack getType(String string) {
        return items.get(string.toLowerCase()) == null ? new ItemStack(Material.valueOf(string)) : items.get(string.toLowerCase());
    }

    public void registerItem(ModItem item) {
        NewMod.get().getLogger().info("Registered New Item: " + item.getId());

        items.put(item.getId(), item);
    }

    public Map<String, ModItem> getItems() {
        return new LinkedHashMap<>(items);
    }

    public void removeData(Location location) {
        blocks.remove(location);
    }

    public void changeData(Location location, String key, String value) {
        blocks.computeIfAbsent(location, (x) -> new HashMap<>()).put(key, value);
    }

    public String getData(Location location, String key) {
        return blocks.getOrDefault(location, new HashMap<>()).getOrDefault(key, "");
    }

    public List<String> getData(Location location) {
        return new ArrayList<>(blocks.getOrDefault(location, new HashMap<>()).keySet());
    }

    public List<Location> getAllBlocksOfType(String id) {
        List<Location> r = new ArrayList<>();

        for(Map.Entry<Location, Map<String, String>> entry : blocks.entrySet()) {
            if(getData(entry.getKey(), "id").equals(id)) {
                r.add(entry.getKey());
            }
        }

        return r;
    }

    public List<Location> getAllLocations() {
        return new ArrayList<>(blocks.keySet());
    }

    public boolean isModItem(ItemStack stack) {
        PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();

        return container.has(ModItem.ITEM_ID, PersistentDataType.STRING);
    }

    //EVENTS

    @EventHandler
    public void onBlockPlaceE(BlockPlaceEvent event) {
        PersistentDataContainer cont = event.getItemInHand().getItemMeta().getPersistentDataContainer();

        if(cont.has(ModItem.ITEM_ID, PersistentDataType.STRING)) {
            String id = cont.get(ModItem.ITEM_ID, PersistentDataType.STRING);

            if(items.get(id).getValidMaterials().contains(event.getBlock().getType())) {
                changeData(event.getBlock().getLocation(), "id", id);

                ((ModItem) getType(id)).onPlace(event);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreakE(BlockBreakEvent event) {
        String id = getData(event.getBlock().getLocation(), "id");

        if(!id.isEmpty()) {
            if(((ModItem) getType(id)).shouldBeGone(event.getBlock().getLocation())) {
                ((ModItem) getType(id)).onBreak(event);

                event.setDropItems(false);
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), getType(getData(event.getBlock().getLocation(), "id")));

                removeData(event.getBlock().getLocation());
            } else {
                ((ModItem) getType(id)).onBreak(event);

                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onTickE(ServerTickStartEvent event) {
        if(event.getTickNumber() % 5 == 0) {
            for (Location location : getAllLocations()) {
                ModItem item = (ModItem) getType(getData(location, "id"));

                if (!item.getValidMaterials().contains(location.getBlock().getType())) {
                    removeData(location);
                    location.getBlock().setType(Material.AIR);
                    continue;
                }

                item.tick(location.clone(), event.getTickNumber());
            }
        }
    }

    @EventHandler
    public void onInteractE(PlayerInteractEvent event) {
        if(event.getItem() != null && isModItem(event.getItem())) {
            ((ModItem) getType(BlockStorage.getID(event.getItem()))).onUse(event);
        }

        if(event.getClickedBlock() == null || getData(event.getClickedBlock().getLocation()).isEmpty()) {
            return;
        }

        ModItem item = (ModItem) getType(getData(event.getClickedBlock().getLocation(), "id"));

        item.onInteract(event);
    }
}
