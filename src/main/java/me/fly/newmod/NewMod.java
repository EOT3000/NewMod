package me.fly.newmod;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import me.fly.newmod.armor.listener.DamageListener;
import me.fly.newmod.crafting.CraftingChangesManager;
import me.fly.newmod.api.block.BlockManager;
import me.fly.newmod.api.block.data.DefaultModBlockData;
import me.fly.newmod.api.item.ModItemStack;
import me.fly.newmod.api.item.meta.DefaultModItemMeta;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.listener.BlockListener;
import me.fly.newmod.listener.CraftingListener;
import me.fly.newmod.listener.HornListener;
import me.fly.newmod.listener.VanillaReplacementListener;
import me.fly.newmod.save.DataSaver;
import me.fly.newmod.time.TimeManager;
import me.fly.newmod.api.util.ColorUtils;
import me.fly.newmod.time.TimeUtils;
import me.fly.newmod.time.TimeValues;
import me.fly.newmod.time.nms.world.TrickWorlds;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public class NewMod extends JavaPlugin implements Listener {
    private static NewMod instance;
    private BlockManager blockManager;
    private ItemManager itemManager;
    private CraftingChangesManager craftingChangesManager;
    private Random random = new Random();
    private final List<ModExtension> extensions = new ArrayList<>();
    private TimeManager timeManager;

    private File saveDir;

    Player player;

    public NewMod() {
        instance = this;

        blockManager = new BlockManager();
        itemManager = new ItemManager();
        craftingChangesManager = new CraftingChangesManager();
    }

    @Override
    public void onLoad() {
        //CustomBeeHiveTicker.
    }

    @Override
    public void onEnable() {
        getLogger().info("[NewMod] THIS PLUGIN USES COPYRIGHTED MATERIAL");
        getLogger().info(ColorUtils.disclaimer);
        getLogger().info("[NewMod] NewMod is not affiliated with The University of Southampton, nor with the authors of any used code");

        saveDir = new File("plugins\\NewMod\\save");

        saveDir.mkdirs();

        //System.out.println(saveFile.getAbsolutePath());

        blockManager.init();

        new DefaultModItemMeta.DefaultModItemMetaSerializer();
        new DefaultModBlockData.DefaultModBlockDataSerializer();

        //timeManager = new TimeManager();

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new VanillaReplacementListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        //Bukkit.getPluginManager().registerEvents(timeManager, this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new CraftingListener(), this);
        Bukkit.getPluginManager().registerEvents(new HornListener(), this);

        List<ModExtension> toLoad = new ArrayList<>(extensions);

        for (ModExtension extension : toLoad) {
            attemptLoad(extension);
        }

        Bukkit.getScheduler().runTaskLater(this, () -> {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(saveDir);

            for (String location : configuration.getKeys(false)) {
                String[] split = location.split(",");

                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                int z = Integer.parseInt(split[2]);

                World world = Bukkit.getWorld(split[3]);

                ConfigurationSection section = configuration.getConfigurationSection(location);

                for (String key : section.getKeys(false)) {
                    blockManager.changeData(new Location(world, x, y, z), key, section.getString(key));
                }
            }
        }, 1);

        DataSaver.load(blockManager, saveDir);

        //TODO: config save time
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> DataSaver.save(blockManager), 200, 200);

        /*ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Client.VEHICLE_MOVE, PacketType.Play.Client.BOAT_MOVE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                try {
                    if (event.getPacket().getType().equals(PacketType.Play.Client.VEHICLE_MOVE)) {
                        event.setCancelled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

        TrickWorlds.init();
    }

    private void attemptLoad(ModExtension extension) {
        if(extension.loaded()) {
            return;
        }

        getLogger().log(Level.INFO, "[NewMod] Attempting to load: " + extension.getName());

        List<ModExtension> requirements = extension.requirements();

        for(ModExtension requirement : requirements) {
            attemptLoad(requirement);

            if(requirement.errored) {
                getLogger().log(Level.SEVERE, "Plugin: " + extension.getName() + " failed to load (plugin " + requirement.getName() + " errored)");
                return;
            }
        }

        try {
            extension.load();

            extension.loaded = true;
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Plugin: " + extension.getName() + " failed to load");

            e.printStackTrace();

            extension.errored = true;
        }
    }

    private void tick(int ticks) {
        //todo add
    }

    @Override
    public void onDisable() {
        DataSaver.saveFinally(blockManager);
    }


    @EventHandler
    public void onTick(ServerTickStartEvent event) {
        tick(event.getTickNumber());

        for(ModExtension extension : extensions) {
            extension.tick(event.getTickNumber());
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        DataSaver.loadWorld(blockManager, event.getWorld());
    }

    /*@EventHandler
    public void onTick(ServerTickStartEvent event) {
        if(player == null) {
            return;
        }

        if(event.getTickNumber() % 120 == 0) {
            player.sendMessage(ChatColor.GREEN + "Speed: " + plane.speed);
        }


        if(event.getTickNumber() % 2 != 0) {
            return;
        }

        if(player.getInventory().getHeldItemSlot() == 0) {
            plane.speed+=0.1;
        }

        if(player.getInventory().getHeldItemSlot() == 1) {
            plane.speed-=0.1;
        }

        /*if(player.getInventory().getHeldItemSlot() == 39) {
            plane.yaw-=3;
        }

        if(player.getInventory().getHeldItemSlot() == 40) {
            plane.yaw+=3;
        }*/

        /*Vehicle vehicle = (Vehicle) player.getVehicle();

        if (vehicle == null) {
            return;
        }

        double x = -Math.sin(Math.toRadians(vehicle.getLocation().getYaw()));
        double z = Math.cos(Math.toRadians(vehicle.getLocation().getYaw()));

        Vector v = new Vector(x, 0, z);

        v.normalize();

        double yc = 0;

        if(Math.abs(plane.speed) >= plane.takeOffSpeed) {
            if(player.getEyeLocation().getPitch() < 0) {
                yc = Math.min(Math.max(0, -player.getEyeLocation().getPitch()/5), 3)/10;
            }
        }

        if(player.getEyeLocation().getPitch() > 0) {
            yc = Math.min(Math.max(-3, -player.getEyeLocation().getPitch()/5), 0)/10;
        }

        if(plane.speed < plane.takeOffSpeed/2) {
            yc = plane.fallSpeed-=0.05;
        } else {
            plane.fallSpeed=0;
        }

        plane.speed = (float) Math.max(0, Math.min(50, plane.speed-0.05));

        vehicle.setVelocity(new Vector(v.getX()*(plane.speed/10.0), yc, v.getZ()*(plane.speed/10.0)));

        if(plane.lastYaw == -1) {
            plane.lastYaw = vehicle.getLocation().getYaw();
        }

        Location loc = player.getVehicle().getLocation();

        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.VEHICLE_MOVE);

        packet.getDoubles().write(0, loc.getX());
        packet.getDoubles().write(1, loc.getY());
        packet.getDoubles().write(2, loc.getZ());

        packet.getFloat().write(0, plane.lastYaw);
        packet.getFloat().write(1, loc.getPitch());

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*Location newLoc = vehicle.getLocation().clone().add(v.getX() * (plane.speed / 10), yc, z * (plane.speed / 10));

        float oldYaw = player.getLocation().getYaw();
        float oldPitch = player.getLocation().getPitch();

        newLoc.setPitch(0);
        newLoc.setYaw(plane.yaw);

        vehicle.teleport(newLoc);*/
    //}

    /*@EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        if(event.getView().getTitle().startsWith(ChatColor.RED + "FlyFun Menu")) {
            event.setCancelled(true);

            //event.getWhoClicked().openInventory(inventory);

            if(event.getCurrentItem() != null) {
                event.getWhoClicked().getInventory().addItem(event.getCurrentItem());
            }
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if(player.equals(event.getPlayer()) && event.getReason().contains("lying")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCollision(VehicleBlockCollisionEvent event) {
        if(plane.speed > 5) {
            if(event.getVehicle().getLocation().getY() > event.getBlock().getLocation().getBlockY()) {
                return;
            }

            //event.getBlock().getWorld().createExplosion(event.getVehicle().getLocation(), (plane.speed+plane.fallSpeed)/9);
        }
    }*/


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Vector vector = ((Player) sender).getLocation().getDirection();

        if(sender instanceof ConsoleCommandSender) {
            System.out.println(System.currentTimeMillis() + " : " + System.currentTimeMillis()/25000000);
        }

        if(sender.isOp()) {
            if(args.length == 1 && args[0].equalsIgnoreCase("time")) {
                Player player = (Player) sender;

                int b = timeManager.getSkyBrightness(player.getLocation());

                player.sendMessage("Brightness: " + b);
                player.sendMessage("Sun Position: " + timeManager.coordinate);
                player.sendMessage("Morning time: " + TimeUtils.timeMorning(b));
                player.sendMessage("Night time: " + TimeUtils.timeNight(b));
                player.sendMessage("Morning? " + timeManager.morning(player.getLocation()));
                player.sendMessage("-");

                return true;
            }

            //TODO: remove cb stuff from here
            if(args.length == 2 && args[0].equalsIgnoreCase("time") && args[1].equalsIgnoreCase("world")) {
                sender.sendMessage("day in NIGHT? " + TrickWorlds.NIGHT.getHandle().M());
                sender.sendMessage("night in NIGHT? " + TrickWorlds.NIGHT.getHandle().N());

                sender.sendMessage("");


                sender.sendMessage("day in DAY? " + TrickWorlds.DAY.getHandle().M());
                sender.sendMessage("night in DAY? " + TrickWorlds.DAY.getHandle().N());

                sender.sendMessage("");

                sender.sendMessage("day in NONE? " + TrickWorlds.NONE.getHandle().M());
                sender.sendMessage("night in NONE? " + TrickWorlds.NONE.getHandle().N());

                sender.sendMessage("");

                sender.sendMessage("time in NIGHT: " + TrickWorlds.NIGHT.getTime());
                sender.sendMessage("time in DAY: " + TrickWorlds.DAY.getTime());
                sender.sendMessage("time in NONE: " + TrickWorlds.NONE.getTime());

                player.sendMessage("---");

                return true;
            } else if(args.length == 2 && args[0].equalsIgnoreCase("time")) {
                timeManager.coordinate = Double.parseDouble(args[1]);

                return true;
            }

            if(args.length == 2 && args[0].equalsIgnoreCase("speed")) {
                TimeValues.INCREMENT = Double.parseDouble(args[1]);

                return true;
            }

            /*if (args.length == 5) {
                Location location = new Location(((Player) sender).getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                Chest chest = ((Chest) location.getBlock().getState());
                Inventory inventory = ((Player) sender).getInventory();

                for(int x = 0; x < 54; x++) {
                    double r = random.nextDouble();

                    for(ItemStack stack : getBlockStorage().oreMap.get(((Player) sender).getItemInHand().getType()).keySet()) {
                        double probability = getBlockStorage().oreMap.get(((Player) sender).getItemInHand().getType()).get(stack);

                        if(r < probability) {
                            if(stack == null) {
                                inventory.addItem(Setup.SILICON_NUGGET);
                            } else {
                                inventory.addItem(stack);
                            }

                            System.out.println(stack);

                            System.out.println("a");

                            break;
                        } else {
                            r-=probability;
                        }
                    }
                }

                chest.update();
            }*/

            if (args.length == 4) {
                Location location = ((Player) sender).getLocation();
                //Vector pos = GeometryUtils.getRelative(location, new Vector(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));

                //sender.sendMessage(pos.getX() + "");
                //sender.sendMessage(pos.getY() + "");
                //sender.sendMessage(pos.getZ() + "");

                //new Location(((Player) sender).getWorld(), pos.getX(), pos.getY(), pos.getZ()).getBlock().setType(Material.NETHERRACK);
            }
            if (args.length == 3) {
                //explode(Integer.parseInt(args[0]), Integer.parseInt(args[1]), ((Player) sender).getLocation());
            }
            if (args.length == 2) {
                this.player = (Player) sender;

                //plane.takeOffSpeed = Float.parseFloat(args[0]);
                //plane.speed = 5;
            } else {
                if(args[0].equalsIgnoreCase("data")) {
                    Location location = new Location(((Player) sender).getWorld(), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

                    for(String key : blockManager.getAllData(location)) {
                        sender.sendMessage(ChatColor.YELLOW + key + " : " + blockManager.getData(location, key));
                    }

                    if(args.length == 6) {
                        blockManager.changeData(location, args[4], args[5]);
                    }

                    return true;
                }
                if(args[0].equalsIgnoreCase("map")) {
                    Player player = (Player) sender;
                    Location eyeLocation = player.getEyeLocation();

                    Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
                        //ItemStack stack = Camera.newMap(eyeLocation);

                        //Bukkit.getScheduler().runTaskLater(this, () -> {
                        //    player.getInventory().addItem(stack);
                        //}, 1);
                    });

                    return true;
                }

                int page = Integer.parseInt(args[0]) - 1;
                ArrayList<ModItemType> list = new ArrayList<>(itemManager.getItems());
                Inventory inventory = Bukkit.createInventory((Player) sender, 54, ChatColor.RED + "FlyFun Menu " + (page + 1));

                for (int x = 0; x < 54; x++) {
                    int index = x + (page * 54);

                    if (index < list.size()) {
                        inventory.addItem(new ModItemStack(list.get(index)).create());
                    }
                }

                ((Player) sender).openInventory(inventory);
            }
        }

        return true;
    }

    /*private void processN(Location to, Location from) {
        int xt = to.getBlockX();
        int zt = to.getBlockZ();

        int xf = from.getBlockX();
        int zf = from.getBlockZ();

        if(zt > zf) {
            plane.speed += 1;
            return;
        }
        if(zt < zf) {
            plane.speed -=1;
            return;
        }
        if(xt > xf) {
            plane.yaw += 5;
            return;
        }
        if(xt < xf) {
            plane.yaw -= 5;
        }
    }

    private void processS(Location to, Location from) {
        double xt = to.getX();
        double zt = to.getZ();

        double xf = from.getX();
        double zf = from.getZ();

        if (zt < zf) {
            plane.speed += 1;
            return;
        }
        if (zt > zf) {
            plane.speed -= 1;
            return;
        }
        if (xt < xf) {
            plane.yaw += 5;
            return;
        }
        if (xt > xf) {
            plane.yaw -= 5;
        }
    }

    private void processE(Location to, Location from) {
        int xt = to.getBlockX();
        int zt = to.getBlockZ();

        int xf = from.getBlockX();
        int zf = from.getBlockZ();

        if (xt > xf) {
            plane.speed += 1;
            return;
        }
        if (xt < xf) {
            plane.speed -= 1;
            return;
        }
        if (zt > zf) {
            plane.yaw += 5;
            return;
        }
        if (zt < zf) {
            plane.yaw -= 5;
        }
    }

    private void processW(Location to, Location from) {
        int xt = to.getBlockX();
        int zt = to.getBlockZ();

        int xf = from.getBlockX();
        int zf = from.getBlockZ();

        if (xt < xf) {
            plane.speed += 1;
            return;
        }
        if (xt > xf) {
            plane.speed -= 1;
            return;
        }
        if (zt < zf) {
            plane.yaw += 5;
            return;
        }
        if (zt > zf) {
            plane.yaw -= 5;
        }
    }*/

    public static NewMod get() {
        return instance;
    }

    public List<ModExtension> getExtensions() {
        return new ArrayList<>(extensions);
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }

    public CraftingChangesManager getCraftingChangesManager() {
        return craftingChangesManager;
    }

    public static abstract class ModExtension extends JavaPlugin {
        private boolean loaded;
        private boolean errored;

        private Map<String, ModItemType> moduleItems = new HashMap<>();

        public ModExtension() {
            new RuntimeException().printStackTrace();

            instance.extensions.add(this);
        }

        public abstract void load();

        public final boolean loaded() {
            return loaded;
        }

        public final boolean errored() {
            return errored;
        }

        public void tick(int count) {

        }

        public void registerItem(String string, ModItemType item) {
            moduleItems.put(string, item);

            NewMod.get().getItemManager().registerItem(item);
        }

        public Map<String, ModItemType> getItems() {
            return new HashMap<>(moduleItems);
        }

        public List<ModExtension> requirements() {
            return new ArrayList<>();
        }
    }
}
