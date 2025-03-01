package me.fly.newmod.time.nms;

import com.destroystokyo.paper.entity.ai.VanillaGoal;
import me.fly.newmod.NewMod;
import me.fly.newmod.time.nms.bee.CustomEnterHiveGoal;
import me.fly.newmod.time.nms.bee.CustomGoToHiveGoal;
import me.fly.newmod.time.nms.undead.CustomFireInDayGoal;
import me.fly.newmod.time.nms.undead.CustomFleeSunGoal;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.goal.PathfinderGoalWrapped;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.entity.monster.EntitySkeletonAbstract;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftMob;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Creature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class NMSUtils {
    static {
        BEE_ENTER_HIVE_GOAL = clazz("entitybee$d", "enter hive goal");
        BEE_LOCATE_HIVE_GOAL = clazz("entitybee$i", "locate hive goal");

        Class<?> bpg = clazz("entitybee$k", "pollinate goal");;

        BEE_POLLINATE_GOAL = bpg;

        try {
            Method method = bpg.getDeclaredMethod("l");

            method.setAccessible(true);

            IS_POLLINATING = method;
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Error when getting bee stuff: is pollinating");
        }

        try {
            Field field = EntityBee.class.getDeclaredField("cC");

            field.setAccessible(true);

            NEW_HIVE_COOLDOWN = field;
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Error when getting bee stuff: new hive cooldown");
        }
    }

    private static Class<?> clazz(String name, String err) {
        Class<?> b = null;

        for(Class<?> clazz : EntityBee.class.getDeclaredClasses()) {
            if(clazz.getName().toLowerCase().contains(name)) {
                b = clazz;
            }
        }

        if(b == null) {
            throw new RuntimeException("Error when getting bee stuff: " + err);
        }

        return b;
    }

    public static final Class<?> BEE_ENTER_HIVE_GOAL;
    public static final Class<?> BEE_POLLINATE_GOAL;
    public static final Class<?> BEE_LOCATE_HIVE_GOAL;
    public static final Method IS_POLLINATING;
    public static final Field NEW_HIVE_COOLDOWN;

    public static boolean wantsToEnter(Location pos, EntityBee bee, boolean pollinating) {
        if (bee.cz <= 0 && !pollinating && !bee.gj() && bee.j() == null) {
            boolean flag = (bee.cy > 3600) || bee.dI().Z() || NewMod.get().getTimeManager().getSkyBrightness(pos) < 5458 || bee.gi();
            return flag && !hiveNearFire(bee);
        } else {
            return false;
        }
    }

    public static boolean isPollinating(PathfinderGoal goal) {
        try {
            return (boolean) IS_POLLINATING.invoke(goal);
        } catch (Exception e) {
            return false;
        }
    }

    public static int newHiveCooldown(EntityBee bee) {
        try {
            return (int) NEW_HIVE_COOLDOWN.get(bee);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean hiveNearFire(EntityBee bee) {
        if (bee.cG == null) {
            return false;
        } else if (!bee.dI().isLoadedAndInBounds(bee.cG)) {
            return false;
        } else {
            TileEntity tileentity = bee.dI().c_(bee.cG);
            return tileentity instanceof TileEntityBeehive && ((TileEntityBeehive)tileentity).c();
        }
    }

    public static PathfinderGoal getGoal(Class<?> clazz, PathfinderGoalSelector goalSelector) {
        for (PathfinderGoalWrapped goal : goalSelector.b()) {
            PathfinderGoal handle = goal.k();

            if (clazz.isInstance(handle)) {
                return handle;
            }
        }

        return null;
    }

    public static void addUndeadGoals(Creature mob) {
        Bukkit.getMobGoals().removeGoal(mob, VanillaGoal.RESTRICT_SUN);
        Bukkit.getMobGoals().removeGoal(mob, VanillaGoal.FLEE_SUN);
        //Bukkit.getMobGoals().addGoal(mob, 2, new CustomRestrictSunGoal(mob));
        Bukkit.getMobGoals().addGoal(mob, 3, new CustomFleeSunGoal(mob, 1.0));
        Bukkit.getMobGoals().addGoal(mob, 5, new CustomFireInDayGoal(mob));

        EntityInsentient ei = ((CraftMob) mob).getHandle();

        if(ei instanceof EntitySkeletonAbstract) {
            ((EntitySkeletonAbstract) ei).setShouldBurnInDay(false);
        } else if(ei instanceof EntityZombie) {
            ((EntityZombie) ei).setShouldBurnInDay(false);
        }
    }

    public static void addBeeGoals(Bee bee) {
        /*Bukkit.getMobGoals().addGoal(bee, 1, new CustomEnterHiveGoal(bee));
        Bukkit.getMobGoals().addGoal(bee, 5, new CustomGoToHiveGoal(bee));

        Bukkit.getMobGoals().removeGoal(bee, VanillaGoal.BEE_ENTER_HIVE);
        Bukkit.getMobGoals().removeGoal(bee, VanillaGoal.BEE_GO_TO_HIVE);*/
    }
}
