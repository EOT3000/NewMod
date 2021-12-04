package fly.newmod.impl.machines;

import fly.newmod.bases.ModItem;
import fly.newmod.setup.Setup;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PhotoViewerInput extends ModItem {
    public PhotoViewerInput() {
        super(Material.DROPPER, "&6Photo Input Block", "photo_viewer_input", 
                new ItemStack(Material.IRON_INGOT), Setup.MAGNET, new ItemStack(Material.IRON_INGOT)
        );
    }
}
