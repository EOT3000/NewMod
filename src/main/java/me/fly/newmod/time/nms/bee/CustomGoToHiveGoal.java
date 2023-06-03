package me.fly.newmod.time.nms.bee;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import me.fly.newmod.NewMod;
import me.fly.newmod.time.TimeManager;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.level.pathfinder.PathEntity;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftBee;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.EnumSet;

public class CustomGoToHiveGoal implements Goal<Bee> {
    private static final GoalKey<Bee> KEY = GoalKey.of(Bee.class, new NamespacedKey(NewMod.get(), "bee_go_to_hive"));

    private final EntityBee a;
    private final Bee bee;
    private final EntityBee.e e;

    public CustomGoToHiveGoal(Bee bee) {
        this.bee = bee;
        this.a = ((CraftBee) bee).getHandle();

        try {
            Field field = EntityBee.class.getDeclaredField("cH");

            field.setAccessible(true);

            this.e = (EntityBee.e) field.get(this.a);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Please report to Fly this error");
        }
    }

    @Override
    public boolean shouldActivate() {
        return this.e.h() || NewMod.get().getTimeManager().getSkyBrightness(bee.getLocation()) < 5458;
    }

    @Override
    public boolean shouldStayActive() {
        return this.e.i();
    }

    @Override
    public void start() {
        this.e.c();
    }

    @Override
    public void stop() {
        this.e.d();
    }

    @Override
    public void tick() {
        this.e.e();
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
