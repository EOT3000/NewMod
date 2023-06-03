package me.fly.newmod.time.nms.bee;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import me.fly.newmod.NewMod;
import me.fly.newmod.time.nms.NMSUtils;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftBee;
import org.bukkit.entity.Bee;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class CustomEnterHiveGoal extends AbstractCustomBeeGoal {
    private static final GoalKey<Bee> KEY = GoalKey.of(Bee.class, new NamespacedKey(NewMod.get(), "bee_enter_hive"));


    public CustomEnterHiveGoal(Bee bee) {
        super(bee, NMSUtils.BEE_ENTER_HIVE_GOAL);
    }

    @Override
    public boolean shouldActivate() {
        if (a.fZ() && wte() && a.cF.a(a.de(), 2.0D)) {
            if (!a.H.isLoadedAndInBounds(a.cF)) {
                return false;
            }

            TileEntity tileentity = a.H.c_(a.cF);
            if (tileentity instanceof TileEntityBeehive) {
                TileEntityBeehive tileentitybeehive = (TileEntityBeehive)tileentity;
                if (!tileentitybeehive.f()) {
                    return true;
                }

                a.cF = null;
            }
        }

        return false;
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
