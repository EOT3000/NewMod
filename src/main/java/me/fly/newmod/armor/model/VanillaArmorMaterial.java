package me.fly.newmod.armor.model;

import me.fly.newmod.api.item.ItemWithProperties;
import me.fly.newmod.api.item.VanillaItemWithProperties;
import me.fly.newmod.api.item.VanillaOrModItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.*;

public enum VanillaArmorMaterial implements ArmorMaterial {
    LEATHER(Material.LEATHER, LEATHER_BOOTS, LEATHER_LEGGINGS, LEATHER_CHESTPLATE, LEATHER_HELMET),

    GOLDEN(GOLD_INGOT, GOLDEN_BOOTS, GOLDEN_LEGGINGS, GOLDEN_CHESTPLATE, GOLDEN_HELMET),

    CHAINMAIL(CHAIN, CHAINMAIL_BOOTS, CHAINMAIL_LEGGINGS, CHAINMAIL_CHESTPLATE, CHAINMAIL_HELMET),

    IRON(IRON_INGOT, IRON_BOOTS, IRON_LEGGINGS, IRON_CHESTPLATE, IRON_HELMET),

    DIAMOND(Material.DIAMOND, DIAMOND_BOOTS, DIAMOND_LEGGINGS, DIAMOND_CHESTPLATE, DIAMOND_HELMET),

    NETHERITE(NETHERITE_INGOT, NETHERITE_BOOTS, NETHERITE_LEGGINGS, NETHERITE_CHESTPLATE, NETHERITE_HELMET);

    private final Material crafting;

    private final VanillaArmorPiece boots;
    private final VanillaArmorPiece legs;
    private final VanillaArmorPiece chest;
    private final VanillaArmorPiece head;

    VanillaArmorMaterial(Material crafting, Material boots, Material legs, Material chest, Material head) {
        this.crafting = crafting;

        if (head != null) {
            this.boots = new VanillaArmorPiece(ArmorSection.BOOTS, boots);
            this.legs = new VanillaArmorPiece(ArmorSection.LEGS, legs);
            this.chest = new VanillaArmorPiece(ArmorSection.CHEST, chest);
            this.head = new VanillaArmorPiece(ArmorSection.HEAD, head);
        } else {
            this.boots = null;
            this.legs = null;
            this.chest = null;
            this.head = null;
        }
    }

    @Override
    public ItemStack material() {
        return new ItemStack(crafting);
    }

    @Override
    public ItemWithProperties<ArmorProperties> boots() {
        return boots;
    }

    @Override
    public ItemWithProperties<ArmorProperties> legs() {
        return legs;
    }

    @Override
    public ItemWithProperties<ArmorProperties> chest() {
        return chest;
    }

    @Override
    public ItemWithProperties<ArmorProperties> head() {
        return head;
    }


    public class VanillaArmorPiece extends VanillaItemWithProperties<ArmorProperties> {
        private final ArmorSection section;
        public final Material item;

        public VanillaArmorPiece(ArmorSection section, Material item) {
            //TODO: config this
            super(item, new ArmorPropertiesImpl(VanillaArmorMaterial.this, section, 0, 0, 0));

            this.section = section;
            this.item = item;
        }
    }
}
