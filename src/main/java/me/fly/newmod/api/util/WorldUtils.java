package me.fly.newmod.api.util;

import me.fly.newmod.NewMod;
//TODO
//import net.coreprotect.CoreProtect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.*;

public class WorldUtils {
    private Random random = new Random();

    private void explode(int force, int size, Location location) {
        float[][][] array = new float[size * 4][][];

        for (int x = 0; x < size * 4; x++) {
            float[][] xa = new float[size * 4][];

            for (int y = 0; y < size * 4; y++) {
                float[] za = new float[size * 4];

                for (int i = 0; i < size * 4; i++) {
                    za[i] = -1;
                }

                xa[y] = za;
            }

            array[x] = xa;
        }

        new Thread(() -> {

            Set<Block> blocks = new HashSet<>(size * size * size);
            Map<Entity, Float> entities = new HashMap<>(size * size * size);
            long time = System.currentTimeMillis();

            for (float xi = 0; xi <= 360.0; xi += 1.0) {
                for (float yi = 0; yi <= 360.0; yi += 1.0) {
                    long time2 = System.currentTimeMillis();
                    float x = (float) Math.toRadians(xi);
                    float y = (float) Math.toRadians(yi);

                    Location current = location.clone();

                    Vector rtv = new Vector(Math.cos(x) * Math.cos(y),
                            Math.sin(y), Math.sin(x) * Math.cos(y)).normalize();

                    for (int rev = 0; rev < size; rev++) {
                        float damage = (1-((float) rev)/size)*((float) Math.sqrt(force)*3);

                        Block b = current.getBlock();
                        float br;

                        int xa = current.getBlockX() - location.getBlockX() + size * 2;
                        int ya = current.getBlockY() - location.getBlockY() + size * 2;
                        int za = current.getBlockZ() - location.getBlockZ() + size * 2;

                        if (array[xa][ya][za] == -1) {
                            array[xa][ya][za] = b.getType().getBlastResistance();
                        }

                        br = array[xa][ya][za];

                        if (force < br) {
                            break;
                        } else {
                            blocks.add(b);
                            //System.out.println("added");
                        }

                        current.add(rtv.getX() / c(4 * (br / force)), rtv.getY() / c(4 * (br / force)), rtv.getZ() / c(4 * (br / force)));
                    }

                    //System.out.println(System.currentTimeMillis()-time2);
                }
            }

            Bukkit.getScheduler().runTaskLater(NewMod.get(), () -> {
                for (Block b : blocks) {
                    if(b.getType() != Material.AIR) {
                        //TODO: readd
                        //CoreProtect.getInstance().getAPI().logRemoval("#nmexplosion", b.getLocation(), b.getType(), ((byte) 0));
                    }
                    if (random.nextBoolean()) {
                        b.breakNaturally();
                    } else {
                        b.setType(Material.AIR);
                    }
                }
                for (Block b : blocks) {
                    double damage = (size*force)/location.distance(b.getLocation());

                    for(Entity entity : location.getWorld().getNearbyEntities(b.getLocation(), 1.5, 1.5, 1.5)) {
                        if(!entities.containsKey(entity)) {
                            if (entity instanceof LivingEntity) {
                                ((LivingEntity) entity).damage(damage);
                            } else if (damage > 10) {
                                entity.remove();
                            }
                        } else {
                            entities.put(entity, 0f);
                        }
                    }
                    //blocks.remove(b);
                }
            }, 1);
        }).start();
    }

    private double c(double a) {
        if(random.nextInt(16) == 1) {
            return 0.75;
        }
        return Math.min(Math.max(a, 1.5), 8)+random.nextDouble()/4;
    }
}
