package me.fly.newmod.time.nms;

import com.destroystokyo.paper.entity.ai.VanillaGoal;
import me.fly.newmod.time.nms.bee.CustomEnterHiveGoal;
import me.fly.newmod.time.nms.bee.CustomGoToHiveGoal;
import me.fly.newmod.time.nms.undead.CustomFireInDayGoal;
import me.fly.newmod.time.nms.undead.CustomFleeSunGoal;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.monster.EntitySkeletonAbstract;
import net.minecraft.world.entity.monster.EntityZombie;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftMob;
import org.bukkit.entity.Bee;
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

    public static void addBeeGoals(Bee bee) {
        Bukkit.getMobGoals().removeGoal(bee, VanillaGoal.BEE_ENTER_HIVE);
        Bukkit.getMobGoals().removeGoal(bee, VanillaGoal.BEE_GO_TO_HIVE);

        Bukkit.getMobGoals().addGoal(bee, 1, new CustomEnterHiveGoal(bee));
        Bukkit.getMobGoals().addGoal(bee, 5, new CustomGoToHiveGoal(bee));
    }
}
