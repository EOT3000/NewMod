package me.fly.newmod.time.nms.bee;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalType;
import me.fly.newmod.time.nms.NMSUtils;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.animal.EntityBee;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftBee;
import org.bukkit.entity.Bee;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public abstract class AbstractCustomBeeGoal implements Goal<Bee> {
    protected final EntityBee a;
    protected final Bee bee;
    protected final PathfinderGoal handle;

    protected final PathfinderGoal pollinating;

    protected AbstractCustomBeeGoal(Bee bee, Class<?> clazz) {
        this.bee = bee;
        this.a = ((CraftBee) bee).getHandle();

        this.handle = getHandle(clazz, this.a);

        this.pollinating = NMSUtils.getGoal(NMSUtils.BEE_POLLINATE_GOAL, this.a.bN);
    }

    protected AbstractCustomBeeGoal(Bee bee, PathfinderGoal handle) {
        this.bee = bee;
        this.a = ((CraftBee) bee).getHandle();

        this.handle = handle;

        this.pollinating = NMSUtils.getGoal(NMSUtils.BEE_POLLINATE_GOAL, this.a.bN);
    }


    private static PathfinderGoal getHandle(Class<?> clazz, EntityBee bee) {
        try {
            return NMSUtils.getGoal(clazz, bee.bN);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Please report to Fly this error");
        }
    }

    protected boolean wte() {
        return NMSUtils.wantsToEnter(bee.getLocation(), a, NMSUtils.isPollinating(pollinating));
    }

    @Override
    public boolean shouldStayActive() {
        return this.handle.b();
    }

    @Override
    public void start() {
        this.handle.c();
    }

    @Override
    public void stop() {
        this.handle.d();
    }

    @Override
    public void tick() {
        this.handle.e();
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE);
    }
}
