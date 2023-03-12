package fly.newmod.horns;

import fly.newmod.parent.NewMod;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.MusicInstrumentMeta;

import static org.bukkit.MusicInstrument.*;
import static org.bukkit.Sound.*;


public class GoatHornsManager implements Listener {
    public void enable(NewMod newMod) {
        Bukkit.getPluginManager().registerEvents(this, newMod);
    }

    @EventHandler
    public void onHornBlow(PlayerStopUsingItemEvent event) {
        if(event.getItem().getType().equals(Material.GOAT_HORN)) {
            MusicInstrumentMeta meta = (MusicInstrumentMeta) event.getItem().getItemMeta();

            event.getPlayer().getWorld().playSound(event.getPlayer(), getSound(meta.getInstrument()), SoundCategory.RECORDS, 64.5f, 1.0f);
        }
    }

    private static Sound getSound(MusicInstrument instrument) {
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
