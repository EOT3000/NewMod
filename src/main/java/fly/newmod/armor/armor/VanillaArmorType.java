package fly.newmod.armor.armor;

import fly.newmod.armor.damage.DamageType;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.Material.*;

public enum VanillaArmorType {
    NONE(AIR, AIR, AIR, AIR),

    LEATHER(LEATHER_BOOTS, LEATHER_LEGGINGS, LEATHER_CHESTPLATE, LEATHER_HELMET),

    GOLDEN(GOLDEN_BOOTS, GOLDEN_LEGGINGS, GOLDEN_CHESTPLATE, GOLDEN_HELMET),

    CHAINMAIL(CHAINMAIL_BOOTS, CHAINMAIL_LEGGINGS, CHAINMAIL_CHESTPLATE, CHAINMAIL_HELMET),

    IRON(IRON_BOOTS, IRON_LEGGINGS, IRON_CHESTPLATE, IRON_HELMET),

    DIAMOND(DIAMOND_BOOTS, DIAMOND_LEGGINGS, DIAMOND_CHESTPLATE, DIAMOND_HELMET),

    NETHERITE(NETHERITE_BOOTS, NETHERITE_LEGGINGS, NETHERITE_CHESTPLATE, NETHERITE_HELMET);

    static {
        LEATHER.head.setDefense(1);
        LEATHER.chest.setDefense(3);
        LEATHER.legs.setDefense(2);
        LEATHER.boots.setDefense(1);

        GOLDEN.head.setDefense(2);
        GOLDEN.chest.setDefense(5);
        GOLDEN.legs.setDefense(3);
        GOLDEN.boots.setDefense(1);


    }

    private final List<DamageType> immune = new ArrayList<>();
    private final List<DamageType> noDefense = new ArrayList<>();
    private final Map<DamageType, Integer> addedDefense = new HashMap<>();

    private final VanillaArmorPiece boots;
    private final VanillaArmorPiece legs;
    private final VanillaArmorPiece chest;
    private final VanillaArmorPiece head;

    VanillaArmorType(Material boots, Material legs, Material chest, Material head) {
        if (boots != AIR) {
            this.boots = new VanillaArmorPiece(boots);
            this.legs = new VanillaArmorPiece(legs);
            this.chest = new VanillaArmorPiece(chest);
            this.head = new VanillaArmorPiece(head);
        } else {
            VanillaArmorPiece vap = new VanillaArmorPiece(boots);

            this.boots = vap;
            this.legs = vap;
            this.chest = vap;
            this.head = vap;
        }
    }

    public static VanillaArmorPiece get(Material material) {
        for(VanillaArmorType type : VanillaArmorType.values()) {
            if(type.boots.mat.equals(material)) {
                return type.boots;
            }

            if(type.legs.mat.equals(material)) {
                return type.legs;
            }

            if(type.chest.mat.equals(material)) {
                return type.chest;
            }

            if(type.head.mat.equals(material)) {
                return type.head;
            }
        }

        //All are same for none
        return NONE.boots;
    }

    public void setImmune(DamageType type) {
        immune.add(type);
    }

    public void unsetImmune(DamageType type) {
        immune.remove(type);
    }

    public boolean isImmune(DamageType type) {
        return immune.contains(type);
    }

    public void setNoDefense(DamageType type) {
        noDefense.add(type);
    }

    public void unsetNoDefense(DamageType type) {
        noDefense.remove(type);
    }

    public boolean isNoDefense(DamageType type) {
        return noDefense.contains(type);
    }

    public void setAddedDefense(DamageType type, int defense) {
        addedDefense.put(type, defense);
    }

    public int getAddedDefense(DamageType type) {
        return addedDefense.get(type);
    }

    public class VanillaArmorPiece {
        private int defense;
        private int toughness;

        public final Material mat;

        public VanillaArmorPiece(Material mat) {
            this.mat = mat;
        }

        public VanillaArmorType getType() {
            return VanillaArmorType.this;
        }

        public void setDefense(int defense) {
            this.defense = defense;
        }

        public int getDefense() {
            return defense;
        }

        public void setToughness(int toughness) {
            this.toughness = toughness;
        }

        public int getToughness() {
            return toughness;
        }
    }
}
