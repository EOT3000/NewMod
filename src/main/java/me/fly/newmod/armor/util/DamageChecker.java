package me.fly.newmod.armor.util;

import me.fly.newmod.armor.model.ArmorSection;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftBlock;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class DamageChecker {
    //0-0.28125 boots
    //0.15625-1.0 legs
    //0.78125-1.40625 chest
    //1.8125 helm


    //ratios for armor height, taken from player values and scaled such that it runs from 0-1 instead of 0-1.8125 as for a player
    public static final double SCALE_FACTOR = 1.0/1.8125;

    public static final double BOOT_LOW = 0;
    public static final double BOOT_HIGH = 0.28125*SCALE_FACTOR;

    public static final double LEGS_LOW = 0.15625*SCALE_FACTOR;
    public static final double LEGS_HIGH = SCALE_FACTOR;

    public static final double CHEST_LOW = 0.78125*SCALE_FACTOR;
    public static final double CHEST_HIGH = 1.40625*SCALE_FACTOR;

    public static final double HELM_LOW = 1.40625*SCALE_FACTOR;
    public static final double HELM_HIGH = 1.0;

    public static List<ArmorSection> affectsLava(Entity entity) {
        BoundingBox bb = entity.getBoundingBox();

        double bottom = bb.getMinY();
        double scale = (bb.getMaxY()-bb.getMinY());

        BoundingBox boots = newAdjusted(bb, bottom, scale, BOOT_LOW, BOOT_HIGH);
        BoundingBox legs = newAdjusted(bb, bottom, scale, LEGS_LOW, LEGS_HIGH);
        BoundingBox chest = newAdjusted(bb, bottom, scale, CHEST_LOW, CHEST_HIGH);
        BoundingBox helm = newAdjusted(bb, bottom, scale, HELM_LOW, HELM_HIGH);

        List<ArmorSection> sections = new ArrayList<>();

        //List<Material> bbl = blocksIn(boots, entity.getWorld());
        //List<Material> bll = blocksIn(legs, entity.getWorld());
        //List<Material> bcl = blocksIn(chest, entity.getWorld());
        //List<Material> bhl = blocksIn(helm, entity.getWorld());

        if(inLava(boots, entity.getWorld())) {
            sections.add(ArmorSection.BOOTS);
        }

        if(inLava(legs, entity.getWorld())) {
            sections.add(ArmorSection.LEGS);
        }

        if(inLava(chest, entity.getWorld())) {
            sections.add(ArmorSection.CHEST);
        }

        if(inLava(helm, entity.getWorld())) {
            sections.add(ArmorSection.HEAD);
        }

        //System.out.println("boots: " + bcl);
        //System.out.println("legs: " + bll);
        //System.out.println("chest: " + bcl);
        //System.out.println("helm: " + bhl);

        return sections;
    }

    public static boolean inLava(BoundingBox box, World world) {
        int startX = (int)Math.floor(box.getMinX())-1;
        int startY = (int)Math.floor(box.getMinY())-1;
        int startZ = (int)Math.floor(box.getMinZ())-1;

        int endX = (int)Math.ceil(box.getMaxX())+1;
        int endY = (int)Math.ceil(box.getMaxY())+1;
        int endZ = (int)Math.ceil(box.getMaxZ())+1;

        //System.out.println("main: " + box);

        for(int x = startX; x <= endX; x++) {
            for(int y = startY; y <= endY; y++) {
                for(int z = startZ; z <= endZ; z++) {
                    Block block = world.getBlockAt(x,y,z);

                    if(block.getType().equals(Material.LAVA)) {
                        Fluid base = ((CraftBlock) block).getNMS().u();

                        VoxelShape shape = base.d(((CraftBlock) block).getCraftWorld().getHandle(), new BlockPosition(x,y,z));

                        for(AxisAlignedBB bb : shape.d()) {
                            //System.out.println(x + " " + y + " " + z + ": " + bb);

                            BoundingBox part = new BoundingBox(x+bb.a, y+bb.b, z+bb.c,
                                    x+bb.d, y+bb.e, z+bb.f);

                            if(part.overlaps(new BoundingBox(box.getMinX(), box.getMinY(), box.getMinZ(),
                                    box.getMaxX(), box.getMaxY(), box.getMaxZ()))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static List<ArmorSection> affectsProjectile(Entity entity, Entity projectile) {
        BoundingBox bb = entity.getBoundingBox();

        double bottom = bb.getMinY();
        double scale = (bb.getMaxY()-bb.getMinY());

        BoundingBox boots = newAdjusted(bb, bottom, scale, BOOT_LOW, BOOT_HIGH);
        BoundingBox legs = newAdjusted(bb, bottom, scale, LEGS_LOW, LEGS_HIGH);
        BoundingBox chest = newAdjusted(bb, bottom, scale, CHEST_LOW, CHEST_HIGH);
        BoundingBox helm = newAdjusted(bb, bottom, scale, HELM_LOW, HELM_HIGH);

        List<ArmorSection> sections = new ArrayList<>();

        if(boots.overlaps(projectile.getBoundingBox())) {
            sections.add(ArmorSection.BOOTS);
        } else if(legs.overlaps(projectile.getBoundingBox())) {
            sections.add(ArmorSection.LEGS);
        } else if(chest.overlaps(projectile.getBoundingBox())) {
            sections.add(ArmorSection.CHEST);
        } else if(helm.overlaps(projectile.getBoundingBox())) {
            sections.add(ArmorSection.HEAD);
        }

        return sections;
    }

    private static BoundingBox newAdjusted(BoundingBox old, double bottom, double scale, double low, double high) {
        BoundingBox n = new BoundingBox();

        n.resize(old.getMinX(), bottom+low*scale, old.getMinZ(), old.getMaxX(), bottom+high*scale, old.getMaxZ());

        return n;
    }


}
