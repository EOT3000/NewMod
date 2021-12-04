package fly.newmod.bases.magic;

import fly.newmod.bases.ModItem;
import fly.newmod.impl.magic.EnchantingRune;
import fly.newmod.impl.magic.LevelRune;
import fly.newmod.setup.Setup;
import fly.newmod.utils.Pair;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runes {
    private static Map<Pair<Enchantment, Integer>, Rune> RUNE_TYPES = new HashMap<>();
    static List<Rune> RUNES = new ArrayList<>();

    public static void setRune(Enchantment enchantment, int level, Rune rune) {
        RUNE_TYPES.put(new Pair<>(enchantment, level), rune);
    }

    public static Rune getRune(Enchantment enchantment, int level) {
        return RUNE_TYPES.get(new Pair<>(enchantment, level));
    }

    public static Rune getRune(ItemStack stack) {
        for(Rune rune : RUNES) {
            if(ModItem.matches(rune, stack)) {
                return rune;
            }
        }

        return null;
    }

    public static Map<Pair<Enchantment, Integer>, Rune> getRuneTypes() {
        return new HashMap<>(RUNE_TYPES);
    }

    public static ItemStack combine(ItemStack type, ItemStack level, ItemStack addition) {
        Rune typeRune = getRune(type);
        Rune levelRune = getRune(level);

        if(typeRune instanceof EnchantingRune && levelRune instanceof LevelRune) {
            ItemStack ret = Setup.COMBINED_RUNE.clone();
            ItemMeta meta = ret.getItemMeta();

            meta.addEnchant(((EnchantingRune) typeRune).getEnchantment(addition != null ? addition.getType() : null), ((LevelRune) levelRune).getLevel(), true);

            ret.setItemMeta(meta);

            return ret;
        }

        return null;
    }
}
