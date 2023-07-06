package me.fly.newmod.crafting;

import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class ShapedRecipeIdentifier {
    public boolean matches(CraftingInventory inventory) {
        ItemStack[] i = inventory.getMatrix();

        ItemStack[] limited = restrictMatrix(new ItemStack[][] {
                {i[0], i[1], i[2]},
                {i[3], i[4], i[5]},
                {i[6], i[7], i[8]}});
    }

    private ItemStack[] restrictMatrix(ItemStack[][] old) {


        rotate(old);
    }

    private ItemStack[][] restrict(ItemStack[][] old) {
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

    private ItemStack[][] rotate(ItemStack[][] old) {
        ItemStack[][] ret = new ItemStack[3][];

        for(int x = 0; x < old.length; x++) {
            ItemStack[] ins = new ItemStack[old.length];

            for(int y = 0; y < 3; y++) {
                ins[x] = old[x][y];
            }

        }
    }

    private ItemStack[][] ft(ItemStack[][] a) {
        ItemStack[] s = a[0];

        boolean replace = e(s[0]) && e(s[1]) && e(s[2]);

        if(replace) {
            if(a.length == 3) {
                return new ItemStack[][] {a[1], a[2]};
            } else {
                return new ItemStack[][] {a[1]};
            }
        }

        return a;
    }

    private ItemStack[][] fb(ItemStack[][] a) {
        ItemStack[] s = a[a.length-1];

        boolean replace = e(s[0]) && e(s[1]) && e(s[2]);

        if(replace) {
            if(a.length == 3) {
                return new ItemStack[][] {a[0], a[1]};
            } else if(a.length == 2) {
                return new ItemStack[][] {a[1]};
            } else {
                return new ItemStack[0][];
            }
        }

        return a;
    }

    private boolean e/*mpty*/(ItemStack stack) {
        return stack == null;
    }
}
