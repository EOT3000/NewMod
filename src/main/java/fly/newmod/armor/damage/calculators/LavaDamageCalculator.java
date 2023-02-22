package fly.newmod.armor.damage.calculators;

import fly.newmod.armor.armor.ArmorSection;
import fly.newmod.armor.damage.DamageType;
import fly.newmod.armor.damage.DefaultDamageType;
import fly.newmod.armor.util.ArmorPiece;
import fly.newmod.armor.util.DamageChecker;
import fly.newmod.armor.util.ReductionUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class LavaDamageCalculator implements DamageCalculator {
    @Override
    public void calculateNewDamage(EntityDamageEvent event, DamageType type) {
        List<ArmorSection> sections = DamageChecker.affectsLava(event.getEntity());

        if (!sections.isEmpty()) {
            double original = 0;
            double armor = 0;
            double magic = 0;
            double resistance = 0;

            for (ArmorSection section : sections) {
                double d = event.getDamage() / 4 * section.modifier;

                original += d;

                double a = ReductionUtils.armorModifier(d, new ArmorPiece((LivingEntity) event.getEntity(), section), DefaultDamageType.LAVA_DIRECT);
                double m = ReductionUtils.magicModifier(d, armor, new ArmorPiece((LivingEntity) event.getEntity(), section), DefaultDamageType.LAVA_DIRECT);
                double r = ReductionUtils.resistanceModifier(d, armor, magic, new ArmorPiece((LivingEntity) event.getEntity(), section), DefaultDamageType.LAVA_DIRECT);

                armor += a;
                magic += m;
                resistance += r;
            }

            event.setDamage(EntityDamageEvent.DamageModifier.BASE, original);
            event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, armor);
            event.setDamage(EntityDamageEvent.DamageModifier.MAGIC, magic);
            event.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, resistance);
        }
    }
}
