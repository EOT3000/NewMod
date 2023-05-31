package me.fly.newmod.time.nms;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import me.fly.newmod.NewMod;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.util.PathfinderGoalUtil;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftMob;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class CustomFleeSunGoal implements Goal<Creature> {
    private static final GoalKey<Creature> KEY = GoalKey.of(Creature.class, new NamespacedKey(NewMod.get(), "flee_sun"));

    private final double speed;

    private final Creature mob;
    final EntityCreature a;


    private double x;
    private double y;
    private double z;

    public CustomFleeSunGoal(Creature mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.a = ((CraftCreature) mob).getHandle();
    }

    @Override
    public boolean shouldActivate() {
        if (this.a.P_() != null) {
            return false;
        } else if (!(NewMod.get().getTimeManager().getSkyBrightness(mob.getLocation()) >= 5460)) {
            return false;
        } else if (!this.a.bK()) {
            return false;
        } else if (!this.a.H.g(this.a.dg())) {
            return false;
        } else {
            return this.a.c(EnumItemSlot.f).b() && this.h();
        }
    }

    protected boolean h() {
        Vec3D vec3 = this.getRandomPosition();
        if (vec3 == null) {
            return false;
        } else {
            this.x = vec3.c;
            this.y = vec3.d;
            this.z = vec3.e;
            return true;
        }
    }

    @Override
    public boolean shouldStayActive() {
        return !this.a.G().l();
    }

    @Override
    public void start() {
        this.a.G().a(this.x, this.y, this.z, speed);
        System.out.println("flee started");
    }

    @Nullable
    protected Vec3D getRandomPosition() {
        RandomSource randomSource = this.a.dZ();
        BlockPosition blockPos = this.a.dg();

        for(int i = 0; i < 10; ++i) {
            BlockPosition blockPos2 = blockPos.b(randomSource.a(20) - 10, randomSource.a(6) - 3, randomSource.a(20) - 10);
            if (!this.a.H.g(blockPos2) && this.a.f(blockPos2) < 0.0F) {
                return Vec3D.c(blockPos2);
            }
        }

        return null;
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
