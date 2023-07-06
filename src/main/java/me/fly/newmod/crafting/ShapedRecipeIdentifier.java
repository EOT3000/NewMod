package me.fly.newmod.crafting;

import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class ShapedRecipeIdentifier {
    public boolean matches(CraftingInventory inventory) {
        ItemStack[] i = inventory.getMatrix();

        ItemStack[][] limited = restrictMatrix(new ItemStack[][] {
                {i[0], i[1], i[2]},
                {i[3], i[4], i[5]},
                {i[6], i[7], i[8]}});

        return false;
    }

    private static ItemStack[][] restrictMatrix(ItemStack[][] old) {
        old = restrict(old);

        if(old == null) {
            return null;
        }

        old = rotate(old);

        old = restrict(old);

        if(old == null) {
            return null;
        }

        return rotate(old);
    }

    private static ItemStack[][] restrict(ItemStack[][] old) {
        // Remove top 1 or 2 layers if empty
        old = ft(old);
        old = ft(old);

        // Remove bottom layer if empty
        old = fb(old);

        // If the restricted matrix is now empty, return null
        if(old.length == 0) {
            return null;
        }

        // Remove bottom empty layer, unless only 1 layer remains
        if(old.length != 1) {
            old = fb(old);
        }

        return old;
    }

    private static ItemStack[][] rotate(ItemStack[][] old) {
        ItemStack[][] ret = new ItemStack[old[0].length][];

        for(int y = 0; y < old[0].length; y++) {
            ItemStack[] ins = new ItemStack[old.length];

            for (int x = 0; x < old.length; x++) {
                ins[x] = old[x][y];
            }

            ret[y] = ins;
        }

        return ret;
    }

    private static ItemStack[][] ft(ItemStack[][] a) {
        ItemStack[] s = a[0];

        boolean replace = allE(s);

        if(replace) {
            if(a.length == 3) {
                return new ItemStack[][] {a[1], a[2]};
            } else {
                return new ItemStack[][] {a[1]};
            }
        }

        return a;
    }

    private static ItemStack[][] fb(ItemStack[][] a) {
        ItemStack[] s = a[a.length-1];

        boolean replace = allE(s);

        if(replace) {
            if(a.length == 3) {
                return new ItemStack[][] {a[0], a[1]};
            } else if(a.length == 2) {
                return new ItemStack[][] {a[0]};
            } else {
                return new ItemStack[0][];
            }
        }

        return a;
    }

    private static boolean allE(ItemStack[] s) {
        if(s.length == 3) {
            return e(s[0]) && e(s[1]) && e(s[2]);
        }

        if(s.length == 2) {
            return e(s[0]) && e(s[1]);
        }

        if(s.length == 1) {
            return e(s[0]);
        }

        return true;
    }

    private static boolean e/*mpty*/(ItemStack stack) {
        return stack == null;
    }
}
