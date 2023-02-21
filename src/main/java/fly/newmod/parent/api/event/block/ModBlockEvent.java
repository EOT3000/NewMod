package fly.newmod.parent.api.event.block;

import fly.newmod.parent.api.block.ModBlock;
import org.bukkit.block.Block;

public interface ModBlockEvent {
    ModBlock getModBlock();

    Block getBlock();
}
