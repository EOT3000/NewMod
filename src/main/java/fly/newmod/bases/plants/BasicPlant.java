package fly.newmod.bases.plants;

import fly.newmod.bases.ModItem;
import org.bukkit.Material;
import org.bukkit.event.Listener;

public class BasicPlant extends ModItem implements Listener {
    public BasicPlant(Material material, String name, String id) {
        super(material, name, id);
    }
}
