package me.fly.newmod.metals;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemStack;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.metals.items.FilledOreSponge;
import me.fly.newmod.metals.items.MetalNugget;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static org.bukkit.Material.*;

public class MetalsAddonSetup {
    private static int bn;
    public static Map<Material, Map<ItemStack, BigDecimal>> oreMap = new LinkedHashMap<>();
    private static Map<ModItemType, Double> otherPercentages = new HashMap<>();

    //private static Map<Material, ModItemType> dusts = new HashMap<>();

    public static void init() {
        File file = new File("plugins\\NewMod\\config.yml");

        try {
            if (!file.exists()) {
                file.createNewFile();

                FileOutputStream outputStream = new FileOutputStream(file);

                InputStream inputStream = MetalsAddonSetup.class.getClassLoader().getResourceAsStream("config.yml");

                byte[] bytes = new byte[inputStream.available()];

                inputStream.read(bytes);

                outputStream.write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        bn = configuration.getInt("block-nuggets");

        ConfigurationSection ores = configuration.getConfigurationSection("ores");

        ItemManager manager = NewMod.get().getItemManager();

        for(String oreKey : ores.getKeys(false)) {
            ConfigurationSection oreData = ores.getConfigurationSection(oreKey);

            oreMap.put(Material.valueOf(oreKey), new HashMap<>());

            BigDecimal d = new BigDecimal("0");

            for(String metalKey : oreData.getKeys(false)) {
                if (metalKey.equals("ITEM")) {
                    BASE_FILLED_ORE_SPONGE.addVariant(Material.valueOf(oreKey), oreData.getString("NAME"));
                    continue;
                }

                if (metalKey.equals("NAME")) {
                    continue;
                }

                String metalData = oreData.getString(metalKey);

                //noinspection ConstantConditions
                BigDecimal bigMetalData = new BigDecimal(metalData);

                System.out.println(oreKey + ", " + metalKey + ", " + metalData);

                d = d.add(bigMetalData);

                if (!metalKey.contains(":")) {
                    oreMap.get(Material.valueOf(oreKey)).put(null, bigMetalData);
                } else {
                    String namespace = metalKey.split(":")[0];
                    String name = metalKey.split(":")[1];

                    if (namespace.equalsIgnoreCase("METALSMODULE")) {
                        ModItemType type = manager.getType(new NamespacedKey(MetalsPlugin.get(), name));

                        if(type == null) {
                            System.err.println("Error on init of " + oreKey + ", no such metal name " + name + " (" + metalKey + ")");
                        }

                        oreMap.get(Material.valueOf(oreKey)).put(new ModItemStack(type).create(), bigMetalData);
                    } else if (namespace.equalsIgnoreCase("MINECRAFT")) {
                        try {
                            oreMap.get(Material.valueOf(oreKey)).put(new ItemStack(Material.valueOf(name)), bigMetalData);
                        } catch (Exception e) {
                            System.err.println("Error on init of " + oreKey + ", exception " + e.getMessage() + " (" + metalKey + ")");
                        }
                    } else {
                        System.err.println("Error on init of " + oreKey + ", no such namespace " + namespace + " (" + metalKey + ")");
                    }
                }
            }

            if(!d.equals(new BigDecimal("1"))) {
                System.err.println("Error on init of " + oreKey + ", values do not equal 1, equals " + d);
            }
        }
    }

    public static ItemStack refine(Material material) {
        Random random = new Random();

        double r = random.nextDouble();

        for (ItemStack item : oreMap.get(material).keySet()) {
            double probability = oreMap.get(material).get(item).doubleValue();

            if (r < probability) {

                return item == null ? new ModItemStack(SILICON_NUGGET).create() : item;
            } else {
                r -= probability;
            }
        }

        return new ModItemStack(SILICON_NUGGET).create();
    }

    //TODO: translation and i18n

    public static final ModItemType ZINC_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "zinc_ingot", "Zinc Ingot", 0x6F857E);
    public static final ModItemType ALUMINUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "aluminum_ingot", "Aluminum Ingot", 0x8495B8);
    public static final ModItemType TITANIUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "titanium_ingot", "Titanium Ingot", 0xFAFAFA);
    public static final ModItemType NEODYMIUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "neodymium_ingot", "Neodymium Ingot", 0xC2CBFF);
    public static final ModItemType EUROPIUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "europium_ingot", "Europium Ingot", 0xFFC2C2);
    public static final ModItemType SODIUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "sodium_ingot", "Sodium Ingot", 0xFFFDEB);
    public static final ModItemType ZIRCONIUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "zirconium_ingot", "Zirconium Ingot", 0xF4EDFF);
    public static final ModItemType POTASSIUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "potassium_ingot", "Potassium Ingot", 0xFFF5ED);
    public static final ModItemType LITHIUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "lithium_ingot", "Lithium Ingot", 0xE8F2E6);
    public static final ModItemType LEAD_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "lead_ingot", "Lead Ingot", 0x282D3B);
    public static final ModItemType MAGNESIUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "magnesium_ingot", "Magnesium Ingot", 0xE8E289);
    public static final ModItemType OSMIUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "osmium_ingot", "Osmium Ingot", 0x1A1F26);

    //public static final ModItemType REDSTONE_INGOT = new ModItemType(NETHER_BRICK, "Redstone Ingot", "redstone_ingot");
    public static final ModItemType SILICON_INGOT = ModItemType.createAndRegister(IRON_INGOT,MetalsPlugin.get(), "silicon_ingot", "Silicon Ingot", 0xEBFFFE);

    public static final MetalNugget ZINC_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "zinc_nugget", "Zinc Nugget", 0x6F857E, ZINC_INGOT);
    public static final MetalNugget ALUMINUM_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "aluminum_nugget", "Aluminum Nugget", 0x8495B8, ALUMINUM_INGOT);
    public static final MetalNugget TITANIUM_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "titanium_nugget", "Titanium Nugget", 0xFAFAFA, TITANIUM_INGOT);
    public static final MetalNugget NEODYMIUM_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "neodymium_nugget", "Neodymium Nugget", 0xC2CBFF, NEODYMIUM_INGOT);
    public static final MetalNugget EUROPIUM_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "europium_nugget", "Europium Nugget", 0xFFC2C2, EUROPIUM_INGOT);
    public static final MetalNugget SODIUM_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "sodium_nugget", "Sodium Nugget", 0xFFFDEB, SODIUM_INGOT);
    public static final MetalNugget ZIRCONIUM_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "zirconium_nugget", "Zirconium Nugget", 0xF4EDFF, ZIRCONIUM_INGOT);
    public static final MetalNugget POTASSIUM_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "potassium_nugget", "Potassium Nugget", 0xFFF5ED, POTASSIUM_INGOT);
    public static final MetalNugget LITHIUM_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "lithium_nugget", "Lithium Nugget", 0xE8F2E6, LITHIUM_INGOT);
    public static final MetalNugget LEAD_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "lead_nugget", "Lead Nugget", 0x282D3B, LEAD_INGOT);
    public static final MetalNugget MAGNESIUM_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "magnesium_nugget", "Magnesium Nugget", 0xE8E289, MAGNESIUM_INGOT);
    public static final MetalNugget OSMIUM_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "osmium_nugget", "Osmium Nugget", 0x1A1F26, OSMIUM_INGOT);

    public static final MetalNugget SILICON_NUGGET = MetalNugget.createAndRegister(IRON_NUGGET, MetalsPlugin.get(), "silicon_nugget", "Silicon Nugget", 0xEBFFFE, SILICON_INGOT);

    public static final ModItemType BORON_PIECE = MetalNugget.createAndRegister(FLINT, MetalsPlugin.get(), "boron_piece", "Boron Piece", 0x404050);

    public static final MetalNugget COPPER_NUGGET = MetalNugget.createAndRegister(GOLD_NUGGET, MetalsPlugin.get(), "copper_nugget", "Copper Nugget", 0xE8BF82, new ItemStack(COPPER_INGOT));

    public static final ModItemType SULFUR_POWDER = ModItemType.createAndRegister(GLOWSTONE_DUST, MetalsPlugin.get(), "sulfur_powder", "Sulfur Powder", 0xFFF200);
    public static final ModItemType CARBON_POWDER = ModItemType.createAndRegister(GUNPOWDER, MetalsPlugin.get(), "carbon_powder", "Carbon Powder", 0x202020);
    public static final ModItemType PHOSPHORUS_POWDER = ModItemType.createAndRegister(REDSTONE, MetalsPlugin.get(), "phosphorus_powder", "Phosphorus Powder", 0xFF8A7D);

    public static final ModItemType URANIUM_NUGGET = ModItemType.createAndRegister(SCUTE, MetalsPlugin.get(), "uranium_nugget", "Uranium Nugget", 0x00FF00);
    public static final ModItemType PLUTONIUM_NUGGET = ModItemType.createAndRegister(SCUTE, MetalsPlugin.get(), "plutonium_nugget", "Plutonium Nugget", 0x00FF00);

    public static final ModItemType MAGNET = ModItemType.createAndRegister(STONE_BUTTON, MetalsPlugin.get(), "magnet", "Magnet", 0x00EAFF);

    public static final ModItemType HARD_CARBON_CHUNK = ModItemType.createAndRegister(FLINT, MetalsPlugin.get(), "hard_carbon_chunk", "Hard Carbon Chunk", 0x202020);

    public static final ModItemType ORE_SPONGE = ModItemType.createAndRegister(GLASS, MetalsPlugin.get(), "ore_sponge", "Ore Sponge", 0xFFFEBF)
            .shapelessRecipe(24, new ItemStack(COAL_BLOCK), new ItemStack(CLAY), new ItemStack(GLASS));
    public static final FilledOreSponge BASE_FILLED_ORE_SPONGE = new FilledOreSponge();

    public static final ModItemType SALT = ModItemType.createAndRegister(SUGAR, MetalsPlugin.get(), "salt", "Salt", 0xCCCCCC)
            .shapelessRecipe(2, new ModItemStack(SODIUM_NUGGET).create(), new ModItemStack(SODIUM_NUGGET).create(), new ModItemStack(SODIUM_NUGGET).create(), new ItemStack(SUGAR));


    public static final ModItemType BORRO_NEODYMIUM_POWDER = ModItemType.createAndRegister(GUNPOWDER, MetalsPlugin.get(), "borro_neodymium_powder", "Borro-Neodymium Powder", 0xE4E2CF)
            .shapelessRecipe(7, TITANIUM_INGOT.create(), BORON_PIECE.create(), BORON_PIECE.create(), NEODYMIUM_INGOT.create(), SILICON_INGOT.create(), SILICON_INGOT.create(), SILICON_INGOT.create());

    public static final ModItemType BORRO_NEODYMIUM_INGOT = ModItemType.createAndRegister(IRON_INGOT, MetalsPlugin.get(), "borro_neodymium_ingot", "Borro-Neodymium Ingot", 0xE4E2CF)
            .furnaceRecipe(BORRO_NEODYMIUM_POWDER.create(), 2, 90)
            .blastingRecipe(BORRO_NEODYMIUM_POWDER.create(), 3, 45);
}
