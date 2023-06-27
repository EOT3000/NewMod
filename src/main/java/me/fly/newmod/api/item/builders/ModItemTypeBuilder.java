package me.fly.newmod.api.item.builders;

import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.texture.MetaModifier;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ModItemTypeBuilder {
    private Material defaultMaterial;
    private NamespacedKey id;

    private ModBlockType block = null;

    private ItemEventsListener listener = new ItemEventsListener() {};
    private Class<? extends ModItemMeta> meta = DefaultModItemMeta.class;
    private boolean craftable = false;

    private List<MetaModifier<?>> modifiers = new ArrayList<>();

    public ModItemTypeBuilder(Material m, String id, JavaPlugin plugin) {
        this.defaultMaterial = m;
        this.id = new NamespacedKey(plugin, id);
    }


}
