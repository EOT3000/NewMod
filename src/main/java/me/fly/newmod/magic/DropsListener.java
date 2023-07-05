package me.fly.newmod.magic;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.util.Pair;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DropsListener implements Listener {
    public static final NamespacedKey SHATTERED = new NamespacedKey(MagicManager.get(), "shattered");

    private Map<EntityType, Pair<ModItemType, Double>> drops = new HashMap<>();

    private Random random = new Random();

    @EventHandler
    public void onMobDamage(EntityDamageEvent event) {
        if(event instanceof EntityDamageByEntityEvent eee && (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK))) {
            if(eee.getDamager() instanceof Player player) {
                if(MagicModuleTypes.SHARP_SWORD.equals(NewMod.get().getItemManager().getType(player.getInventory().getItemInMainHand()))) {
                    return;
                }
            }
        }

        PersistentDataContainer container = event.getEntity().getPersistentDataContainer();

        if(!container.has(SHATTERED)) {
            container.set(SHATTERED, PersistentDataType.BOOLEAN, true);
        }
    }

    @EventHandler
    public void onMobDie(EntityDeathEvent event) {
        if(!drops.containsKey(event.getEntity().getType())) {
            return;
        }

        PersistentDataContainer container = event.getEntity().getPersistentDataContainer();

        if(container.getOrDefault(SHATTERED, PersistentDataType.BOOLEAN, false)) {
            return;
        }

        if(random.nextDouble() < drops.get(event.getEntity().getType()).getValue())
            event.getDrops().add(drops.get(event.getEntity().getType()).getKey().create());
    }

    public void addDrop(EntityType type, ModItemType item, double d) {
        drops.put(type, new Pair<>(item, d));
    }
}
