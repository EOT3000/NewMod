package fly.newmod.bases;

import fly.newmod.NewMod;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.List;

public class ModItem extends ItemStack {
    public static final NamespacedKey ITEM_ID = new NamespacedKey(NewMod.get(), "item_id");

    private String id;
    private String name;

    public ModItem(Material material, String name, String id) {
        super(material);

        ItemMeta meta = this.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        meta.getPersistentDataContainer().set(ITEM_ID, PersistentDataType.STRING, id);

        this.setItemMeta(meta);

        this.id = id.toLowerCase();
        this.name = name;

        if(this instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) this, NewMod.get());
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

        Recipe recipe = new ShapelessRecipe(new NamespacedKey(NewMod.get(), getId()), r);

        for(ItemStack stack : recipeItems) {
            ((ShapelessRecipe) recipe).addIngredient(stack);
        }

        addRecipe(recipe);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    protected void addRecipe(Recipe recipe) {
        Bukkit.addRecipe(recipe);
    }

    public void tick(Location location) {

    }

    public void onPlace(Location location) {

    }

    public void onBreak(Location location) {

    }

    public boolean shouldBeGone(Location location) {
        return true;
    }

    public boolean click(Location location, Action type, Player player, ItemStack stack) {
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
}
