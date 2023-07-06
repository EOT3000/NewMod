package me.fly.newmod.technology.producer;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.BlockEventsListener;
import me.fly.newmod.api.block.BlockManager;
import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.events.block.ModBlockTickEvent;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.technology.EnergyComponent;
import me.fly.newmod.technology.data.EnergyHolderBlockData;
import me.fly.newmod.technology.data.EnergyHolderBlockDataImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class SolarGeneratorItemType extends ModItemType {
    private static final Random random = new Random();

    public SolarGeneratorItemType(int baseMax, int randomAdd, int bonusForSide, int capacity, String id, String name, int color) {
        super(Material.DAYLIGHT_DETECTOR, new NamespacedKey("", id), DefaultModItemMeta.class, false,
                new SolarGeneratorBlockType(baseMax, randomAdd, bonusForSide, capacity, null, id), new ItemEventsListener() {}, Component.text(name).color(TextColor.color(color)));

        throw new NullPointerException();
    }

    public SolarGeneratorItemType(int baseMax, int randomAdd, int bonusForSide, int capacity, JavaPlugin plugin, String id, String name, int color) {
        super(Material.DAYLIGHT_DETECTOR, new NamespacedKey(plugin, id), DefaultModItemMeta.class, false,
                new SolarGeneratorBlockType(baseMax, randomAdd, bonusForSide, capacity, plugin, id), new ItemEventsListener() {}, Component.text(name).color(TextColor.color(color)));
    }

    public static class SolarGeneratorBlockType extends ModBlockType implements EnergyComponent {
        private final int baseMax, randomAdd, bonusForSide, capacity;

        public SolarGeneratorBlockType(int baseMax, int randomAdd, int bonusForSide, int capacity, JavaPlugin plugin, String id) {
            super(Material.DAYLIGHT_DETECTOR, new NamespacedKey(plugin, id), EnergyHolderBlockDataImpl.class);

            this.baseMax = baseMax;
            this.randomAdd = randomAdd;
            this.bonusForSide = bonusForSide;
            this.capacity = capacity;

            setListener(new BlockEventsListener() {
                @Override
                public void onBlockTick(ModBlockTickEvent event) {
                    tick(event);
                }
            });
        }

        @Override
        public EnergyComponentType getType() {
            return EnergyComponentType.PRODUCER;
        }

        @Override
        public int getCapacity() {
            return capacity;
        }

        private void tick(ModBlockTickEvent event) {
            int brightness = NewMod.get().getTimeManager().getSkyBrightness(event.getBlock().getLocation());
            double power = (brightness/12000.0)*baseMax+random.nextDouble()*randomAdd;

            int bonus = 0;

            if(isSP(event.getBlock().getLocation(), BlockFace.NORTH)) {
                bonus+=bonusForSide;
            }
            if(isSP(event.getBlock().getLocation(), BlockFace.EAST)) {
                bonus+=bonusForSide;
            }
            if(isSP(event.getBlock().getLocation(), BlockFace.SOUTH)) {
                bonus+=bonusForSide;
            }
            if(isSP(event.getBlock().getLocation(), BlockFace.WEST)) {
                bonus+=bonusForSide;
            }

            power+=(bonus/4.0)*
                    (random.nextDouble()+
                    random.nextDouble()+
                    random.nextDouble()+
                    random.nextDouble());

            EnergyHolderBlockData data = (EnergyHolderBlockData) event.getModBlock().getData();

            data.addCharge((int) Math.round(power));

            event.getModBlock().setData(data);
            event.getModBlock().update();
        }

        private boolean isSP(Location location, BlockFace face) {
            Location l = location.clone();

            BlockManager manager = NewMod.get().getBlockManager();

            return manager.getType(l.add(face.getModX(), face.getModY(), face.getModZ())) instanceof SolarGeneratorBlockType;
        }
    }
}
