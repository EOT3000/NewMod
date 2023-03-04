package fly.newmod.time.nms;

import fly.newmod.parent.NewMod;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.util.PathfinderGoalUtil;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftMob;
import org.bukkit.entity.Mob;

public class NMSBehaviourTime {
    //Copied and modified from NMS

    public static boolean shouldActivate(Mob mob) {
        EntityInsentient a = ((CraftMob) mob).getHandle();

        return NewMod.get().getTimeManager().getSkyBrightness(mob.getLocation()) >= 5460
                && a.c(EnumItemSlot.f).b()
                && PathfinderGoalUtil.a(a);
    }


    public static void start(Mob mob) {
        EntityInsentient a = ((CraftMob) mob).getHandle();

        ((Navigation) a.E()).d(true);
    }


    public static void stop(Mob mob) {
        EntityInsentient a = ((CraftMob) mob).getHandle();

        if (PathfinderGoalUtil.a(a)) {
            ((Navigation) a.E()).d(false);
        }
    }
}
