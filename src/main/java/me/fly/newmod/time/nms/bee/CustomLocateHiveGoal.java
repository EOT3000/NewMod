package me.fly.newmod.time.nms.bee;

import com.destroystokyo.paper.entity.ai.GoalKey;
import me.fly.newmod.NewMod;
import me.fly.newmod.time.nms.NMSUtils;
import net.minecraft.world.entity.animal.EntityBee;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Bee;
import org.jetbrains.annotations.NotNull;

public class CustomLocateHiveGoal extends AbstractCustomBeeGoal {
    private static final GoalKey<Bee> KEY = GoalKey.of(Bee.class, new NamespacedKey(NewMod.get(), "bee_locate_hive"));

    public CustomLocateHiveGoal(Bee bee) {
        super(bee, NMSUtils.BEE_LOCATE_HIVE_GOAL);
    }

    @Override
    public boolean shouldActivate() {
        return NMSUtils.newHiveCooldown(a) == 0 && !a.fZ() && wte();
    }

    @Override
    public @NotNull GoalKey<Bee> getKey() {
        return KEY;
    }
}