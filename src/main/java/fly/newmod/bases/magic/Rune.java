package fly.newmod.bases.magic;

import fly.newmod.bases.ModItem;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.FireworkEffectMeta;

public class Rune extends ModItem {
    private RuneType runeType;

    public Rune(String name, String id, Color color, RuneType runeType) {
        super(Material.FIREWORK_STAR, name, id);

        FireworkEffectMeta meta = (FireworkEffectMeta) getItemMeta();

        meta.setEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(color).build());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DYE, ItemFlag.HIDE_PLACED_ON);

        setItemMeta(meta);

        Runes.RUNES.add(this);

        this.runeType = runeType;
    }

    public RuneType getRuneType() {
        return runeType;
    }

    public enum RuneType {
        ENCHANTMENT_TYPE,
        ENCHANTMENT_LEVEL,

        OTHER
    }
}
