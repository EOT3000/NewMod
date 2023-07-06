package me.fly.newmod.technology.consumer;

import me.fly.newmod.api.events.block.ModBlockTickEvent;
import org.bukkit.block.Furnace;

public class FastFurnace {
    boolean tick(ModBlockTickEvent event) {
        Furnace furnace = (Furnace) event.getBlock().getState();

        if(furnace.getBurnTime() <= 0) {
            return false;
        }

        furnace.setCookSpeedMultiplier();

        furnace.update();

        return true;
    }
}
