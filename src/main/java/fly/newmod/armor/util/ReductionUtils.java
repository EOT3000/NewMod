package fly.newmod.armor.util;

import fly.newmod.armor.damage.DamageType;
import fly.newmod.armor.damage.DefaultDamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

//Following calculations all from Minecraft wiki
public class ReductionUtils {
    private static final List<DamageType> resistanceImmune = Arrays.asList(
            DefaultDamageType.VOID, DefaultDamageType.CUSTOM, DefaultDamageType.SUICIDE, DefaultDamageType.STARVATION, DefaultDamageType.SONIC_BOOM);

    private static final List<DamageType> fire = Arrays.asList(
            DefaultDamageType.FIRE_TICK, DefaultDamageType.FIRE_DIRECT, DefaultDamageType.LAVA_DIRECT, DefaultDamageType.HOT_FLOOR);

    private static final List<DamageType> blast = Arrays.asList(
            DefaultDamageType.BLOCK_EXPLOSION, DefaultDamageType.ENTITY_EXPLOSION);

    private static final List<DamageType> proj = Arrays.asList(
            DefaultDamageType.PROJECTILE_BLUNT, DefaultDamageType.PROJECTILE_SHARP);


    public static double armorModifier(double damage, ArmorPiece piece, DamageType type) {
        double general = reductionArmor(damage, piece.defense, piece.toughness);

        if(piece.piece.getType().isNoDefense(type)) {
            general = 0;
        }

        return Math.min(general, damage);
    }

    public static double magicModifier(double damage, double armor, ArmorPiece piece, DamageType type) {
        damage = damage-armor;

        double protection = reductionProtection(damage, piece.enchantments.getOrDefault(Enchantment.PROTECTION_ENVIRONMENTAL, 0));
        double protectionFire = reductionBodySpecialProtection(damage, piece.enchantments.getOrDefault(Enchantment.PROTECTION_FIRE, 0));
        double protectionProj = reductionBodySpecialProtection(damage, piece.enchantments.getOrDefault(Enchantment.PROTECTION_PROJECTILE, 0));
        double protectionBlas = reductionBodySpecialProtection(damage, piece.enchantments.getOrDefault(Enchantment.PROTECTION_EXPLOSIONS, 0));
        double protectionFall = reductionFeatherFalling(damage, piece.enchantments.getOrDefault(Enchantment.PROTECTION_FALL, 0));

        if(resistanceImmune.contains(type)) {
            protection = 0;
        }

        if(!fire.contains(type)) {
            protectionFire = 0;
        }

        if(!blast.contains(type)) {
            protectionBlas = 0;
        }

        if(!proj.contains(type)) {
            protectionProj = 0;
        }

        if(!type.equals(DefaultDamageType.FALL)) {
            protectionFall = 0;
        }

        return Math.min(protection+protectionFire+protectionProj+protectionBlas+protectionFall, damage);
    }

    public static double resistanceModifier(double damage, double armor, double magic, ArmorPiece piece, DamageType type) {
        damage = damage-armor-magic;

        double resistance = reductionResistance(damage, piece.entity.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)
                ? piece.entity.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier()
                : 0);

        if(resistanceImmune.contains(type)) {
            resistance = 0;
        }

        return Math.min(resistance, damage);
    }


    public static double reductionArmor(double damage, int defense, int toughness) {
        double secondCalc = defense - (4*damage)/(toughness+8);

        return damage - damage*(1-(Math.max(defense/5.0,secondCalc)/25));
    }

    public static double reductionResistance(double damage, int level) {
        return damage*(0.2*level);
    }

    /*public static double reductionFireResistance(double damage, int level) {
        return damage * (level > 0 ? 1.0 : 0.0);
    }*/

    public static double reductionProtection(double damage, int level) {
        return damage*(0.04*level);
    }

    //Blast Protection, Fire Protection, Projectile Protection
    public static double reductionBodySpecialProtection(double damage, int level) {
        return damage*(0.08*level);
    }

    public static double reductionFeatherFalling(double damage, int level) {
        return damage*(0.12*level);
    }
}
