package me.fly.newmod.armor;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.armor.model.ArmorItemType;
import me.fly.newmod.armor.model.ArmorMaterial;
import me.fly.newmod.armor.model.VanillaArmorMaterial;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ArmorManager {

    public ArmorItemType getArmorItem(ItemStack stack) {
        if (stack == null) return null;

        ItemManager manager = NewMod.get().getItemManager();

        ModItemType type = manager.getType(stack);

        if(type == null) {
            for(VanillaArmorMaterial material : VanillaArmorMaterial.values()) {
                if(material.boots().get().equals(stack.getType())) return material.boots();
                if(material.legs().get().equals(stack.getType())) return material.legs();
                if(material.chest().get().equals(stack.getType())) return material.chest();
                if(material.head().get().equals(stack.getType())) return material.head();
            }
        } else {
            if(type instanceof ArmorItemType) {
                return (ArmorItemType) type;
            }
        }

        return null;
    }
}
