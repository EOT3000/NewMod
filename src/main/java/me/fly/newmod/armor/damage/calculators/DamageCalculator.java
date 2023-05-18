package me.fly.newmod.armor.damage.calculators;

import me.fly.newmod.armor.model.ArmorSection;
import me.fly.newmod.armor.damage.DamageType;
import me.fly.newmod.armor.util.ArmorPiece;
import me.fly.newmod.armor.util.ReductionUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.List;

public interface DamageCalculator {
    default void calculateNewDamage(EntityDamageEvent event, DamageType type) {
        applyDefault(event, type, Arrays.asList(ArmorSection.values()));
    }

    default void applyDefault(EntityDamageEvent event, DamageType type, List<ArmorSection> sections) {
        if(!sections.isEmpty()) {
            double original = 0;
            double armor = 0;
            double magic = 0;
            double resistance = 0;

            for(ArmorSection section : sections) {
                double d = event.getDamage()/4 * section.modifier;

                original+=d;

                double a = ReductionUtils.armorModifier(d, new ArmorPiece((LivingEntity) event.getEntity(), section), type);
                double m = ReductionUtils.magicModifier(d, armor, new ArmorPiece((LivingEntity) event.getEntity(), section), type);
                double r = ReductionUtils.resistanceModifier(d, armor, magic, new ArmorPiece((LivingEntity) event.getEntity(), section), type);

                armor+=a;
                magic+=m;
                resistance+=r;
            }

            event.setDamage(EntityDamageEvent.DamageModifier.BASE, original);
            event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, armor);
            event.setDamage(EntityDamageEvent.DamageModifier.MAGIC, magic);
            event.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, resistance);
        }
    }
}
