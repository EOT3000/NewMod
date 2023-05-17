package me.fly.newmod.armor.damage.calculators;

import me.fly.newmod.armor.vanilla.ArmorSection;
import me.fly.newmod.armor.damage.DamageType;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Collections;

public class FloorDamageCalculator implements DamageCalculator {

    //TODO: crawling
    @Override
    public void calculateNewDamage(EntityDamageEvent event, DamageType type) {
        applyDefault(event, type, Collections.singletonList(ArmorSection.FEET));
    }
}
