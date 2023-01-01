package fly.newmod.time;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class TimeManager implements Listener {
    private int coordinate = 0;

    @EventHandler
    public void onTick(ServerTickStartEvent event) {
        coordinate+=TimeValues.ALIGNMENT.direction;

        if(TimeValues.ALIGNMENT.direction*coordinate > TimeValues.END_COORDINATE) {
            coordinate = TimeValues.START_COORDINATE;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerTime(TimeUtils.time(getBrightness(player.getLocation()), morning(player.getLocation())), true);
            player.sendMessage(coordinate + ": " + getBrightness(player.getLocation()) + ": " + TimeUtils.time(getBrightness(player.getLocation()), morning(player.getLocation())));
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if(!(event.getEntity() instanceof Monster)) {
            return;
        }

        if(!event.getLocation().getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            return;
        }

        if(getBrightness(event.getLocation()) > 50) {
            event.setCancelled(true);
        }
    }

    public int getBrightness(Location location) {
        int locX = coordinate;
        int locZ = TimeValues.AXIS_COORDINATE;

        int swap;

        //If Z alignment, switch the coordinates
        if(TimeValues.ALIGNMENT.z) {
            swap = locX;

            locX = locZ;

            locZ = swap;
        }

        int dx = (locX-location.getBlockX());
        int dz = (locZ-location.getBlockZ());

        int distance = (int) Math.sqrt(dx*dx + dz*dz);

        if(distance <= TimeValues.SUN_RADIUS) {
            return 12000;
        }

        double mult = 1-Math.min(Math.max(0.0, (distance-TimeValues.SUN_RADIUS)/(TimeValues.FADE_RADIUS*1.0)), 1.0);

        int r = (int) (mult*12000);

        return r;
    }

    public boolean morning(Location location) {
        int significantCoordinate;

        if(TimeValues.ALIGNMENT.z) {
            significantCoordinate = location.getBlockZ();
        } else {
            significantCoordinate = location.getBlockX();
        }

        return TimeValues.ALIGNMENT.direction*(coordinate-significantCoordinate) < 0;
    }
}
