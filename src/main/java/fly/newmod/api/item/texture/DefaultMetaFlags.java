package fly.newmod.api.item.texture;

import fly.newmod.utils.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.*;

import java.util.List;

public class DefaultMetaFlags {
    public static final MetaModifier.MetaModifierType<Integer> COLOR_MODIFIER = (integer, meta) -> {
        if(meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) meta).setColor(Color.fromRGB(integer));
        }

        if(meta instanceof PotionMeta) {
            ((PotionMeta) meta).setColor(Color.fromRGB(integer));
        }
        //TODO: fireworks
        return meta;
    };

    public static final MetaModifier.MetaModifierType<ItemFlag> FLAG_MODIFIER = (flag, meta) -> {
        meta.addItemFlags(flag);

        return meta;
    };

    public static final MetaModifier.MetaModifierType<Pair<Enchantment, Integer>> ENCHANTMENT_MODIFIER = (enchantment, meta) -> {
        meta.addEnchant(enchantment.getKey(), enchantment.getValue(), true);

        return meta;
    };

    public static final MetaModifier.MetaModifierType<Component> NAME_MODIFIER = (name, meta) -> {
        meta.displayName(name.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));

        return meta;
    };

    public static final MetaModifier.MetaModifierType<List<Component>> LORE_MODIFIER = (collection, meta) -> {
        meta.lore(collection);

        return meta;
    };

    public static final MetaModifier.MetaModifierType<SkullTexture> SKULL_MODIFIER = (texture, meta) -> {
        if(meta instanceof SkullMeta) {
            ((SkullMeta) meta).setPlayerProfile(texture.getProfile());
        }

        return meta;
    };
}
