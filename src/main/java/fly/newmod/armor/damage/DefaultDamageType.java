package fly.newmod.armor.damage;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EntityEquipment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum DefaultDamageType implements DamageType {
    //All damage reduction applies
    GENERAL(null),

    PROJECTILE_SHARP(EntityDamageEvent.DamageCause.PROJECTILE) {
        @Override
        protected boolean check(EntityDamageEvent event) {
            return false;
        }

        /*@Override
        protected boolean check(ProjectileHitEvent event) {
            return event.getEntity();
        }*/
    },
    PROJECTILE_BLUNT(EntityDamageEvent.DamageCause.PROJECTILE),
    MELEE_SHARPS(EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
        @Override
        protected boolean check(EntityDamageEvent event) {
            if(event.getEntity() instanceof LivingEntity) {
                EntityEquipment entityEquipment = ((LivingEntity) event.getEntity()).getEquipment();

                if(entityEquipment != null) {
                    return sharps.contains(entityEquipment.getItemInMainHand().getType());
                }
            }

            return false;
        }
    },
    MELEE_BLUNT(EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
        @Override
        protected boolean check(EntityDamageEvent event) {
            if(event.getEntity() instanceof LivingEntity) {
                EntityEquipment entityEquipment = ((LivingEntity) event.getEntity()).getEquipment();

                if(entityEquipment != null) {
                    return !sharps.contains(entityEquipment.getItemInMainHand().getType());
                }
            }

            return true;
        }
    },
    SWEEPING_EDGE(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK),

    FIRE_DIRECT(EntityDamageEvent.DamageCause.FIRE),
    FIRE_TICK(EntityDamageEvent.DamageCause.FIRE_TICK),
    HOT_FLOOR(EntityDamageEvent.DamageCause.HOT_FLOOR),
    LAVA_DIRECT(EntityDamageEvent.DamageCause.LAVA),
    FREEZE(EntityDamageEvent.DamageCause.FREEZE),

    BLOCK_EXPLOSION(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION),
    ENTITY_EXPLOSION(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION),

    POISON(EntityDamageEvent.DamageCause.POISON),
    WITHER(EntityDamageEvent.DamageCause.WITHER),
    MAGIC(EntityDamageEvent.DamageCause.MAGIC),
    STARVATION(EntityDamageEvent.DamageCause.STARVATION),

    SUFFOCATION(EntityDamageEvent.DamageCause.SUFFOCATION),
    DROWNING(EntityDamageEvent.DamageCause.DROWNING),
    DRYOUT(EntityDamageEvent.DamageCause.DRYOUT),

    DRAGON_BREATH(EntityDamageEvent.DamageCause.DRAGON_BREATH),
    LIGHTNING(EntityDamageEvent.DamageCause.LIGHTNING),
    SONIC_BOOM(EntityDamageEvent.DamageCause.SONIC_BOOM),

    CRAMMING(EntityDamageEvent.DamageCause.CRAMMING),
    CONTACT(EntityDamageEvent.DamageCause.CONTACT),
    THORNS(EntityDamageEvent.DamageCause.THORNS),
    MELTING(EntityDamageEvent.DamageCause.MELTING),

    FALLING_BLOCK(EntityDamageEvent.DamageCause.FALLING_BLOCK),
    FALL(EntityDamageEvent.DamageCause.FALL),
    FLY_INTO_WALL(EntityDamageEvent.DamageCause.FLY_INTO_WALL),

    VOID(EntityDamageEvent.DamageCause.VOID),
    SUICIDE(EntityDamageEvent.DamageCause.SUICIDE),
    CUSTOM(EntityDamageEvent.DamageCause.CUSTOM)
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

    DefaultDamageType(EntityDamageEvent.DamageCause cause) {
        this.cause = cause;

        //DamageTypes
    }



    private static final Random random = new Random();

    protected boolean check(EntityDamageEvent event) {return true;}
    protected boolean check(ProjectileHitEvent event) {return true;}

    //@Override
    public boolean applies(EntityDamageEvent event) {
        return event.getCause().equals(cause);
    }

    //@Override
    public boolean applies(ProjectileHitEvent event) {
        return false;
    }

    @Override
    public List<Enchantment> enchantments() {
        return Collections.emptyList();
    }




    //private void check(int t, EntityDamageEvent event) {}
}
