package me.fly.newmod.armor.listener;

import me.fly.newmod.armor.damage.DamageType;
import me.fly.newmod.armor.damage.DefaultDamageType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        //System.out.println(event.getEntity());
    }

    /*//TODO: Make this not hacky

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageBlock(EntityDamageByBlockEvent event) {

    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        System.out.println(event.getDamager().getLocation());

        /*if(event.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
            List<ArmorSection> sections = DamageChecker.affectsLava(event.getEntity());

            if(!sections.isEmpty()) {
                event.setCancelled(true);

                for(ArmorSection section : sections) {

                }
            }
        }*
    }*/

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        for(DamageType type : DefaultDamageType.values()) {
            if(type.applies(event)) {
                type.apply(event);

                return;
            }
        }
    }
}
