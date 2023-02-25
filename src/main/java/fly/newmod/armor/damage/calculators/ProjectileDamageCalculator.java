package fly.newmod.armor.damage.calculators;

import fly.newmod.armor.armor.ArmorSection;
import fly.newmod.armor.damage.DamageType;
import fly.newmod.armor.damage.DefaultDamageType;
import fly.newmod.armor.util.ArmorPiece;
import fly.newmod.armor.util.DamageChecker;
import fly.newmod.armor.util.ReductionUtils;
import org.bukkit.entity.LivingEntity;
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
