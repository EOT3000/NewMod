package me.fly.newmod.api.util;

/*import net.minecraft.world.level.material.MaterialMapColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;*/

public class MapColorStorage {
/*    private final Pair<MaterialMapColor, Integer>[][] colors;

    public MapColorStorage(Pair<MaterialMapColor, Integer>[][] colors) {
        this.colors = colors;
    }

    public ItemStack getItems() {
        ItemStack shulker = new ItemStack(Material.SHULKER_BOX);
        BlockStateMeta meta = (BlockStateMeta) shulker.getItemMeta();
        ShulkerBox state = (ShulkerBox) meta.getBlockState();

        for(int xi = 0; xi < 2; xi++) {
            for(int yi = 0; yi < 2; yi++) {
                ItemStack map = new ItemStack(Material.FILLED_MAP);
                MapMeta mapMeta = (MapMeta) map.getItemMeta();
                MapView mapView = Bukkit.createMap(Bukkit.getWorlds().get(0));

                for(MapRenderer renderer : mapView.getRenderers()) {
                    mapView.removeRenderer(renderer);
                }

                mapView.addRenderer(new CustomMapRenderer(colors, xi*128, yi*128));

                mapMeta.setMapView(mapView);

                map.setItemMeta(mapMeta);

                state.getInventory().addItem(map);
            }
        }

        state.update();

        meta.setBlockState(state);

        shulker.setItemMeta(meta);

        return shulker;
    }

    public static class CustomMapRenderer extends MapRenderer {
        private final Pair<MaterialMapColor, Integer>[][] colors;
        private final int x;
        private final int y;

        private CustomMapRenderer(Pair<MaterialMapColor, Integer>[][] colors, int x, int y) {
            this.colors = colors;
            this.x = x;
            this.y = y;
        }

        @Override
        public void render(@NotNull MapView mapView, @NotNull MapCanvas mapCanvas, @NotNull Player player) {
            for(int xi = x; xi < x+128; xi++) {
                for(int yi = y; yi < y+128; yi++) {
                    Pair<MaterialMapColor, Integer> color = colors[xi][yi];

                    mapCanvas.setPixel(xi-x, yi=y, (byte) ((color.getKey().am-1)*4+color.getValue()));
                }
            }
        }
    }*/
}
