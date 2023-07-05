package me.fly.newmod.metals;


import me.fly.newmod.NewMod;
import me.fly.newmod.metals.items.FilledOreSponge;
import me.fly.newmod.metals.items.meta.FilledOreSpongeMeta;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MetalsPlugin extends NewMod.ModExtension {
    private Random random = new Random();

    private static MetalsPlugin INSTANCE;

    public MetalsPlugin() {
        INSTANCE = this;
    }

    public static MetalsPlugin get() {
        return INSTANCE;
    }

    @Override
    public void load() {
        new FilledOreSpongeMeta.FilledOreSpongeMetaSerializer();

        MetalsModuleTypes.init();
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new FilledOreSponge.SpongeListener(), this);
    }

    /*@EventHandler
    public void onLightning(LightningStrikeEvent event) {
        List<Block> blocks = getBlock(event.getLightning().getCausingEntity().getLocation());

        for(Block block : blocks) {
            block.setType(Material.AIR);

            for (int x = 0; x < 54; x++) {
                double r = random.nextDouble();

                /*for (ItemStack stack : MetalsModuleTypes.oreMap.get(block.getType()).keySet()) {
                    double probability = MetalsModuleTypes.oreMap.get(block.getType()).get(stack);

                    if (r < probability) {
                        block.getWorld().dropItem(block.getLocation(), Objects.requireNonNullElse(stack, MetalsModuleTypes.SILICON_NUGGET));

                        break;
                    } else {
                        r -= probability;
                    }
                }
            }
        }
    }*/

    private List<Block> getBlock(Location location) {
        List<Block> blocks = new ArrayList<>();

        for(int x = -1; x < 2; x++) {
            for(int y = -1; y < 2; y++) {
                for(int z = -1; z < 2; z++) {
                    if(MetalsModuleTypes.oreMap.containsKey(location.clone().add(x, y, z).getBlock().getType())) {
                        blocks.add(location.clone().add(x, y, z).getBlock());
                    }
                }
            }
        }

        return blocks;
    }
}
