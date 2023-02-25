package fly.newmod.armor.damage;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public interface DamageType {
    boolean applies(EntityDamageEvent event);

    void apply(EntityDamageEvent event);

    //boolean applies(ProjectileHitEvent event);

    //default double reduce(EntityDamageEvent event) {
    //    return 0;
    //}

    //List<Enchantment> enchantments();
}
