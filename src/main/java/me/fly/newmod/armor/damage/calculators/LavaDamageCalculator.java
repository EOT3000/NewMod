package me.fly.newmod.armor.damage.calculators;

import me.fly.newmod.armor.vanilla.ArmorSection;
import me.fly.newmod.armor.damage.DamageType;
import me.fly.newmod.armor.util.DamageChecker;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class LavaDamageCalculator implements DamageCalculator {
    @Override
    public void calculateNewDamage(EntityDamageEvent event, DamageType type) {
        List<ArmorSection> sections = DamageChecker.affectsLava(event.getEntity());

        applyDefault(event, type, sections);
    }
}
