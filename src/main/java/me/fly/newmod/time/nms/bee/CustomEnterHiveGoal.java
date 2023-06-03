package me.fly.newmod.time.nms.bee;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import me.fly.newmod.NewMod;
import me.fly.newmod.time.nms.NMSUtils;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.level.World;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftBee;
import org.bukkit.entity.Bee;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class CustomEnterHiveGoal implements Goal<Bee> {
    private static final GoalKey<Bee> KEY = GoalKey.of(Bee.class, new NamespacedKey(NewMod.get(), "bee_enter_hive"));

    private final EntityBee a;
    private final Bee bee;
    private final PathfinderGoal d;

    public CustomEnterHiveGoal(Bee bee) {
        this.bee = bee;
        this.a = ((CraftBee) bee).getHandle();

        try {
            this.d = NMSUtils.getGoal(NMSUtils.BEE_ENTER_HIVE_GOAL, a.bN);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Please report to Fly this error");
        }
    }

    @Override
    public boolean shouldActivate() {
        World world = a.H;

        return a.cF != null && (this.d.a() || NMSUtils.wantsToEnter(bee.getLocation(), a, NMSUtils.isPollinating(d)));
    }

    @Override
    public boolean shouldStayActive() {
        return this.d.b();
    }

    @Override
    public void start() {
        this.d.c();
    }

    @Override
    public void stop() {
        this.d.d();
    }

    @Override
    public void tick() {
        this.d.e();
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
