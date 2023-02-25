package fly.newmod.armor.damage;

import fly.newmod.armor.damage.calculators.DamageCalculator;
import fly.newmod.armor.damage.calculators.FloorDamageCalculator;
import fly.newmod.armor.damage.calculators.LavaDamageCalculator;
import fly.newmod.armor.damage.calculators.ProjectileDamageCalculator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EntityEquipment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum DefaultDamageType implements DamageType {
    //All damage reduction applies
    GENERAL(null, (a,b) -> {}),

    PROJECTILE_SHARP(EntityDamageEvent.DamageCause.PROJECTILE, new ProjectileDamageCalculator()) {
        @Override
        public boolean applies0(EntityDamageEvent event) {
            if(!(event instanceof EntityDamageByEntityEvent)) {
                return false;
            }

            return ((EntityDamageByEntityEvent) event).getDamager() instanceof AbstractArrow;
        }
    },
    PROJECTILE_BLUNT(EntityDamageEvent.DamageCause.PROJECTILE, new ProjectileDamageCalculator()) {
        @Override
        public boolean applies0(EntityDamageEvent event) {
            if(!(event instanceof EntityDamageByEntityEvent)) {
                return false;
            }

            return !(((EntityDamageByEntityEvent) event).getDamager() instanceof AbstractArrow);
        }
    },
    MELEE_SHARPS(EntityDamageEvent.DamageCause.ENTITY_ATTACK, (a,b) -> {}) {
        @Override
        public boolean applies0(EntityDamageEvent event) {
            if(!(event instanceof EntityDamageByEntityEvent)) {
                return false;
            }

            if(((EntityDamageByEntityEvent) event).getDamager() instanceof LivingEntity) {
                EntityEquipment entityEquipment = ((LivingEntity) ((EntityDamageByEntityEvent) event).getDamager()).getEquipment();

                if(entityEquipment != null) {
                    return sharps.contains(entityEquipment.getItemInMainHand().getType());
                }
            }

            return false;
        }
    },
    MELEE_BLUNT(EntityDamageEvent.DamageCause.ENTITY_ATTACK, (a,b) -> {}) {
        @Override
        public boolean applies0(EntityDamageEvent event) {
            if(!(event instanceof EntityDamageByEntityEvent)) {
                return false;
            }

            if(((EntityDamageByEntityEvent) event).getDamager() instanceof LivingEntity) {
                EntityEquipment entityEquipment = ((LivingEntity) ((EntityDamageByEntityEvent) event).getDamager()).getEquipment();

                if(entityEquipment != null) {
                    return !sharps.contains(entityEquipment.getItemInMainHand().getType());
                }
            }

            return false;
        }
    },
    SWEEPING_EDGE(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK, (a,b) -> {}),

    FIRE_DIRECT(EntityDamageEvent.DamageCause.FIRE, (a,b) -> {}),
    FIRE_TICK(EntityDamageEvent.DamageCause.FIRE_TICK, (a,b) -> {}),
    HOT_FLOOR(EntityDamageEvent.DamageCause.HOT_FLOOR, new FloorDamageCalculator()),
    LAVA_DIRECT(EntityDamageEvent.DamageCause.LAVA, new LavaDamageCalculator()),
    FREEZE(EntityDamageEvent.DamageCause.FREEZE, (a,b) -> {}),

    BLOCK_EXPLOSION(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, (a,b) -> {}),
    ENTITY_EXPLOSION(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, (a,b) -> {}),

    POISON(EntityDamageEvent.DamageCause.POISON, (a,b) -> {}),
    WITHER(EntityDamageEvent.DamageCause.WITHER, (a,b) -> {}),
    MAGIC(EntityDamageEvent.DamageCause.MAGIC, (a,b) -> {}),
    STARVATION(EntityDamageEvent.DamageCause.STARVATION, (a,b) -> {}),

    SUFFOCATION(EntityDamageEvent.DamageCause.SUFFOCATION, (a,b) -> {}),
    DROWNING(EntityDamageEvent.DamageCause.DROWNING, (a,b) -> {}),
    DRYOUT(EntityDamageEvent.DamageCause.DRYOUT, (a,b) -> {}),

    DRAGON_BREATH(EntityDamageEvent.DamageCause.DRAGON_BREATH, (a,b) -> {}),
    LIGHTNING(EntityDamageEvent.DamageCause.LIGHTNING, (a,b) -> {}),
    SONIC_BOOM(EntityDamageEvent.DamageCause.SONIC_BOOM, (a,b) -> {}),

    CRAMMING(EntityDamageEvent.DamageCause.CRAMMING, (a,b) -> {}),
    CONTACT(EntityDamageEvent.DamageCause.CONTACT, (a,b) -> {}),
    THORNS(EntityDamageEvent.DamageCause.THORNS, (a,b) -> {}),
    MELTING(EntityDamageEvent.DamageCause.MELTING, (a,b) -> {}),

    FALLING_BLOCK(EntityDamageEvent.DamageCause.FALLING_BLOCK, (a,b) -> {}),
    FALL(EntityDamageEvent.DamageCause.FALL, new FloorDamageCalculator()),
    FLY_INTO_WALL(EntityDamageEvent.DamageCause.FLY_INTO_WALL, (a,b) -> {}),

    VOID(EntityDamageEvent.DamageCause.VOID, (a,b) -> {}),
    SUICIDE(EntityDamageEvent.DamageCause.SUICIDE, (a,b) -> {}),
    CUSTOM(EntityDamageEvent.DamageCause.CUSTOM, (a,b) -> {})
    ;

    protected List<Material> sharps = Arrays.asList(
            Material.WOODEN_SWORD, Material.WOODEN_AXE, Material.WOODEN_HOE, Material.WOODEN_SHOVEL, Material.WOODEN_PICKAXE,
            Material.STONE_SWORD, Material.STONE_AXE, Material.STONE_HOE, Material.STONE_SHOVEL, Material.STONE_PICKAXE,
            Material.IRON_SWORD, Material.IRON_AXE, Material.IRON_HOE, Material.IRON_SHOVEL, Material.IRON_PICKAXE,
            Material.GOLDEN_SWORD, Material.GOLDEN_AXE, Material.GOLDEN_HOE, Material.GOLDEN_SHOVEL, Material.GOLDEN_PICKAXE,
            Material.DIAMOND_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_SHOVEL, Material.DIAMOND_PICKAXE,
            Material.NETHERITE_SWORD, Material.NETHERITE_AXE, Material.NETHERITE_HOE, Material.NETHERITE_SHOVEL, Material.NETHERITE_PICKAXE,
            Material.TRIDENT, Material.SHEARS);

    private final EntityDamageEvent.DamageCause cause;
    private final DamageCalculator calculator;

    DefaultDamageType(EntityDamageEvent.DamageCause cause, DamageCalculator calculator) {
        this.cause = cause;
        this.calculator = calculator;

        //DamageTypes
    }

    @Override
    public void apply(EntityDamageEvent event) {

    }

    @Override
    public boolean applies(EntityDamageEvent event) {
        return event.getCause().equals(cause) && applies0(event);
    }

    protected boolean applies0(EntityDamageEvent event) {
        return true;
    }



    //private void check(int t, EntityDamageEvent event) {}
}
