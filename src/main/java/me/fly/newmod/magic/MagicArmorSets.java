package me.fly.newmod.magic;

import me.fly.newmod.api.item.builders.ModArmorItemTypeBuilder;
import me.fly.newmod.armor.model.ArmorItemType;
import me.fly.newmod.armor.model.ArmorMaterial;
import me.fly.newmod.armor.model.ArmorSection;
import me.fly.newmod.armor.model.ModArmorMaterial;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MagicArmorSets {
    public static final ModArmorMaterial BLAZE = new ModArmorMaterial(MagicItems.BLAZE_SCALE.create(),
            new ModArmorItemTypeBuilder(Material.LEATHER_BOOTS, "blaze_boots", MagicManager.get(), ArmorSection.BOOTS)
                    .customName("Blaze Boots", NamedTextColor.GOLD.value())
                    .defense(3).toughness(2).durability(-1),
            new ModArmorItemTypeBuilder(Material.LEATHER_LEGGINGS, "blaze_leggings", MagicManager.get(), ArmorSection.BOOTS)
                    .customName("Blaze Pants", NamedTextColor.GOLD.value())
                    .defense(8).toughness(2).durability(-1),
            new ModArmorItemTypeBuilder(Material.LEATHER_CHESTPLATE, "blaze_chestplate", MagicManager.get(), ArmorSection.BOOTS)
                    .customName("Blaze Tunic", NamedTextColor.GOLD.value())
                    .defense(6).toughness(2).durability(-1),
            new ModArmorItemTypeBuilder(Material.LEATHER_HELMET, "blaze_helmet", MagicManager.get(), ArmorSection.BOOTS)
                    .customName("Blaze Cap", NamedTextColor.GOLD.value())
                    .defense(3).toughness(2).durability(-1)
    );
}
