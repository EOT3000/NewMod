package me.fly.newmod.magic;

import me.fly.newmod.api.item.ModItemType;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import static org.bukkit.Material.*;

public class MagicItems {
    public static final ModItemType BLAZE_SCALE = ModItemType.createAndRegister(LEATHER, MagicManager.get(), "blaze_scale", "Blaze Scale", NamedTextColor.GOLD.value())
            .enchant(Enchantment.FIRE_ASPECT, 1);

    public static final ModItemType CORE_BLAZE_ROD = ModItemType.createAndRegister(BLAZE_ROD, MagicManager.get(), "core_blaze_rod", "Core Blaze Rod", TextColor.color(NamedTextColor.GOLD).value())
            .enchant(Enchantment.FIRE_ASPECT, 2);

    public static final ModItemType GUARDIAN_SCALE = ModItemType.createAndRegister(PRISMARINE_SHARD, MagicManager.get(), "guardian_scale", "Guardian Scale", TextColor.color(NamedTextColor.AQUA).value())
            .enchant(Enchantment.LOOT_BONUS_MOBS, 1);

    public static final ModItemType DRAGON_SCALE = ModItemType.createAndRegister(BLACK_DYE, MagicManager.get(), "ender_dragon_scale", "Dragon Scale", 0x282034)
            .enchant(Enchantment.KNOCKBACK, 1);


}
