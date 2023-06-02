package me.fly.newmod.time.nms;

import com.destroystokyo.paper.entity.ai.VanillaGoal;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.monster.EntitySkeletonAbstract;
import net.minecraft.world.entity.monster.EntityZombie;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftMob;
import org.bukkit.entity.Creature;

public class NMSUtils {
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
}
