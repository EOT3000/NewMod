package fly.newmod.time.behaviour;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import fly.newmod.parent.NewMod;
import fly.newmod.time.nms.NMSBehaviourTime;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class RestrictSunGoal implements Goal<Mob> {
    private final Mob mob;

    public RestrictSunGoal(Mob mob) {
        this.mob = mob;
    }

    @Override
    public void start() {
        NMSBehaviourTime.start(mob);
    }

    @Override
    public void stop() {
        NMSBehaviourTime.stop(mob);
    }

    @Override
    public boolean shouldActivate() {
        return NMSBehaviourTime.shouldActivate(mob);
    }

    @Override
    public @NotNull GoalKey<Mob> getKey() {
        return GoalKey.of(Mob.class, new NamespacedKey(NewMod.get(), "restrict_sun"));
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE);
    }
}
