package me.fly.newmod.time.nms.bee;

import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import me.fly.newmod.NewMod;
import me.fly.newmod.time.nms.NMSUtils;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import org.bukkit.NamespacedKey;
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
        if (a.fZ() && wte() && a.cG.a(a.dg(), 2.0D)) {
            if (!a.dI().isLoadedAndInBounds(a.cG)) {
                return false;
            }

            TileEntity tileentity = a.dI().c_(a.cG);
            if (tileentity instanceof TileEntityBeehive) {
                TileEntityBeehive tileentitybeehive = (TileEntityBeehive)tileentity;
                if (!tileentitybeehive.f()) {
                    return true;
                }

                a.cG = null;
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
