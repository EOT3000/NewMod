package fly.newmod.impl.items;

import fly.newmod.NewMod;
import fly.newmod.utils.ColorUtils;
import fly.newmod.utils.GeometryUtils;
import fly.newmod.utils.MapColorStorage;
import fly.newmod.utils.Pair;
//import net.minecraft.world.level.material.MaterialMapColor;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Camera {
/*    public static ItemStack newMap(Location location) {
        Pair<MaterialMapColor, Integer>[][] vectors = new Pair[256][];

        for (int x = 0; x < 256; x++) {
            Pair<MaterialMapColor, Integer>[] v1 = new Pair[256];

            for (int y = 0; y < 256; y++) {
                int x2 = x - 128;
                int y2 = y - 128;

                Vector coordinates = GeometryUtils.getRelative(location, new Vector(3 * (y2 / 256.0), 3 * (x2 / 256.0), 1));
                Vector newVector = coordinates.clone().subtract(location.toVector()).normalize();

                RayTraceResult result = location.getWorld().rayTraceBlocks(location, newVector, 96, FluidCollisionMode.ALWAYS);

                if (result == null) {
                    v1[y] = new Color(0, 0, 255).getNearestMapColor();
                } else {
                    v1[y] = new Color(255, 255, 0).getNearestMapColor();
                }

            }

            vectors[x] = v1;
        }


        //TODO: change this to a static method
        return new MapColorStorage(vectors).getItems();
    }

    public static class Color {
        public final int r,g,b;

        public Color(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public static void main(String[] args) {
            Pair<MaterialMapColor, Integer> color = new Color(255, 255, 0).getNearestMapColor();

            int colorInt = color.getKey().a(color.getValue());

            int red = (colorInt >> 16) & 0xff;
            int green = (colorInt >> 8) & 0xff;
            int blue = colorInt & 0xff;

            System.out.println(red);
            System.out.println(green);
            System.out.println(blue);

            System.out.println(color.getKey().am);
        }

        private static Map<Color, Pair<MaterialMapColor, Integer>> CACHE = new HashMap<>();

        public Pair<MaterialMapColor, Integer> getNearestMapColor() {
            if(CACHE.containsKey(this)) {
                return CACHE.get(this);
            }

            double recordDistance = 255;

            MaterialMapColor mapColor = MaterialMapColor.b;
            int mapI = 0;

            for(MaterialMapColor color : MaterialMapColor.a) {
                if(color != null) {
                    for (int i = 0; i <= 4; i++) {
                        int colorInt = color.a(i);

                        int red = (colorInt >> 16) & 0xff;
                        int green = (colorInt >> 8) & 0xff;
                        int blue = colorInt & 0xff;

                        int[] lab = new int[3];

                        ColorUtils.rgb2lab(red, green, blue, lab);

                        int[] lab2 = new int[3];

                        ColorUtils.rgb2lab(r, g, b, lab2);

                        double distance = ColorUtils.calculateDeltaE(lab[0], lab[1], lab[2], lab2[0], lab2[1], lab2[2]);

                        if (distance < recordDistance) {
                            recordDistance = distance;

                            mapColor = color;
                            mapI = i;
                        }
                    }
                }
            }

            return new Pair<>(mapColor, mapI);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Color color = (Color) o;
            return r/2 == color.r/2 && g/2 == color.g/2 && b/2 == color.b/2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r/2, g/2, b/2);
        }
    }*/
}
