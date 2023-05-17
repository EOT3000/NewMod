package me.fly.newmod.api.util;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.BlockManager;
import me.fly.newmod.api.block.ModBlock;
import me.fly.newmod.api.block.ModBlockType;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class BlockUtils {
    public static List<ModBlock> getAllBlocks(Predicate<ModBlockType> typePredicate, Location location, boolean limitOne) {
        List<ModBlock> ret = new ArrayList<>();

        for(int i = 0; i < 6; i++) {
            BlockFace face = BlockFace.values()[i];
            ModBlock r = isPredicate(typePredicate, location, face.getModX(), face.getModY(), face.getModZ());

            if(r != null) {
                ret.add(r);

                if(limitOne) {
                    return ret;
                }
            }
        }

        return ret;
    }

    private static ModBlock isPredicate(Predicate<ModBlockType> typePredicate, Location location, int x, int y, int z) {
        //if(sqrt(x*x+y*y+z*z) != 1) {
        //    return null;
        //}

        BlockManager manager = NewMod.get().getBlockManager();
        Location l = location.clone().add(x, y, z);
        ModBlockType b = manager.getType(l.getBlock());

        return b != null && typePredicate.test(b) ? new ModBlock(l.getBlock()) : null;
    }
}
