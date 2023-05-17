package me.fly.newmod.armor.damage;

import org.bukkit.event.entity.EntityDamageEvent;

public interface DamageType {
    boolean applies(EntityDamageEvent event);

    void apply(EntityDamageEvent event);

    //boolean applies(ProjectileHitEvent event);

    //default double reduce(EntityDamageEvent event) {
    //    return 0;
    //}

    //List<Enchantment> enchantments();
}
