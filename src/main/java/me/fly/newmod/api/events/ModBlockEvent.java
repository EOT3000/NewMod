package me.fly.newmod.api.events;

import me.fly.newmod.api.block.ModBlock;
import org.bukkit.block.Block;

public interface ModBlockEvent {
    ModBlock getModBlock();

    Block getBlock();
}
