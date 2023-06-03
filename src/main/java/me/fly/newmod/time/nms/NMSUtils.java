package me.fly.newmod.time.nms;

import com.destroystokyo.paper.entity.ai.VanillaGoal;
import me.fly.newmod.NewMod;
import me.fly.newmod.time.nms.bee.CustomEnterHiveGoal;
import me.fly.newmod.time.nms.bee.CustomGoToHiveGoal;
import me.fly.newmod.time.nms.undead.CustomFireInDayGoal;
import me.fly.newmod.time.nms.undead.CustomFleeSunGoal;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.goal.PathfinderGoalWrapped;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.entity.monster.EntitySkeletonAbstract;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftMob;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Creature;

import java.lang.reflect.Method;

public class NMSUtils {
    static {
        BEE_ENTER_HIVE_GOAL = clazz("entitybee$d", "enter hive goal");
        BEE_LOCATE_HIVE_GOAL = clazz("entitybee$i", "enter hive goal");

        Class<?> bpg = clazz("entitybee$k", "locate hive goal");;

        BEE_POLLINATE_GOAL = bpg;

        try {
            Method method = bpg.getDeclaredMethod("l");

            method.setAccessible(true);

            IS_POLLINATING = method;
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Error when getting bee stuff: is pollinating");
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

    public static boolean wantsToEnter(Location pos, EntityBee bee, boolean pollinating) {
        if (bee.cy <= 0 && !pollinating && !bee.gd() && bee.P_() == null) {
            boolean flag = (bee.cx > 3600) || bee.H.Y() || NewMod.get().getTimeManager().getSkyBrightness(pos) < 5458 || bee.gc();
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

    public static boolean hiveNearFire(EntityBee bee) {
        if (bee.cF == null) {
            return false;
        } else if (!bee.H.isLoadedAndInBounds(bee.cF)) {
            return false;
        } else {
            TileEntity tileentity = bee.H.c_(bee.cF);
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
        Bukkit.getMobGoals().addGoal(bee, 1, new CustomEnterHiveGoal(bee));
        Bukkit.getMobGoals().addGoal(bee, 5, new CustomGoToHiveGoal(bee));

        Bukkit.getMobGoals().removeGoal(bee, VanillaGoal.BEE_ENTER_HIVE);
        Bukkit.getMobGoals().removeGoal(bee, VanillaGoal.BEE_GO_TO_HIVE);
    }
}
