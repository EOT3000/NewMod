package fly.newmod.api.event.block;

import fly.newmod.api.block.ModBlock;
import org.bukkit.block.Block;

public interface ModBlockEvent {
    ModBlock getModBlock();

    Block getBlock();
}
