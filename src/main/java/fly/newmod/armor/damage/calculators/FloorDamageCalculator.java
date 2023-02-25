package fly.newmod.armor.damage.calculators;

import com.google.common.collect.Lists;
import fly.newmod.armor.armor.ArmorSection;
import fly.newmod.armor.damage.DamageType;
import fly.newmod.armor.util.DamageChecker;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Collections;
import java.util.List;

public class FloorDamageCalculator implements DamageCalculator {

    //TODO: crawling
    @Override
    public void calculateNewDamage(EntityDamageEvent event, DamageType type) {
        applyDefault(event, type, Collections.singletonList(ArmorSection.FEET));
    }
}
