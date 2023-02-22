package fly.newmod.armor.listener;

import fly.newmod.armor.armor.ArmorSection;
import fly.newmod.armor.damage.DefaultDamageType;
import fly.newmod.armor.util.ArmorPiece;
import fly.newmod.armor.util.DamageChecker;
import fly.newmod.armor.util.ReductionUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class DamageListener implements Listener {
    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        //System.out.println(event.getEntity());
    }

    //TODO: Make this not hacky

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
        }*/
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageEntity(EntityDamageEvent event) {
        //System.out.println(event.getDamage() + ", " + event.getCause());

        /*if(event.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
            List<ArmorSection> sections = DamageChecker.affectsLava(event.getEntity());

            if(!sections.isEmpty()) {
                event.setCancelled(true);

                for(ArmorSection section : sections) {

                }
            }
        }*/
    }
}
