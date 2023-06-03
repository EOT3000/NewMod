package me.fly.newmod.time;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import me.fly.newmod.time.nms.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class TimeManager implements Listener {
    public double coordinate = 0;

    @EventHandler
    public void onTick(ServerTickStartEvent event) {
        if(event.getTickNumber() % 5 == 0) {
            advanceSun();

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setPlayerTime(TimeUtils.time(getSkyBrightness(player.getLocation()), morning(player.getLocation())), false);
            }
        }
    }

    private void advanceSun() {
        //System.out.println("Sun advance start");
        //System.out.println("pre sun at: " + coordinate);

        double inc = TimeValues.INCREMENT*((long) TimeValues.ALIGNMENT.direction);

        coordinate+=inc;

        //System.out.println("now sun at: " + coordinate);

        if(coordinate*TimeValues.ALIGNMENT.direction > TimeValues.END_COORDINATE + (long) TimeValues.ALIGNMENT.direction * TimeValues.EFFECT_RADIUS) {
            //System.out.println("reset");
            coordinate = TimeValues.START_COORDINATE + (long) TimeValues.ALIGNMENT.direction * TimeValues.EFFECT_RADIUS;
        }

        //System.out.println("sun now at: " + coordinate);
        //System.out.println();
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if(!(event.getEntity() instanceof Monster)) {
            if(event.getEntity() instanceof Bee) {
                NMSUtils.addBeeGoals((Bee) event.getEntity());
            }

            return;
        }

        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
            if (getSkyBrightness(event.getLocation()) >= 4810 && !event.getLocation().getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                event.setCancelled(true);
            }
        }

        if(event.getEntity().getCategory().equals(EntityCategory.UNDEAD)) {
            NMSUtils.addUndeadGoals((Creature) event.getEntity());
        }
    }

    public int getSkyBrightness(Location location) {
        if(!location.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            return -1;
        }

        int brightness = 0;

        double ds = Math.abs(coordinate-TimeValues.END_COORDINATE);

        if(ds < TimeValues.EFFECT_RADIUS) {
            brightness = getBrightness0(location, TimeValues.START_COORDINATE + TimeValues.ALIGNMENT.direction*ds*-1);
        }

        double de = Math.abs(coordinate-TimeValues.START_COORDINATE);

        if(de < TimeValues.EFFECT_RADIUS) {
            brightness = getBrightness0(location, TimeValues.END_COORDINATE + TimeValues.ALIGNMENT.direction*de);
        }


        return Math.max(getBrightness0(location, coordinate), brightness);
    }

    private int getBrightness0(Location location, double coordinate) {
        double locX = coordinate;
        double locZ = TimeValues.AXIS_COORDINATE;

        double swap;

        //If Z alignment, switch the coordinates
        if(TimeValues.ALIGNMENT.z) {
            swap = locX;

            locX = locZ;

            locZ = swap;
        }

        double dx = (locX-location.getBlockX());
        double dz = (locZ-location.getBlockZ());

        double distance = Math.sqrt(dx*dx + dz*dz);

        if(distance <= TimeValues.SUN_RADIUS) {
            return 12000;
        }

        double mult = 1-Math.min(Math.max(0.0, (distance-TimeValues.SUN_RADIUS)/(TimeValues.EFFECT_RADIUS*1.0)), 1.0);

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
