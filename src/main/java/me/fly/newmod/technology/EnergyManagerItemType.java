package me.fly.newmod.technology;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.BlockEventsListener;
import me.fly.newmod.api.block.BlockManager;
import me.fly.newmod.api.block.ModBlock;
import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.events.block.ModBlockTickEvent;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.technology.data.EnergyHolderBlockData;
import me.fly.newmod.technology.data.EnergyHolderBlockDataImpl;
import me.fly.newmod.technology.link.SolarLinkItemType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class EnergyManagerItemType extends ModItemType {
    public EnergyManagerItemType(Material material, int capacity, String id, String name, int color) {
        super(material, new NamespacedKey("", id), DefaultModItemMeta.class, false,
                new EnergyManagerBlockType(material, capacity, null, id), new ItemEventsListener() {}, Component.text(name).color(TextColor.color(color)));
    }

    public EnergyManagerItemType(Material material, int capacity, JavaPlugin plugin, String id, String name, int color) {
        super(material, new NamespacedKey(plugin, id), DefaultModItemMeta.class, false,
                new EnergyManagerBlockType(material, capacity, plugin, id), new ItemEventsListener() {}, Component.text(name).color(TextColor.color(color)));
    }

    public static class EnergyManagerBlockType extends ModBlockType implements EnergyComponent {
        private final int capacity;

        public EnergyManagerBlockType(Material material, int capacity, JavaPlugin plugin, String id) {
            super(material, new NamespacedKey(plugin, id), EnergyHolderBlockDataImpl.class);

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
            return EnergyComponentType.MANAGER;
        }

        @Override
        public int getCapacity() {
            return capacity;
        }

        public void tick(ModBlockTickEvent event) {
            collectSolarLinks(event);
        }

        private void collectSolarLinks(ModBlockTickEvent event) {
            List<Location> list = new ArrayList<>();
            findAndAdd(event.getBlock().getLocation(), list, new ArrayList<>(), 4);

            List<ModBlock> hp = new ArrayList<>();
            BlockManager bm = NewMod.get().getBlockManager();
            EnergyHolderBlockData m = (EnergyHolderBlockData) event.getModBlock().getData();

            int excess = 0;
            int managerCap = Math.min(m.getCapacity()-m.getCharge(), excess);

            for(Location location : list) {
                ModBlock b = new ModBlock(location);
                EnergyHolderBlockData data = (EnergyHolderBlockData) b.getData();

                if(data.getCharge() > data.getCapacity()/2) {
                    hp.add(b);
                    excess+=data.getCharge()-(data.getCapacity()/2);
                }
            }

            for(ModBlock b : hp) {
                EnergyHolderBlockData d = (EnergyHolderBlockData) b.getData();

                int pe = d.getCharge()-(d.getCapacity()/2);
                int trans = (int) (pe/(excess*1.0))*managerCap;

                d.removeCharge(trans);
                b.setData(d);
                b.update();

                m.addCharge(trans);
            }

            if(m.getCharge() == m.getCapacity()) {
                event.getModBlock().setData(m);
                event.getModBlock().update();

                return;
            }

            managerCap = m.getCapacity()-m.getCharge();

            for(Location location : list) {
                EnergyHolderBlockData data = (EnergyHolderBlockData) bm.getType(location);

                data.removeCharge(managerCap/list.size());
                m.addCharge(managerCap/list.size());
            }

            event.getModBlock().setData(m);
            event.getModBlock().update();
        }

        private void findAndAdd(Location location, List<Location> list, List<Location> checked, int deep) {
            List<Location> n = new ArrayList<>();

            if(deep == 0) {
                return;
            }

            check(location.clone().add(1, 0, 0), n, checked);
            check(location.clone().add(-1, 0, 0), n, checked);
            check(location.clone().add(0, 0, 1), n, checked);
            check(location.clone().add(0, 0, -1), n, checked);

            list.addAll(n);

            for(Location l : n) {
                findAndAdd(l, list, checked, deep-1);
            }
        }

        private void check(Location location, List<Location> n, List<Location> checked) {
            if(checked.contains(location)) {
                return;
            }

            checked.add(location);

            BlockManager manager = NewMod.get().getBlockManager();
            ModBlockType type = manager.getType(location);

            if(type instanceof SolarLinkItemType.SolarLinkBlockType) {
                n.add(location);
            }
        }
    }
}
