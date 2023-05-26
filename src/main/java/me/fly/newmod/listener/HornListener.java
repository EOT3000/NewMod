package me.fly.newmod.listener;

import org.bukkit.GameEvent;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.GenericGameEvent;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.bukkit.util.Vector;

import static org.bukkit.MusicInstrument.*;
import static org.bukkit.Sound.*;

public class HornListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(GenericGameEvent event) {
        if(event.getEvent().equals(GameEvent.HIT_GROUND) ||
                event.getEvent().equals(GameEvent.STEP) ||
                event.getEvent().equals(GameEvent.FLAP) ||
                event.getEvent().equals(GameEvent.SWIM)) {
            return;
        }

        if(event.getEvent().equals(GameEvent.INSTRUMENT_PLAY)) {
            System.out.println("insturment played");

            for(Player p : event.getLocation().getNearbyPlayers(2000)) {
                if(event.getLocation().distance(event.getEntity().getLocation()) != 0) {
                    Vector vector = event.getLocation().subtract(p.getLocation()).toVector().normalize();

                    p.playSound(p.getLocation().add(vector), getHorn((Player) event.getEntity()), 10, 1);
                }
            }

            event.setRadius(1);
        }
    }

    private static Sound getHorn(Player player) {
        MusicInstrument instrument;

        if(player.getInventory().getItemInMainHand().getType().equals(Material.GOAT_HORN)) {
            instrument = ((MusicInstrumentMeta) player.getInventory().getItemInMainHand().getItemMeta()).getInstrument();
        } else if(player.getInventory().getItemInOffHand().getType().equals(Material.GOAT_HORN)) {
            instrument = ((MusicInstrumentMeta) player.getInventory().getItemInOffHand().getItemMeta()).getInstrument();
        } else {
            instrument = null;
        }

        if(PONDER.equals(instrument)) {
            return ITEM_GOAT_HORN_SOUND_0;
        } else if(SING.equals(instrument)) {
            return ITEM_GOAT_HORN_SOUND_1;
        } else if(SEEK.equals(instrument)) {
            return ITEM_GOAT_HORN_SOUND_2;
        } else if(FEEL.equals(instrument)) {
            return ITEM_GOAT_HORN_SOUND_3;
        } else if(ADMIRE.equals(instrument)) {
            return ITEM_GOAT_HORN_SOUND_4;
        } else if(CALL.equals(instrument)) {
            return ITEM_GOAT_HORN_SOUND_5;
        } else if(YEARN.equals(instrument)) {
            return ITEM_GOAT_HORN_SOUND_6;
        } else if(DREAM.equals(instrument)) {
            return ITEM_GOAT_HORN_SOUND_7;
        }

        //In case something goes wrong
        return BLOCK_NOTE_BLOCK_BANJO;
    }
}
