package fly.newmod.time.ai;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import fly.newmod.parent.NewMod;
import fly.newmod.time.TimeManager;
import org.bukkit.entity.AbstractSkeleton;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class SkeletonHideFromSunGoal implements Goal<AbstractSkeleton> {
    private AbstractSkeleton skeleton;

    public SkeletonHideFromSunGoal(AbstractSkeleton skeleton) {
        this.skeleton = skeleton;
    }

    @Override
    public void start() {

    }

    @Override
    public boolean shouldActivate() {
        return NewMod.get().getTimeManager().getSkyBrightness(skeleton.getLocation()) > 5460;
    }

    @Override
    public @NotNull GoalKey<AbstractSkeleton> getKey() {
        return null;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE);
    }
}
