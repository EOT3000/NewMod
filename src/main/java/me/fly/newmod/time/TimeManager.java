package me.fly.newmod.time;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import me.fly.newmod.time.behaviour.RestrictSunGoal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class TimeManager implements Listener {
    private int coordinate = 0;

    @EventHandler
    public void onTick(ServerTickStartEvent event) {
        if(event.getTickNumber() % 7 == 0) {
            coordinate += TimeValues.ALIGNMENT.direction;

            if(TimeValues.ALIGNMENT.direction*coordinate > TimeValues.ALIGNMENT.direction*TimeValues.LOOP_BACK_COORDINATE) {
                coordinate = TimeValues.LOOP_START_COORDINATE;
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setPlayerTime(TimeUtils.time(getSkyBrightness(player.getLocation()), morning(player.getLocation())), true);
            }
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

        if(getSkyBrightness(event.getLocation()) >= 4810) {
            event.setCancelled(true);
        } else if(event.getEntity().getCategory().equals(EntityCategory.UNDEAD)) {
            Bukkit.getMobGoals().addGoal((Mob) event.getEntity(), 1, new RestrictSunGoal((Mob) event.getEntity()));
        }
    }

    public int getSkyBrightness(Location location) {
        if(!location.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            return -1;
        }

        int brightness = 0;

        int ds = Math.abs(coordinate-TimeValues.END_COORDINATE);

        if(ds < TimeValues.EFFECT_RADIUS) {
            brightness = getBrightness0(location, TimeValues.START_COORDINATE + TimeValues.ALIGNMENT.direction*ds*-1);
        }

        int de = Math.abs(coordinate-TimeValues.START_COORDINATE);

        if(de < TimeValues.EFFECT_RADIUS) {
            brightness = getBrightness0(location, TimeValues.END_COORDINATE + TimeValues.ALIGNMENT.direction*de);
        }


        return Math.max(getBrightness0(location, coordinate), brightness);
    }

    private int getBrightness0(Location location, int coordinate) {
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
