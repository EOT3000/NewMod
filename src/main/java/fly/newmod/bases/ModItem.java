package fly.newmod.bases;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fly.newmod.NewMod;
import fly.newmod.bases.textures.TexturedModItem;
import fly.newmod.setup.BlockStorage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.List;

public class ModItem extends ItemStack {
    public static final NamespacedKey ITEM_ID = new NamespacedKey(NewMod.get(), "item_id");
    private BlockStorage blockStorage = NewMod.get().getBlockStorage();

    private String id;
    private Component name;

    public ModItem(Material material, String name, String id) {
        this(material, Component.text(name), id);
    }

    public ModItem(Material material, Component name, String id) {
        super(material);

        ItemMeta meta = this.getItemMeta();

        meta.displayName(name);

        meta.getPersistentDataContainer().set(ITEM_ID, PersistentDataType.STRING, id);

        this.setItemMeta(meta);

        this.id = id.toLowerCase();
        this.name = name;

        if(this instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) this, NewMod.get());
        }

        if(this instanceof TexturedModItem) {
            SkullMeta skullMeta = (SkullMeta) this.getItemMeta();

            skullMeta.setPlayerProfile(((TexturedModItem) this).getTexture().getRawTexture());

            this.setItemMeta(skullMeta);
        }

        NewMod.get().getBlockStorage().registerItem(this);
    }

    public ModItem(Material material, String name, String id, ItemStack... recipeItems) {
        this(material, name, id, 1, recipeItems);
    }

    public ModItem(Material material, String name, String id, int count, ItemStack... recipeItems) {
        this(material, name, id);

        ItemStack r = new ItemStack(this);

        r.setAmount(count);

        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(NewMod.get(), getId()), r);

        for(ItemStack stack : recipeItems) {
            recipe.addIngredient(stack);
        }

        addRecipe(recipe);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return PlainTextComponentSerializer.plainText().serialize(name);
    }

    public Component getComponentName() {
        return name;
    }

    protected void addRecipe(Recipe recipe) {
        Bukkit.addRecipe(recipe);
    }

    public void tick(Location location, int count) {

    }

    public void onPlace(BlockPlaceEvent event) {

    }

    public void onBreak(BlockBreakEvent event) {

    }

    public void onInteract(PlayerInteractEvent event) {

    }

    public boolean shouldBeGone(Location location) {
        return true;
    }


    public List<Material> getValidMaterials() {
        return Collections.singletonList(getType());
    }

    public static ItemStack newStack(String name, Material material) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        stack.setItemMeta(meta);

        return stack;
    }

    public static boolean matches(ItemStack a, ItemStack b) {
        if(a == b) {
            return true;
        }

        if(a == null || b == null) {
            return false;
        }

        String aid = a.getItemMeta().getPersistentDataContainer().getOrDefault(ITEM_ID, PersistentDataType.STRING, "none");
        String bid = b.getItemMeta().getPersistentDataContainer().getOrDefault(ITEM_ID, PersistentDataType.STRING, "none");

        return aid.equalsIgnoreCase("none") ? a.isSimilar(b) : aid.equalsIgnoreCase(bid);
    }

    //EVENTS

    @EventHandler
    public void onBlockPlaceE(BlockPlaceEvent event) {
        PersistentDataContainer cont = event.getItemInHand().getItemMeta().getPersistentDataContainer();

        if(cont.has(ModItem.ITEM_ID, PersistentDataType.STRING)) {
            String id = cont.get(ModItem.ITEM_ID, PersistentDataType.STRING);

            if(blockStorage.getItems().get(id).getValidMaterials().contains(event.getBlock().getType())) {
                blockStorage.changeData(event.getBlock().getLocation(), "id", id);

                ((ModItem) blockStorage.getType(id)).onPlace(event);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreakE(BlockBreakEvent event) {
        String id = blockStorage.getData(event.getBlock().getLocation(), "id");

        if(!id.isEmpty()) {
            if(((ModItem) blockStorage.getType(id)).shouldBeGone(event.getBlock().getLocation())) {
                ((ModItem) blockStorage.getType(id)).onBreak(event);

                event.setDropItems(false);
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), blockStorage.getType(blockStorage.getData(event.getBlock().getLocation(), "id")));

                blockStorage.removeData(event.getBlock().getLocation());
            } else {
                ((ModItem) blockStorage.getType(id)).onBreak(event);

                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onTickE(ServerTickStartEvent event) {
        if(event.getTickNumber() % 5 == 0) {
            for (Location location : blockStorage.getAllLocations()) {
                ModItem item = (ModItem) blockStorage.getType(blockStorage.getData(location, "id"));

                if (!item.getValidMaterials().contains(location.getBlock().getType())) {
                    blockStorage.removeData(location);
                    location.getBlock().setType(Material.AIR);
                    continue;
                }

                item.tick(location.clone(), event.getTickNumber());
            }
        }
    }

    @EventHandler
    public void onInteractE(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null || blockStorage.getData(event.getClickedBlock().getLocation()).isEmpty() || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }

        ModItem item = (ModItem) blockStorage.getType(blockStorage.getData(event.getClickedBlock().getLocation(), "id"));

        item.onInteract(event);
    }
}
