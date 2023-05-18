package me.fly.newmod.armor.damage.calculators;

import me.fly.newmod.armor.model.ArmorSection;
import me.fly.newmod.armor.damage.DamageType;
import me.fly.newmod.armor.util.DamageChecker;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class ProjectileDamageCalculator implements DamageCalculator {
    @Override
    public void calculateNewDamage(EntityDamageEvent event, DamageType type) {
        List<ArmorSection> sections = DamageChecker.affectsProjectile(event.getEntity(), ((EntityDamageByEntityEvent) event).getDamager());

        applyDefault(event, type, sections);
    }
}
