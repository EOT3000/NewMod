package fly.newmod.armor.event;

import fly.newmod.armor.armor.ArmorSection;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;

public class SectionDamageInfo {
    public final ArmorSection section;
    public final double originalDamage;

    //will probably make a custom system to not use deprecated methods
    private final Map<EntityDamageEvent.DamageModifier, Double> modifiers = new HashMap<>();

    public SectionDamageInfo(ArmorSection section, double originalDamage) {
        this.section = section;
        this.originalDamage = originalDamage;
    }

    public double getModifier(EntityDamageEvent.DamageModifier modifier) {
        return modifiers.get(modifier);
    }

    public void setModifier(EntityDamageEvent.DamageModifier modifier, double d) {
        modifiers.put(modifier, d);
    }
}
