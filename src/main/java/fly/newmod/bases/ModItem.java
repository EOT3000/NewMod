package fly.newmod.bases;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fly.newmod.NewMod;
import fly.newmod.bases.textures.TexturedModItem;
import fly.newmod.setup.BlockStorage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

    @Deprecated
    public ModItem(Material material, String name, String id) {
        this(material, Component.text(name), id);
    }

    @Deprecated
    public ModItem(Material material, String name, String id, ItemStack... recipeItems) {
        this(material, name, id, 1, recipeItems);
    }

    @Deprecated
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


    public ModItem(Material material, String name, int color, String id) {
        this(material, name, TextColor.color(color), id);
    }

    public ModItem(Material material, String name, int color, String id, ItemStack... recipeItems) {
        this(material, name, TextColor.color(color), id, recipeItems);
    }

    public ModItem(Material material, String name, int color, String id, int count, ItemStack... recipeItems) {
        this(material, name, TextColor.color(color), id, count, recipeItems);
    }


    public ModItem(Material material, String name, TextColor color, String id) {
        this(material, Component.text(name, Style.style(color).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)), id);
    }

    public ModItem(Material material, String name, TextColor color, String id, ItemStack... recipeItems) {
        this(material, Component.text(name, Style.style(color).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)), id, recipeItems);
    }

    public ModItem(Material material, String name, TextColor color, String id, int count, ItemStack... recipeItems) {
        this(material, Component.text(name, Style.style(color).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)), id, count, recipeItems);
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

    public ModItem(Material material, Component name, String id, ItemStack... recipeItems) {
        this(material, name, id, 1, recipeItems);
    }

    public ModItem(Material material, Component name, String id, int count, ItemStack... recipeItems) {
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

    public void onUse(PlayerInteractEvent event) {

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
}
