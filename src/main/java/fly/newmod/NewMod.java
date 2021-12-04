package fly.newmod;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import fly.newmod.impl.items.Camera;
import fly.newmod.impl.machines.DatingMachine;
import fly.newmod.impl.vehicles.Plane;
import fly.newmod.setup.BlockStorage;
import fly.newmod.setup.Setup;
import fly.newmod.utils.ColorUtils;
import fly.newmod.utils.GeometryUtils;
import fly.newmod.utils.PlayerIntMapWrapper;
import fly.newmod.utils.PlayerTimeDataType;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class NewMod extends JavaPlugin implements Listener {
    private static NewMod instance;
    private BlockStorage storage;
    private Random random = new Random();
    private List<ModExtension> extensions = new ArrayList<>();

    private File saveFile;

    private int count = 0;

    Plane plane = new Plane();
    Player player;

    public NewMod() {
        instance = this;

        storage = new BlockStorage();
    }

    @Override
    public void onEnable() {
        saveFile = new File("plugins\\NewMod\\save.yml");

        System.out.println(saveFile.getAbsolutePath());

        Setup.init();

        storage.init();

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(storage, this);

        for(ModExtension extension : extensions) {
            extension.load();
        }

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(saveFile);

        for(String location : configuration.getKeys(false)) {
            String[] split = location.split(",");

            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            int z = Integer.parseInt(split[2]);

            World world = Bukkit.getWorld(split[3]);

            ConfigurationSection section = configuration.getConfigurationSection(location);

            for(String key : section.getKeys(false)) {
                storage.changeData(new Location(world, x, y, z), key, section.getString(key));
            }
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::save, 500, 500);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Client.VEHICLE_MOVE, PacketType.Play.Client.BOAT_MOVE) {
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
        });

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for(Player player : Bukkit.getOnlinePlayers()) {
                ItemStack helmet = player.getInventory().getHelmet();
                ItemStack chestplate = player.getInventory().getChestplate();
                ItemStack leggings = player.getInventory().getLeggings();
                ItemStack boots = player.getInventory().getBoots();

                updateArmor(player, helmet);
                updateArmor(player, chestplate);
                updateArmor(player, leggings);
                updateArmor(player, boots);

                count++;
            }
        }, 200, 2);
    }

    @EventHandler
    public void pluginLoad(PluginEnableEvent event) {
        if(event.getPlugin().getName().contains("protocol")) {

        }
    }

    @Override
    public void onDisable() {
        save();
    }

    private void updateArmor(Player player, ItemStack stack) {
        if(stack == null) {
            return;
        }

        ItemMeta meta = stack.getItemMeta();

        if(count % 100 == 0) {
            PlayerIntMapWrapper wrapper = meta.getPersistentDataContainer().getOrDefault(DatingMachine.PLAYERS_NAMESPACE, PlayerTimeDataType.PLAYER_TIME_TYPE, new PlayerIntMapWrapper(new HashMap<>()));

            wrapper.add(player);

            meta.getPersistentDataContainer().set(DatingMachine.PLAYERS_NAMESPACE, PlayerTimeDataType.PLAYER_TIME_TYPE, wrapper);
        }

        stack.setItemMeta(meta);
    }

    private void save() {
        YamlConfiguration configuration = new YamlConfiguration();

        for(Location location : storage.getAllLocations()) {
            Map<String, String> section = new HashMap<>();

            for(String key : storage.getData(location)) {
                section.put(key, storage.getData(location, key));
            }

            configuration.set(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "," + location.getWorld().getName(), section);
        }

        try {
            configuration.save(saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            if (args.length == 5) {
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
            }

            if (args.length == 4) {
                Location location = ((Player) sender).getLocation();
                //Vector pos = GeometryUtils.getRelative(location, new Vector(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));

                //sender.sendMessage(pos.getX() + "");
                //sender.sendMessage(pos.getY() + "");
                //sender.sendMessage(pos.getZ() + "");

                //new Location(((Player) sender).getWorld(), pos.getX(), pos.getY(), pos.getZ()).getBlock().setType(Material.NETHERRACK);
            }
            if (args.length == 3) {
                explode(Integer.parseInt(args[0]), Integer.parseInt(args[1]), ((Player) sender).getLocation());
            }
            if (args.length == 2) {
                this.player = (Player) sender;

                plane.takeOffSpeed = Float.parseFloat(args[0]);
                plane.speed = 5;
            } else {
                if(args[0].equalsIgnoreCase("data")) {
                    Location location = new Location(((Player) sender).getWorld(), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

                    sender.sendMessage(ChatColor.YELLOW + storage.getData(location).toString());

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
                ArrayList<ItemStack> list = new ArrayList<>(storage.getItems().values());
                Inventory inventory = Bukkit.createInventory((Player) sender, 54, ChatColor.RED + "FlyFun Menu " + (page + 1));

                for (int x = 0; x < 54; x++) {
                    int index = x + (page * 54);

                    if (index < list.size()) {
                        inventory.addItem(list.get(index));
                    }
                }

                ((Player) sender).openInventory(inventory);
            }
        }

        return true;
    }

    private void explode(int force, int size, Location location) {
        float[][][] array = new float[size * 4][][];

        for (int x = 0; x < size * 4; x++) {
            float[][] xa = new float[size * 4][];

            for (int y = 0; y < size * 4; y++) {
                float[] za = new float[size * 4];

                for (int i = 0; i < size * 4; i++) {
                    za[i] = -1;
                }

                xa[y] = za;
            }

            array[x] = xa;
        }

        new Thread(() -> {

            Set<Block> blocks = new HashSet<>(size * size * size);
            Map<Entity, Float> entities = new HashMap<>(size * size * size);
            long time = System.currentTimeMillis();

            for (float xi = 0; xi <= 360.0; xi += 1.0) {
                for (float yi = 0; yi <= 360.0; yi += 1.0) {
                    long time2 = System.currentTimeMillis();
                    float x = (float) Math.toRadians(xi);
                    float y = (float) Math.toRadians(yi);

                    Location current = location.clone();

                    Vector rtv = new Vector(Math.cos(x) * Math.cos(y),
                            Math.sin(y), Math.sin(x) * Math.cos(y)).normalize();

                    for (int rev = 0; rev < size; rev++) {
                        float damage = (1-((float) rev)/size)*((float) Math.sqrt(force)*3);

                        Block b = current.getBlock();
                        float br;

                        int xa = current.getBlockX() - location.getBlockX() + size * 2;
                        int ya = current.getBlockY() - location.getBlockY() + size * 2;
                        int za = current.getBlockZ() - location.getBlockZ() + size * 2;

                        if (array[xa][ya][za] == -1) {
                            array[xa][ya][za] = b.getType().getBlastResistance();
                        }

                        br = array[xa][ya][za];

                        if (force < br) {
                            break;
                        } else {
                            blocks.add(b);
                            //System.out.println("added");
                        }

                        current.add(rtv.getX() / c(4 * (br / force)), rtv.getY() / c(4 * (br / force)), rtv.getZ() / c(4 * (br / force)));
                    }

                    //System.out.println(System.currentTimeMillis()-time2);
                }
            }

            Bukkit.getScheduler().runTaskLater(this, () -> {
                for (Block b : blocks) {
                    if(b.getType() != Material.AIR) {
                        CoreProtect.getInstance().getAPI().logRemoval("#nmexplosion", b.getLocation(), b.getType(), ((byte) 0));
                    }
                    if (random.nextBoolean()) {
                        b.breakNaturally();
                    } else {
                        b.setType(Material.AIR);
                    }
                }
                for (Block b : blocks) {
                    double damage = (size*force)/location.distance(b.getLocation());

                    for(Entity entity : location.getWorld().getNearbyEntities(b.getLocation(), 1.5, 1.5, 1.5)) {
                        if(!entities.containsKey(entity)) {
                            if (entity instanceof LivingEntity) {
                                ((LivingEntity) entity).damage(damage);
                            } else if (damage > 10) {
                                entity.remove();
                            }
                        } else {
                            entities.put(entity, 0f);
                        }
                    }
                    //blocks.remove(b);
                }
            }, 1);
        }).start();
    }

    private double c(double a) {
        if(random.nextInt(16) == 1) {
            return 0.75;
        }
        return Math.min(Math.max(a, 1.5), 8)+random.nextDouble()/4;
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

    public BlockStorage getBlockStorage() {
        return storage;
    }

    public static abstract class ModExtension extends JavaPlugin {
        public ModExtension() {
            instance.extensions.add(this);
        }

        public abstract void load();
    }
}
