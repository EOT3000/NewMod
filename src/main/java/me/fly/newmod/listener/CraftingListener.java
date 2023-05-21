package me.fly.newmod.listener;

import me.fly.newmod.NewMod;
import me.fly.newmod.crafting.CraftingChangesManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftingListener implements Listener {
    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        CraftingChangesManager manager = NewMod.get().getCraftingChangesManager();

        if(event.getRecipe() == null) {
            return;
        }

        if(manager.shouldPreventCraft(event.getInventory())) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

}
