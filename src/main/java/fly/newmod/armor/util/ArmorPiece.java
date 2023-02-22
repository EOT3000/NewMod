package fly.newmod.armor.util;

import com.google.common.collect.ImmutableMap;
import fly.newmod.armor.armor.ArmorSection;
import fly.newmod.armor.armor.VanillaArmorType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;

public class ArmorPiece {
    public final int defense;
    public final int toughness;

    public final Map<Enchantment, Integer> enchantments;

    //For easy potion effect access
    public final LivingEntity entity;

    public final VanillaArmorType.VanillaArmorPiece piece;

    public ArmorPiece(LivingEntity entity, ArmorSection section) {
        this.entity = entity;
        ItemStack stack = section.getItem(entity);

        VanillaArmorType.VanillaArmorPiece piece = VanillaArmorType.get(stack == null ? null : stack.getType());

        this.piece = piece;

        if(piece.getType() == VanillaArmorType.NONE) {
            defense = 0;
            toughness = 0;

            enchantments = ImmutableMap.of();

            return;
        }

        int attributeDefense = 0;
        int attributeToughness = 0;

        if(stack.getItemMeta().getAttributeModifiers() != null) {
            Collection<AttributeModifier> modifiersa = stack.getItemMeta().getAttributeModifiers().get(Attribute.GENERIC_ARMOR);
            Collection<AttributeModifier> modifiersat = stack.getItemMeta().getAttributeModifiers().get(Attribute.GENERIC_ARMOR_TOUGHNESS);

            for (AttributeModifier modifier : modifiersa) {
                attributeDefense += modifier.getAmount();
            }

            for (AttributeModifier modifier : modifiersat) {
                attributeToughness += modifier.getAmount();
            }
        }

        defense = piece.getDefense() + attributeDefense;
        toughness = piece.getToughness() + attributeToughness;

        enchantments = new ImmutableMap.Builder<Enchantment, Integer>().putAll(stack.getEnchantments()).build();
    }

    @Override
    public String toString() {
        return "ArmorPiece{" +
                "defense=" + defense +
                ", toughness=" + toughness +
                ", enchantments=" + enchantments +
                ", entity=" + entity +
                ", piece=" + piece +
                '}';
    }
}
