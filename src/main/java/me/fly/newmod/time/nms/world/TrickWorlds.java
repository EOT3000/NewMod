package me.fly.newmod.time.nms.world;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;

public class TrickWorlds {
    public static final CraftWorld DAY = (CraftWorld) new WorldCreator("always_day_trick_world_nm").createWorld();
    public static final CraftWorld NIGHT = (CraftWorld) new WorldCreator("always_night_trick_world_nm").createWorld();
    public static final CraftWorld NONE = (CraftWorld) new WorldCreator("always_none_trick_world_nm").environment(World.Environment.NETHER).createWorld();

    static {
        DAY.setTime(6000);
        DAY.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        NIGHT.setTime(18000);
        NIGHT.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

        System.out.println("nft? " + NONE.isFixedTime());


    }

    public static void init() {}
}
