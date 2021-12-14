package fly.newmod.setup;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fly.newmod.bases.ModItem;
import fly.newmod.bases.inventory.ItemButtonBlock;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

public class BlockStorage {
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
}
