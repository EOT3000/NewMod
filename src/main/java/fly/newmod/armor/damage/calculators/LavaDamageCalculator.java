package fly.newmod.armor.damage.calculators;

import fly.newmod.armor.armor.ArmorSection;
import fly.newmod.armor.damage.DamageType;
import fly.newmod.armor.util.DamageChecker;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class LavaDamageCalculator implements DamageCalculator {
    @Override
    public void calculateNewDamage(EntityDamageEvent event, DamageType type) {
        List<ArmorSection> sections = DamageChecker.affectsLava(event.getEntity());

        applyDefault(event, type, sections);
    }
}
