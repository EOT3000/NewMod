package fly.newmod.impl.crafting;

import com.sk89q.worldedit.event.platform.BlockInteractEvent;
import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import fly.newmod.setup.BlockStorage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Smelter extends ModItem implements Listener {
    private static Random random = new Random();
    private List<ItemStack> smeltable = new ArrayList<>();

    public Smelter() {
        super(Material.CAULDRON, "&cSmelter", "smelter");
    }

    public void addToSmeltable(ItemStack stack) {
        smeltable.add(stack);
    }

    @Override
    public void tick(Location location) {
        Material material = location.clone().subtract(0, 1, 0).getBlock().getType();

        if (location.getBlock().getType().equals(Material.POWDER_SNOW_CAULDRON)) {
            if (random.nextDouble() < 0.01 && material.equals(Material.FIRE)) {
                location.getBlock().setType(Material.LAVA_CAULDRON);
            }
        }
    }

    @Override
    public boolean click(Location location, Action type, Player player, ItemStack stack) {
        BlockStorage storage;

        if(type.equals(Action.RIGHT_CLICK_BLOCK)) {
            if(location.getBlock().getType().equals(Material.POWDER_SNOW_CAULDRON)) {
                return false;
            } else if(location.getBlock().getType().equals(Material.LAVA_CAULDRON)) {
                ItemMeta meta = stack.getItemMeta();

                meta.setDisplayName(ChatColor.RED + "Molten Metal");
                meta.setLore(Arrays.asList());
            }
        }

        return true;
    }

    @EventHandler
    public void cauldronLevelChange(CauldronLevelChangeEvent event) {
        BlockStorage storage = NewMod.get().getBlockStorage();

        if("smelter".equalsIgnoreCase(storage.getData(event.getBlock().getLocation(), "id"))) {
            Location location = event.getBlock().getLocation();

            switch (event.getReason()) {
                case BUCKET_EMPTY:
                case BOTTLE_EMPTY:
                    event.setCancelled(true);
            }
        }
    }
}
