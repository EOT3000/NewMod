package fly.newmod.impl.magic;

import fly.newmod.bases.magic.Rune;
import fly.newmod.bases.magic.Runes;
import fly.newmod.utils.Pair;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnchantingRune extends Rune {
    private Enchantment enchantment;

    private List<Pair<Material, Enchantment>> validAdditions = new ArrayList<>();

    @SafeVarargs
    public EnchantingRune(String name, String id, Color color, Enchantment enchantment, Pair<Material, Enchantment>... validAdds) {
        super(name, id, color, RuneType.ENCHANTMENT_TYPE);

        this.enchantment = enchantment;

        Runes.setRune(enchantment, enchantment.getMaxLevel(), this);

        validAdditions.addAll(Arrays.asList(validAdds));
    }

    public List<Material> getValidAdditions() {
        List<Material> ret = new ArrayList<>();

        for(Pair<Material, Enchantment> v : validAdditions) {
            ret.add(v.getKey());
        }

        return ret;
    }

    public Enchantment getEnchantment(Material material) {
        if(material == null) {
            return enchantment;
        } else {
            for(Pair<Material, Enchantment> pair : validAdditions) {
                if(pair.getKey().equals(material)) {
                    return pair.getValue();
                }
            }
        }

        return null;
    }
}
