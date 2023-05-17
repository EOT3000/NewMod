package me.fly.newmod.api.events;

import me.fly.newmod.NewMod;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class ModEventWrapper {
    public ModEventWrapper() {

    }

    public static abstract class ModEventListener implements Listener {
        public ModEventListener() {
            Bukkit.getPluginManager().registerEvents(this, NewMod.get());
        }
    }
}
