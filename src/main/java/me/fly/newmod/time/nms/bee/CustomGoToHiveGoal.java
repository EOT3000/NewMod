package me.fly.newmod.time.nms.bee;

import com.destroystokyo.paper.entity.ai.GoalKey;
import me.fly.newmod.NewMod;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.level.pathfinder.PathEntity;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftBee;
import org.bukkit.entity.Bee;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class CustomGoToHiveGoal extends AbstractCustomBeeGoal {
    private static final GoalKey<Bee> KEY = GoalKey.of(Bee.class, new NamespacedKey(NewMod.get(), "bee_go_to_hive"));

    public CustomGoToHiveGoal(Bee bee) {
        super(bee, getHandle(((CraftBee) bee).getHandle()));
    }

    private static PathfinderGoal getHandle(EntityBee a) {
        try {
            Field field = EntityBee.class.getDeclaredField("cH");

            field.setAccessible(true);

            return (EntityBee.e) field.get(a);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Please report to Fly this error");
        }
    }

    @Override
    public boolean shouldActivate() {
        return a.cF != null && !a.fG() && wte() && !this.d(a.cF) && a.H.a_(a.cF).a(TagsBlock.aD);
    }

    private boolean d(BlockPosition pos) {
        if (b(pos, 2)) {
            return true;
        } else {
            PathEntity pathentity = a.G().j();
            return pathentity != null && pathentity.m().equals(pos) && pathentity.j() && pathentity.c();
        }
    }

    boolean b(BlockPosition pos, int distance) {
        return pos.a(a.dg(), distance);
    }

    @Override
    public @NotNull GoalKey<Bee> getKey() {
        return KEY;
    }
}
