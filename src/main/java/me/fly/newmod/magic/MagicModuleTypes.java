package me.fly.newmod.magic;

import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.builders.ModItemTypeBuilder;
import me.fly.newmod.api.item.texture.DefaultMetaFlags;
import me.fly.newmod.api.item.texture.MetaModifier;
import me.fly.newmod.api.util.Pair;
import me.fly.newmod.armor.model.ModArmorItemType;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import static org.bukkit.Material.*;

public class MagicModuleTypes {
    public static void init() {
        DropsListener.addDrop(EntityType.BLAZE, CORE_BLAZE_ROD, 1);
    }

    public static final ModItemType BLAZE_SCALE = new ModItemTypeBuilder(LEATHER, "blaze_scale", MagicManager.get()).customName("Blaze Scale", NamedTextColor.GOLD.value())
            .enchantment(Enchantment.FIRE_ASPECT, 1).build().register();

    public static final ModItemType CORE_BLAZE_ROD = new ModItemTypeBuilder(BLAZE_ROD, "core_blaze_rod", MagicManager.get()).customName("Core Blaze Rod", TextColor.color(NamedTextColor.GOLD).value())
            .enchantment(Enchantment.FIRE_ASPECT, 2).build().register();

    public static final ModItemType GUARDIAN_SCALE = new ModItemTypeBuilder(PRISMARINE_SHARD, "guardian_scale", MagicManager.get()).customName("Guardian Scale", TextColor.color(NamedTextColor.AQUA).value())
            .enchantment(Enchantment.LOOT_BONUS_MOBS, 1).build().register();

    public static final ModItemType DRAGON_SCALE = new ModItemTypeBuilder(BLACK_DYE, "ender_dragon_scale", MagicManager.get()).customName("Dragon Scale", 0x282034)
            .enchantment(Enchantment.KNOCKBACK, 1).build().register();

    public static final ModItemType SHARP_SWORD = new ModItemTypeBuilder(IRON_SWORD, "sharp_sword", MagicManager.get()).customName("Sharp Sword", 0xC0C0C0)
            .addModifier(new MetaModifier<>(true, DefaultMetaFlags.UNBREAKABLE_MODIFIER)).build().register();

    public static final ModArmorItemType BLAZE_BOOTS = MagicArmorSets.BLAZE.boots();
    public static final ModArmorItemType BLAZE_LEGGINGS = MagicArmorSets.BLAZE.legs();
    public static final ModArmorItemType BLAZE_CHESTPLATE = MagicArmorSets.BLAZE.chest();
    public static final ModArmorItemType BLAZE_HELMET = MagicArmorSets.BLAZE.head();
}
