package me.fly.newmod.armor.model;

import me.fly.newmod.armor.damage.DamageType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.Material.*;

public enum VanillaArmorMaterial implements ArmorMaterial {
    LEATHER(Material.LEATHER, LEATHER_BOOTS, LEATHER_LEGGINGS, LEATHER_CHESTPLATE, LEATHER_HELMET),

    GOLDEN(GOLD_INGOT, GOLDEN_BOOTS, GOLDEN_LEGGINGS, GOLDEN_CHESTPLATE, GOLDEN_HELMET),

    CHAINMAIL(CHAIN, CHAINMAIL_BOOTS, CHAINMAIL_LEGGINGS, CHAINMAIL_CHESTPLATE, CHAINMAIL_HELMET),

    IRON(IRON_INGOT, IRON_BOOTS, IRON_LEGGINGS, IRON_CHESTPLATE, IRON_HELMET),

    DIAMOND(Material.DIAMOND, DIAMOND_BOOTS, DIAMOND_LEGGINGS, DIAMOND_CHESTPLATE, DIAMOND_HELMET),

    NETHERITE(NETHERITE_INGOT, NETHERITE_BOOTS, NETHERITE_LEGGINGS, NETHERITE_CHESTPLATE, NETHERITE_HELMET);

    static {
        LEATHER.head.setDefense(1);
        LEATHER.chest.setDefense(3);
        LEATHER.legs.setDefense(2);
        LEATHER.boots.setDefense(1);

        GOLDEN.head.setDefense(2);
        GOLDEN.chest.setDefense(5);
        GOLDEN.legs.setDefense(3);
        GOLDEN.boots.setDefense(1);

        CHAINMAIL.head.setDefense(2);
        CHAINMAIL.chest.setDefense(5);
        CHAINMAIL.legs.setDefense(4);
        CHAINMAIL.boots.setDefense(1);

        IRON.head.setDefense(2);
        IRON.chest.setDefense(6);
        IRON.legs.setDefense(5);
        IRON.boots.setDefense(2);

        DIAMOND.head.setDefense(3);
        DIAMOND.chest.setDefense(8);
        DIAMOND.legs.setDefense(6);
        DIAMOND.boots.setDefense(3);

        NETHERITE.head.setDefense(3);
        NETHERITE.chest.setDefense(8);
        NETHERITE.legs.setDefense(6);
        NETHERITE.boots.setDefense(3);

        DIAMOND.head.setToughness(2);
        DIAMOND.chest.setToughness(2);
        DIAMOND.legs.setToughness(2);
        DIAMOND.boots.setToughness(2);

        NETHERITE.head.setToughness(3);
        NETHERITE.chest.setToughness(3);
        NETHERITE.legs.setToughness(3);
        NETHERITE.boots.setToughness(3);


    }

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
    public ArmorItemType boots() {
        return boots;
    }

    @Override
    public ArmorItemType legs() {
        return legs;
    }

    @Override
    public ArmorItemType chest() {
        return chest;
    }

    @Override
    public ArmorItemType head() {
        return head;
    }


    public class VanillaArmorPiece implements ArmorItemType {
        private int defense;
        private int toughness;
        private int durability;

        private final ArmorSection section;
        public final Material item;

        public VanillaArmorPiece(ArmorSection section, Material item) {
            this.section = section;
            this.item = item;
        }

        @Override
        public Material get() {
            return item;
        }

        @Override
        public ArmorSection getSection() {
            return section;
        }

        @Override
        public ArmorMaterial getMaterial() {
            return VanillaArmorMaterial.this;
        }

        @Override
        public int getToughness() {
            return toughness;
        }

        @Override
        public int getDefense() {
            return defense;
        }

        //Durability does not do anything yet.
        @Override
        public int getDurability() {
            return durability;
        }

        public void setToughness(int toughness) {
            this.toughness = toughness;
        }

        public void setDefense(int defense) {
            this.defense = defense;
        }


        public void setDurability(int durability) {
            this.durability = durability;
        }
    }
}
