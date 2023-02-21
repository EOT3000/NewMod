package fly.newmod.armor.listener;

import fly.newmod.armor.type.armor.ArmorSection;
import fly.newmod.armor.type.damage.DefaultDamageType;
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
        if(event.getCause().equals(EntityDamageEvent.DamageCause.LAVA) && event.getEntity() instanceof LivingEntity) {
            List<ArmorSection> sections = DamageChecker.affectsLava(event.getEntity());

            if(!sections.isEmpty()) {

                double original = 0;
                double reduction = 0;

                for(ArmorSection section : sections) {
                    original+=event.getDamage()/4 * section.modifier;

                    double r = ReductionUtils.totalReduction(event.getDamage()/4 * section.modifier, new ArmorPiece((LivingEntity) event.getEntity(), section), DefaultDamageType.LAVA_DIRECT);

                    reduction+=r;
                }

                System.out.println("pre: " + event.getDamage() +
                                " original: " + original +
                                " reduced: " + (original-reduction) +
                                " sections: " + sections);

                for(EntityDamageEvent.DamageModifier m : EntityDamageEvent.DamageModifier.values()) {
                    if(event.isApplicable(m)) {
                        event.setDamage(m, 0);
                    }
                }

                event.setDamage(EntityDamageEvent.DamageModifier.BASE, original-reduction);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        System.out.println(event.getCause());
        System.out.println(event.getEntity());
        System.out.println(event.getDamager());
        System.out.println(event.getDamager().getType());

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
