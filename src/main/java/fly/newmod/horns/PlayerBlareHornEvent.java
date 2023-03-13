/*
 *  This file is part of GoatHorn. Copyright (c) 2021 SolarRabbit.
 *
 *  GoatHorn is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  GoatHorn is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  See <https://www.gnu.org/licenses/>.
 *
 */

package fly.newmod.horns;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Encapsulates an event where player blares a goat horn in hand. If cancelled,
 * the blare is cancelled, and no sound or cooldown will be applied to the horn.
 */
public class PlayerBlareHornEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack horn;
    private boolean cancelled;

    public PlayerBlareHornEvent(Player who, ItemStack horn) {
        super(who);
        this.horn = horn;
    }

    /**
     * Get the horn that is blown.
     *
     * @return the horn item involved in this event.
     */
    public ItemStack getHorn() {
        return this.horn;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not be
     * executed in the server, but will still pass to other plugins
     * <p>
     * If the event is cancelled, no sound or cooldown will be applied to the horn.
     *
     * @param cancel {@code true} if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
