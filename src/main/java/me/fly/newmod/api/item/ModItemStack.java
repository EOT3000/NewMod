package me.fly.newmod.api.item;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.meta.ModItemMeta;
import me.fly.newmod.api.util.PersistentDataUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Represents an item stack with mod properties.
 *
 * Instances of this class should not be stored; instead a new ModItemStack should be created when the need arises
 *
 * @see ItemManager#deserializeModItemStack
 */
public final class ModItemStack {
    private final ItemStack representation;

    private final ModItemType type;
    private ModItemMeta meta;
    private int amount;

    public static ModItemStack makeOrNull(ItemStack stack) {
        ItemManager manager = NewMod.get().getItemManager();

        if(manager.getType(stack) == null) {
            return null;
        }

        return new ModItemStack(stack);
    }

    public ModItemStack(ItemStack representation) {
        ItemManager manager = NewMod.get().getItemManager();

        this.representation = representation;

        this.type = manager.getType(representation);
        this.meta = manager.deserializeMeta(representation);
        this.amount = representation.getAmount();
    }

    public ModItemStack(ModItemType type) {
        ItemManager manager = NewMod.get().getItemManager();

        this.representation = null;

        this.type = type;
        this.meta = manager.createDefaultMeta(type);
        this.amount = 1;
    }

    public ModItemStack(ModItemType type, ModItemMeta meta, ItemStack representation) {
        this.representation = representation;

        this.type = type;
        this.meta = meta;
        this.amount = representation.getAmount();
    }

    public void setAmount(int amount) {
        if(representation == null) {
            this.amount = amount;
        } else {
            representation.setAmount(amount);

            this.amount = representation.getAmount();
        }
    }

    public ItemStack create() {
        if(representation == null) {
            ItemManager manager = NewMod.get().getItemManager();
            ItemStack stack = new ItemStack(type.getDefaultMaterial(), amount);
            ItemMeta itemMeta = stack.getItemMeta();

            itemMeta.getPersistentDataContainer().set(ItemManager.ID, PersistentDataUtils.NAMESPACED_KEY, type.getId());

            stack.setItemMeta(itemMeta);

            manager.applyMeta(stack, meta);

            type.applyModifiers(stack);

            return stack;
        } else {
            return representation;
        }
    }

    public void update() {
        if(hasPhysicalRepresentation()) {
            ItemManager manager = NewMod.get().getItemManager();

            manager.applyMeta(representation, meta);
        }
    }

    public ModItemType getType() {
        return type;
    }

    public boolean hasPhysicalRepresentation() {
        return representation != null;
    }

    public int getAmount() {
        return amount;
    }

    public void setMeta(ModItemMeta meta) {
        this.meta = meta;
    }

    public ModItemMeta getMeta() {
        return meta.cloneItem();
    }
}
