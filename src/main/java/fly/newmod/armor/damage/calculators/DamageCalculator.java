package fly.newmod.armor.damage.calculators;

import fly.newmod.armor.damage.DamageType;
import org.bukkit.event.entity.EntityDamageEvent;

public interface DamageCalculator {
    void calculateNewDamage(EntityDamageEvent event, DamageType type);
}
