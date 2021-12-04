package fly.newmod.setup;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import fly.newmod.bases.inventory.ItemButtonBlock;
import fly.newmod.impl.items.MetalNugget;
import fly.newmod.impl.machines.DatingMachine;
import fly.newmod.utils.Pair;
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

public class BlockStorage implements Listener {
    private Map<String, ModItem> items = new LinkedHashMap<>();
    private Map<Location, Map<String, String>> blocks = new HashMap<>();

    private int bn;
    public Map<Material, Map<ItemStack, Double>> oreMap = new LinkedHashMap<>();
    private Map<ItemStack, Double> otherPercentages = new HashMap<>();

    public void init() {
        File file = new File("plugins\\NewMod\\config.yml");

        try {
            if (!file.exists()) {
                file.createNewFile();

                FileOutputStream outputStream = new FileOutputStream(file);
                
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yml");
                
                byte[] bytes = new byte[inputStream.available()];
                
                inputStream.read(bytes);
                
                outputStream.write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        
        bn = configuration.getInt("block-nuggets");

        ConfigurationSection ores = configuration.getConfigurationSection("ores");

        for(String oreKey : ores.getKeys(false)) {
            ConfigurationSection oreData = ores.getConfigurationSection(oreKey);

            oreMap.put(Material.valueOf(oreKey), new HashMap<>());

            double d = 0;

            for(String metalKey : oreData.getKeys(false)) {
                double metalData = oreData.getDouble(metalKey);

                System.out.println(oreKey + ", " + metalKey + ", " + metalData);

                d += metalData;

                oreMap.get(Material.valueOf(oreKey)).put(getTypeE(metalKey), metalData);
            }

            if(d != 1) {
                System.err.println("Error on init of " + oreKey + ", values do not equal 1, equals " + d);
            }
        }
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

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        PersistentDataContainer cont = event.getItemInHand().getItemMeta().getPersistentDataContainer();

        if(cont.has(ModItem.ITEM_ID, PersistentDataType.STRING)) {
            String id = cont.get(ModItem.ITEM_ID, PersistentDataType.STRING);

            if(getItems().get(id).getValidMaterials().contains(event.getBlock().getType())) {
                changeData(event.getBlock().getLocation(), "id", id);

                ((ModItem) getType(id)).onPlace(event.getBlock().getLocation());
            } else {
                event.setCancelled(true);
            }
        }

        if(cont.has(DatingMachine.LINE_1_NAMESPACE, PersistentDataType.STRING)) {
            Sign sign = ((Sign) event.getBlock().getState());

            //TODO: fix deprecation

            sign.setLine(0, cont.get(DatingMachine.LINE_1_NAMESPACE, PersistentDataType.STRING));
            sign.setLine(1, cont.get(DatingMachine.LINE_2_NAMESPACE, PersistentDataType.STRING));
            sign.setLine(2, cont.get(DatingMachine.LINE_3_NAMESPACE, PersistentDataType.STRING));
            sign.setLine(3, cont.get(DatingMachine.LINE_4_NAMESPACE, PersistentDataType.STRING));
            sign.setColor(DyeColor.valueOf(cont.get(DatingMachine.COLOR_NAMESPACE, PersistentDataType.STRING)));

            sign.getPersistentDataContainer().set(DatingMachine.DATE_NAMESPACE, PersistentDataType.LONG, cont.get(DatingMachine.DATE_NAMESPACE, PersistentDataType.LONG));

            sign.update();
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if(event.getItem().getItemMeta().getPersistentDataContainer().getOrDefault(ModItem.ITEM_ID, PersistentDataType.STRING, "a").equalsIgnoreCase("drug_potato")) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15000, 2));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        String id = getData(event.getBlock().getLocation(), "id");

        if(!id.isEmpty()) {
            if(((ModItem) getType(id)).shouldBeGone(event.getBlock().getLocation())) {
                ((ModItem) getType(id)).onBreak(event.getBlock().getLocation());

                event.setDropItems(false);
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), items.get(getData(event.getBlock().getLocation(), "id")));

                removeData(event.getBlock().getLocation());
            } else {
                ((ModItem) getType(id)).onBreak(event.getBlock().getLocation());

                event.setCancelled(true);
                return;
            }

        }

        BlockState state = event.getBlock().getState();
        CoreProtectAPI coApi = CoreProtect.getInstance().getAPI();

        if(state instanceof Sign && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getEnchants().containsKey(Enchantment.SILK_TOUCH)) {
            PersistentDataContainer signContainer = ((Sign) state).getPersistentDataContainer();

            String l1 = ((Sign) state).getLine(0);
            String l2 = ((Sign) state).getLine(1);
            String l3 = ((Sign) state).getLine(2);
            String l4 = ((Sign) state).getLine(3);

            long age = -1;

            if(signContainer.has(DatingMachine.DATE_NAMESPACE, PersistentDataType.LONG)) {
                age = signContainer.get(DatingMachine.DATE_NAMESPACE, PersistentDataType.LONG);
            }

            for(String[] s : coApi.blockLookup(event.getBlock(), Integer.MAX_VALUE)) {
                CoreProtectAPI.ParseResult result = coApi.new ParseResult(s);

                if(result.getActionId() == 1 && age == -1) {
                    age = ((long) result.getTime()*1000);
                    System.out.println(result.getTime());
                    break;
                }
            }
            ItemStack sign = DatingMachine.tag(age, new ItemStack(Material.valueOf(event.getBlock().getType().name().replaceFirst("WALL_", ""))), DatingMachine.DATE_NAMESPACE, PersistentDataType.LONG);
            ItemMeta meta = sign.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();


            container.set(DatingMachine.LINE_1_NAMESPACE, PersistentDataType.STRING, l1);
            container.set(DatingMachine.LINE_2_NAMESPACE, PersistentDataType.STRING, l2);
            container.set(DatingMachine.LINE_3_NAMESPACE, PersistentDataType.STRING, l3);
            container.set(DatingMachine.LINE_4_NAMESPACE, PersistentDataType.STRING, l4);
            container.set(DatingMachine.COLOR_NAMESPACE, PersistentDataType.STRING, ((Sign) state).getColor().name());

            sign.setItemMeta(meta);
            event.setDropItems(false);
            event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), sign);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void potionItemPlacer(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null)
            return;
        if (e.getClickedInventory().getType() != InventoryType.BREWING)
            return;
        if(e.getSlot() != 3)
            return;

        System.out.println(e.getResult());
    }

    @EventHandler
    public void onTick(ServerTickStartEvent event) {
        if(event.getTickNumber() % 5 == 0) {
            for (Location location : blocks.keySet()) {
                ModItem item = items.get(blocks.get(location).get("id"));

                if (!item.getValidMaterials().contains(location.getBlock().getType())) {
                    removeData(location);
                    location.getBlock().setType(Material.AIR);
                    continue;
                }

                item.tick(location.clone());
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null || blocks.get(event.getClickedBlock().getLocation()) == null || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }

        ModItem item = items.get(blocks.get(event.getClickedBlock().getLocation()).get("id"));

        event.setCancelled(!item.click(event.getClickedBlock().getLocation(), event.getAction(), event.getPlayer(), event.getItem()));

        if(item instanceof ItemButtonBlock) {
            event.setUseInteractedBlock(Event.Result.DENY);
        }
    }
}
