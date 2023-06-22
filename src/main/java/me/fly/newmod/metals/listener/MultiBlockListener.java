package me.fly.newmod.metals.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MultiBlockListener implements Listener {
    //@EventHandler
    public void onDispenserLaunch(BlockDispenseEvent event) {
        if(event.getBlock() instanceof Dispenser) {
            Dispenser dispenser = (Dispenser) event.getBlock().getState();
            org.bukkit.block.data.type.Dispenser dispenserData = (org.bukkit.block.data.type.Dispenser) event.getBlock().getBlockData();

            Location c = dispenser.getLocation().add(0, -1, 0);

            if(dispenserData.getFacing().equals(BlockFace.DOWN) && c.getBlock().getType().equals(Material.WATER_CAULDRON)) {
                Inventory inventory = dispenser.getInventory();

                for(ItemStack itemStack : inventory.getContents()) {

                }

                event.setCancelled(true);
            }
        }
    }
}
