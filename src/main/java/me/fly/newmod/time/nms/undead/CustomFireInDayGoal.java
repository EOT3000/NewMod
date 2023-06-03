package me.fly.newmod.time.nms.undead;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import me.fly.newmod.NewMod;
import me.fly.newmod.time.TimeManager;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.util.PathfinderGoalUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftCreature;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Random;

public class CustomFireInDayGoal implements Goal<Creature> {
    private static final GoalKey<Creature> KEY = GoalKey.of(Creature.class, new NamespacedKey(NewMod.get(), "fire_in_day"));

    private final Creature mob;
    final EntityCreature a;

    public CustomFireInDayGoal(Creature mob) {
        this.mob = mob;
        this.a = ((CraftCreature) mob).getHandle();
    }

    @Override
    public void start() {
        a.g(12*20);

        if(PathfinderGoalUtil.a(a)) {
            ((Navigation) a.G()).d(true);
        }
    }

    @Override
    public boolean shouldStayActive() {
        return false;
    }

    @Override
    public boolean shouldActivate() {
        boolean sa = canBurn() &&
                NewMod.get().getTimeManager().getSkyBrightness(mob.getLocation()) > 5500 &&
                a.au() <= 4*20;

        return sa;
    }

    public boolean canBurn() {
        boolean cb = !(a.aT() || ((Entity) a).j() || a.k() || a.az || a.aA || !a.H.g(BlockPosition.a(a.dl(), a.dp(), a.dr())) || !a.c(EnumItemSlot.f).b());

        /*System.out.println("abcde?");
        System.out.println("in water? " + a.aT());
        System.out.println("in rain? " + ((Entity) a).j());
        System.out.println("in bubble? " + a.k());
        System.out.println("in powder snow? " + a.az);
        System.out.println("was in powder snow? " + a.aA);
        System.out.println("cannot see sky? " + !a.H.g(BlockPosition.a(a.dl(), a.dp(), a.dr())));
        System.out.println("item on head? " + !a.c(EnumItemSlot.f).b());
        System.out.println("can burn? " + cb);
        System.out.println("abcde!");*/

        return cb;
    }

    @Override
    public @NotNull GoalKey<Creature> getKey() {
        return KEY;
    }

    @Override
    public @NotNull EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.LOOK);
    }
}
