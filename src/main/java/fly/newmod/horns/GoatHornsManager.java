package fly.newmod.horns;

import fly.newmod.parent.NewMod;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MusicInstrumentMeta;

import java.util.HashSet;
import java.util.UUID;

import static org.bukkit.MusicInstrument.*;
import static org.bukkit.Sound.*;


public class GoatHornsManager implements Listener {
    private final HashSet<UUID> cooldowns = new HashSet<>();

    public void enable(NewMod newMod) {
        Bukkit.getPluginManager().registerEvents(this, newMod);
    }

    @EventHandler
    public void onHornUse(PlayerInteractEvent evt) {
        System.out.println(evt.getItem());

        if (evt.useItemInHand() == Event.Result.DENY || evt.getAction() != Action.RIGHT_CLICK_AIR)
            return;
        ItemStack item = evt.getItem();
        if (!isHorn(item))
            return;
        Player player = evt.getPlayer();
        if (hasHornInBothHand(player) && evt.getHand() == EquipmentSlot.OFF_HAND)
            return;
        if (cooldowns.contains(player.getUniqueId()))
            return;

        PlayerBlareHornEvent event = new PlayerBlareHornEvent(player, item);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            event.getPlayer().getWorld().playSound(event.getPlayer(),
                    getSound(((MusicInstrumentMeta) event.getHorn().getItemMeta()).getInstrument()), SoundCategory.RECORDS, 64.5f, 1.0f);

            player.setCooldown(Material.GOAT_HORN, 140);

            UUID uuid = player.getUniqueId();
            cooldowns.add(uuid);
            Bukkit.getScheduler().runTaskLater(NewMod.get(), () -> this.cooldowns.remove(uuid), 140);
        }
    }

    private static boolean isHorn(ItemStack stack) {
        if (stack == null) {
            return false;
        }

        return stack.getType().equals(Material.GOAT_HORN);
    }

    private static boolean hasHornInBothHand(Player player) {
        EntityEquipment equipment = player.getEquipment();
        ItemStack main = equipment.getItemInMainHand();
        ItemStack off = equipment.getItemInOffHand();
        return isHorn(main) && isHorn(off);
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
