package fly.newmod.armor;

import fly.newmod.parent.NewMod;
import fly.newmod.armor.listener.DamageListener;
import fly.newmod.armor.type.armor.ArmorSection;
import fly.newmod.armor.util.DamageChecker;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArmorPlugin extends NewMod.ModExtension implements Listener {
    private static ArmorPlugin INSTANCE;

    public ArmorPlugin() {
        INSTANCE = this;
    }

    public static ArmorPlugin get() {
        return INSTANCE;
    }


    @Override
    public void load() {

    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
    }

    @Override
    public List<NewMod.ModExtension> requirements() {
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for(ArmorSection section : DamageChecker.affectsLava((Player) sender)) {
            sender.sendMessage(section.name());
        }

        return true;
    }
}
