package me.fly.newmod.metals.items.meta;

public class FilledOreSpongeMeta {

}

/*import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.item.meta.ModItemMetaSerializer;
import me.fly.newmod.metals.MetalsPlugin;
import me.fly.newmod.metals.items.FilledOreSponge;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class FilledOreSpongeMeta extends ModItemMeta.AbstractModItemMeta {
    public static final NamespacedKey ORE = new NamespacedKey(MetalsPlugin.get(), "ore");

    private Material material;

    protected FilledOreSpongeMeta(ModItemType type, Material material) {
        super(type);

        //System.out.println(material);

        //new RuntimeException().printStackTrace();

        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public FilledOreSpongeMeta cloneItem() {
        return new FilledOreSpongeMeta(getType(), material);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FilledOreSpongeMeta that = (FilledOreSpongeMeta) o;
        return material == that.material && getType().equals(that.getType());
    }

    @Override
    public boolean isAcceptable(ModItemMeta modItemMeta) {
        if(!(modItemMeta instanceof FilledOreSpongeMeta)) {
            return false;
        }

        return (modItemMeta.getType() == getType() || getType().equals(modItemMeta.getType())) && ((FilledOreSpongeMeta) modItemMeta).material == material;
    }

    public static class FilledOreSpongeMetaSerializer extends ModItemMetaSerializer<FilledOreSpongeMeta> {
        public FilledOreSpongeMetaSerializer() {
            super(FilledOreSpongeMeta.class);
        }

        @Override
        public FilledOreSpongeMeta getItemMeta(PersistentDataContainer container) {
            ItemManager manager = NewMod.get().getItemManager();

            return new FilledOreSpongeMeta(manager.getType(container), Material.valueOf(container.get(ORE, PersistentDataType.STRING)));
        }

        @Override
        public FilledOreSpongeMeta defaultMeta(ModItemType type) {
            return new FilledOreSpongeMeta(type, Material.AIR);
        }

        @Override
        public boolean applyMeta(ItemStack itemStack, FilledOreSpongeMeta fmeta) {
            if(fmeta == null) {
                return false;
            } else {
                ItemMeta meta = itemStack.getItemMeta();

                meta.lore(Arrays.asList(Component.text(""), Component.text(FilledOreSponge.getVariantName(fmeta.material))));
                meta.getPersistentDataContainer().set(ORE, PersistentDataType.STRING, fmeta.material.name());

                itemStack.setItemMeta(meta);

                return true;
            }
        }
    }
}*/
