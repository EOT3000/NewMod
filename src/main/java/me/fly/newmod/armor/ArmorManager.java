package me.fly.newmod.armor;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.armor.model.ArmorProperties;
import me.fly.newmod.armor.model.VanillaArmorMaterial;
import me.fly.newmod.armor.model.VanillaHelmet;
import org.bukkit.inventory.ItemStack;

public class ArmorManager {

    public ArmorProperties getArmorItem(ItemStack stack) {
        if (stack == null) return null;

        ItemManager manager = NewMod.get().getItemManager();

        ModItemType type = manager.getType(stack);

        if(type == null) {
            if(stack.getType().equals(VanillaHelmet.TURTLE_HELMET.get())) {
                return VanillaHelmet.TURTLE_HELMET;
            }

            for(VanillaArmorMaterial material : VanillaArmorMaterial.values()) {
                if(material.boots().get().equals(stack.getType())) return material.boots();
                if(material.legs().get().equals(stack.getType())) return material.legs();
                if(material.chest().get().equals(stack.getType())) return material.chest();
                if(material.head().get().equals(stack.getType())) return material.head();
            }
        } else {
            if(type instanceof ArmorProperties) {
                return (ArmorProperties) type;
            }
        }

        return null;
    }
}
