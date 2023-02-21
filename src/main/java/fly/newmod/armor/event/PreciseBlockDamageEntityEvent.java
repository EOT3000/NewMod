package fly.newmod.armor.event;

import com.google.common.base.Function;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PreciseBlockDamageEntityEvent extends EntityDamageByBlockEvent {
    public PreciseBlockDamageEntityEvent(@Nullable Block damager, @NotNull Entity damagee, @NotNull DamageCause cause, @NotNull Map<DamageModifier, Double> modifiers, @NotNull Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
        super(damager, damagee, cause, modifiers, modifierFunctions);
    }
}
