package me.fly.newmod.time.nms.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;

public class TrickWorlds {
    public static final World DAY = new WorldCreator("always_day_trick_world_nm").createWorld();
    public static final World NIGHT = new WorldCreator("always_night_trick_world_nm").createWorld();
    public static final World NONE = new WorldCreator("always_none_trick_world_nm").environment(World.Environment.NETHER).createWorld();

    static {
        DAY.setTime(6000);
        NIGHT.setTime(18000);

        System.out.println("nft? " + NONE.isFixedTime());
    }

    public static void init() {}
}
