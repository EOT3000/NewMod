package me.fly.newmod.time.nms;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import me.fly.newmod.NewMod;
import me.fly.newmod.time.TimeManager;
import net.minecraft.world.entity.EntityCreature;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftCreature;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class CustomFireInDayGoal implements Goal<Creature> {
    private static final GoalKey<Creature> KEY = GoalKey.of(Creature.class, new NamespacedKey(NewMod.get(), "fire_in_day"));

    private final Creature mob;
    final EntityCreature a;

    public CustomFireInDayGoal(Creature mob) {
        this.mob = mob;
        this.a = ((CraftCreature) mob).getHandle();
    }

    @Override
    public void tick() {
        a.f(8);
    }

    @Override
    public boolean shouldActivate() {
        return NewMod.get().getTimeManager().getSkyBrightness(mob.getLocation()) > 5500;
    }

    @Override
    public @NotNull GoalKey<Creature> getKey() {
        return KEY;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.UNKNOWN_BEHAVIOR);
    }
}
