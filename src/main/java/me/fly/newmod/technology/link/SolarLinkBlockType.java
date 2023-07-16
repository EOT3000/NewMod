package me.fly.newmod.technology.link;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.block.BlockEventsListener;
import me.fly.newmod.api.block.BlockManager;
import me.fly.newmod.api.block.ModBlock;
import me.fly.newmod.api.block.ModBlockType;
import me.fly.newmod.api.events.block.ModBlockTickEvent;
import me.fly.newmod.api.item.ItemEventsListener;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.technology.EnergyComponent;
import me.fly.newmod.technology.TechnologyPlugin;
import me.fly.newmod.technology.data.EnergyHolderBlockData;
import me.fly.newmod.technology.data.EnergyHolderBlockDataImpl;
import me.fly.newmod.technology.producer.SolarGeneratorBlockType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class SolarLinkBlockType extends ModBlockType implements EnergyComponent {
    private final int capacity;

    public SolarLinkBlockType(Material defaultMaterial, int capacity, JavaPlugin plugin, String id) {
        super(defaultMaterial, new NamespacedKey(plugin, id), EnergyHolderBlockDataImpl.class);

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
        return EnergyComponentType.PULLER;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    private void tick(ModBlockTickEvent event) {
        Location location = event.getBlock().getLocation().clone().add(0, 1, 0);
        BlockManager bm = NewMod.get().getBlockManager();

        if (bm.getType(location) instanceof SolarGeneratorBlockType) {
            ModBlock p = new ModBlock(location);

            EnergyHolderBlockData holder = (EnergyHolderBlockData) event.getModBlock().getData();
            EnergyHolderBlockData panel = (EnergyHolderBlockData) p.getData();

            panel.transferTo(holder);

            event.getModBlock().setData(holder);
            event.getModBlock().update();

            p.setData(panel);
            p.update();
        }
    }
}
