package me.fly.newmod.time.nms.bee;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import me.fly.newmod.NewMod;
import me.fly.newmod.time.nms.NMSUtils;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.animal.EntityBee;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftBee;
import org.bukkit.entity.Bee;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class CustomLocateHiveGoal implements Goal<Bee> {
    private static final GoalKey<Bee> KEY = GoalKey.of(Bee.class, new NamespacedKey(NewMod.get(), "bee_locate_hive"));

    private final EntityBee a;
    private final Bee bee;
    private final PathfinderGoal i;

    public CustomLocateHiveGoal(Bee bee) {
        this.bee = bee;
        this.a = ((CraftBee) bee).getHandle();

        try {
            this.i = NMSUtils.getGoal(NMSUtils.BEE_LOCATE_HIVE_GOAL, a.bN);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Please report to Fly this error");
        }
    }

    @Override
    public boolean shouldActivate() {
        return a.cF != null && (this.i.a() || NMSUtils.wantsToEnter(bee.getLocation(), a, NMSUtils.isPollinating(i)));
    }

    @Override
    public boolean shouldStayActive() {
        return this.i.b();
    }

    @Override
    public void start() {
        this.i.c();
    }

    @Override
    public void stop() {
        this.i.d();
    }

    @Override
    public void tick() {
        this.i.e();
    }

    @Override
    public @NotNull GoalKey<Bee> getKey() {
        return KEY;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE);
    }
}