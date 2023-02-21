package fly.newmod.armor.event;

import com.google.common.base.Function;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PreciseEntityDamageEntityEvent extends EntityDamageByEntityEvent {
    public PreciseEntityDamageEntityEvent(@NotNull Entity damager, @NotNull Entity damagee, @NotNull DamageCause cause, @NotNull Map<DamageModifier, Double> modifiers, @NotNull Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions, boolean critical) {
        super(damager, damagee, cause, modifiers, modifierFunctions, critical);
    }
}
