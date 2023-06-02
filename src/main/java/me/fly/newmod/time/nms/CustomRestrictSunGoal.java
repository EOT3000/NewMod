package me.fly.newmod.time.nms;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import com.destroystokyo.paper.entity.ai.PaperCustomGoal;
import me.fly.newmod.NewMod;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.util.PathfinderGoalUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftMob;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class CustomRestrictSunGoal implements Goal<Creature> {
    private static final GoalKey<Creature> KEY = GoalKey.of(Creature.class, new NamespacedKey(NewMod.get(), "restrict_sun"));

    private final Creature mob;
    final EntityInsentient a;

    public CustomRestrictSunGoal(Creature mob) {
        this.mob = mob;
        this.a = ((CraftMob) mob).getHandle();
    }

    @Override
    public boolean shouldActivate() {
        System.out.println("CRSG sa");
        return NewMod.get().getTimeManager().getSkyBrightness(mob.getLocation()) >= 5460
                && a.c(EnumItemSlot.f).b()
                && PathfinderGoalUtil.a(a);
    }

    @Override
    public void start() {
        ((Navigation) a.G()).d(true);
        System.out.println("CRSG start");
    }

    @Override
    public void stop() {
        System.out.println("CRSG stop");
        if (PathfinderGoalUtil.a(a)) {
            ((Navigation) a.G()).d(false);
        }
    }

    @Override
    public @NotNull GoalKey<Creature> getKey() {
        return KEY;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE);
    }
}
