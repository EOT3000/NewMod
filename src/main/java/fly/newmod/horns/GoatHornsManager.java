package fly.newmod.horns;

import fly.newmod.parent.NewMod;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GoatHornsManager implements Listener {
    public void enable(NewMod newMod) {
        Bukkit.getPluginManager().registerEvents(this, newMod);
    }

    @EventHandler
    public void onHornBlow(PlayerStopUsingItemEvent event) {
        System.out.println(event.getItem());
        System.out.println(event.getTicksHeldFor());
    }
}
