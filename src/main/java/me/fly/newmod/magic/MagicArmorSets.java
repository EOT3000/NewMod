package me.fly.newmod.magic;

import me.fly.newmod.armor.model.ArmorSection;
import me.fly.newmod.armor.model.ModArmorMaterial;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

public class MagicArmorSets {
    public static final ModArmorMaterial BLAZE = new ModArmorMaterial(MagicModuleTypes.BLAZE_SCALE.create(),
            new ModArmorItemTypeBuilder(Material.LEATHER_BOOTS, "blaze_boots", MagicManager.get(), ArmorSection.BOOTS)
                    .customName("Blaze Boots", NamedTextColor.GOLD.value()).color(0x832e00)
                    .defense(3).toughness(2).durability(-1),
            new ModArmorItemTypeBuilder(Material.LEATHER_LEGGINGS, "blaze_leggings", MagicManager.get(), ArmorSection.BOOTS)
                    .customName("Blaze Pants", NamedTextColor.GOLD.value()).color(0xd46e00)
                    .defense(8).toughness(2).durability(-1),
            new ModArmorItemTypeBuilder(Material.LEATHER_CHESTPLATE, "blaze_chestplate", MagicManager.get(), ArmorSection.BOOTS)
                    .customName("Blaze Tunic", NamedTextColor.GOLD.value()).color(0xe29614)
                    .defense(6).toughness(2).durability(-1),
            new ModArmorItemTypeBuilder(Material.LEATHER_HELMET, "blaze_helmet", MagicManager.get(), ArmorSection.BOOTS)
                    .customName("Blaze Cap", NamedTextColor.GOLD.value()).color(0xebd45c)
                    .defense(3).toughness(2).durability(-1)
    );
}
