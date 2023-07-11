package me.fly.newmod.metals;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ItemManager;
import me.fly.newmod.api.item.ModItemStack;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.builders.ModItemTypeBuilder;
import me.fly.newmod.metals.items.MetalNuggetItemType;
import me.fly.newmod.metals.items.MetalNuggetItemTypeBuilder;
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

public class MetalsModuleTypes {
    private static int bn;
    public static Map<Material, Map<ItemStack, BigDecimal>> oreMap = new LinkedHashMap<>();
    private static Map<ModItemType, Double> otherPercentages = new HashMap<>();

    //private static Map<Material, ModItemType> dusts = new HashMap<>();

    public static void init() {
        /*File file = new File("plugins\\NewMod\\config.yml");

        try {
            if (!file.exists()) {
                file.createNewFile();

                FileOutputStream outputStream = new FileOutputStream(file);

                InputStream inputStream = MetalsModuleTypes.class.getClassLoader().getResourceAsStream("config.yml");

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
                    //BASE_FILLED_ORE_SPONGE.addVariant(Material.valueOf(oreKey), oreData.getString("NAME"));
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
                        //TODO:
                        ModItemType type = manager.getType(new NamespacedKey("", name));

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
        }*/
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

    public static final ModItemType ZINC_INGOT = new ModItemTypeBuilder(IRON_INGOT, "zinc_ingot", MetalsPlugin.get()).customName("Zinc Ingot", 0x6F857E).buildAndRegister();
    public static final ModItemType ALUMINUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "aluminum_ingot",MetalsPlugin.get()).customName("Aluminum Ingot", 0x8495B8).buildAndRegister();
    public static final ModItemType TITANIUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "titanium_ingot", MetalsPlugin.get()).customName("Titanium Ingot", 0xFAFAFA).buildAndRegister();
    public static final ModItemType NEODYMIUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "neodymium_ingot", MetalsPlugin.get()).customName("Neodymium Ingot", 0xC2CBFF).buildAndRegister();
    public static final ModItemType EUROPIUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "europium_ingot", MetalsPlugin.get()).customName("Europium Ingot", 0xFFC2C2).buildAndRegister();
    public static final ModItemType SODIUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "sodium_ingot", MetalsPlugin.get()).customName("Sodium Ingot", 0xFFFDEB).buildAndRegister();
    public static final ModItemType ZIRCONIUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "zirconium_ingot", MetalsPlugin.get()).customName("Zirconium Ingot", 0xF4EDFF).buildAndRegister();
    public static final ModItemType POTASSIUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "potassium_ingot", MetalsPlugin.get()).customName("Potassium Ingot", 0xFFF5ED).buildAndRegister();
    public static final ModItemType LITHIUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "lithium_ingot", MetalsPlugin.get()).customName("Lithium Ingot", 0xE8F2E6).buildAndRegister();
    public static final ModItemType LEAD_INGOT = new ModItemTypeBuilder(IRON_INGOT, "lead_ingot", MetalsPlugin.get()).customName("Lead Ingot", 0x282D3B).buildAndRegister();
    public static final ModItemType MAGNESIUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "magnesium_ingot", MetalsPlugin.get()).customName("Magnesium Ingot", 0xE8E289).buildAndRegister();
    public static final ModItemType OSMIUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "osmium_ingot", MetalsPlugin.get()).customName("Osmium Ingot", 0x1A1F26).buildAndRegister();

    //public static final ModItemType REDSTONE_INGOT = new ModItemType(NETHER_BRICK, "Redstone Ingot", "redstone_ingot");
    public static final ModItemType SILICON_INGOT = new ModItemTypeBuilder(IRON_INGOT, "silicon_ingot",  MetalsPlugin.get()).customName("Silicon Ingot", 0xEBFFFE).buildAndRegister();

    public static final MetalNuggetItemType ZINC_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "zinc_nugget", MetalsPlugin.get(), ZINC_INGOT).customName("Zinc Nugget", 0x6F857E).buildAndRegister();
    public static final MetalNuggetItemType ALUMINUM_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "aluminum_nugget", MetalsPlugin.get(), ALUMINUM_INGOT).customName("Aluminum Nugget", 0x8495B8).buildAndRegister();
    public static final MetalNuggetItemType TITANIUM_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "titanium_nugget", MetalsPlugin.get(), TITANIUM_INGOT).customName("Titanium Nugget", 0xFAFAFA).buildAndRegister();
    public static final MetalNuggetItemType NEODYMIUM_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "neodymium_nugget", MetalsPlugin.get(), NEODYMIUM_INGOT).customName("Neodymium Nugget", 0xC2CBFF).buildAndRegister();
    public static final MetalNuggetItemType EUROPIUM_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "europium_nugget", MetalsPlugin.get(), EUROPIUM_INGOT).customName("Europium Nugget", 0xFFC2C2).buildAndRegister();
    public static final MetalNuggetItemType SODIUM_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "sodium_nugget", MetalsPlugin.get(), SODIUM_INGOT).customName("Sodium Nugget", 0xFFFDEB).buildAndRegister();
    public static final MetalNuggetItemType ZIRCONIUM_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "zirconium_nugget", MetalsPlugin.get(), ZIRCONIUM_INGOT).customName("Zirconium Nugget", 0xF4EDFF).buildAndRegister();
    public static final MetalNuggetItemType POTASSIUM_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "potassium_nugget", MetalsPlugin.get(), POTASSIUM_INGOT).customName("Potassium Nugget", 0xFFF5ED).buildAndRegister();
    public static final MetalNuggetItemType LITHIUM_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "lithium_nugget", MetalsPlugin.get(), LITHIUM_INGOT).customName("Lithium Nugget", 0xE8F2E6).buildAndRegister();
    public static final MetalNuggetItemType LEAD_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "lead_nugget", MetalsPlugin.get(), LEAD_INGOT).customName("Lead Nugget", 0x282D3B).buildAndRegister();
    public static final MetalNuggetItemType MAGNESIUM_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "magnesium_nugget", MetalsPlugin.get(), MAGNESIUM_INGOT).customName("Magnesium Nugget", 0xE8E289).buildAndRegister();
    public static final MetalNuggetItemType OSMIUM_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "osmium_nugget", MetalsPlugin.get(), OSMIUM_INGOT).customName("Osmium Nugget", 0x1A1F26).buildAndRegister();

    public static final MetalNuggetItemType SILICON_NUGGET = new MetalNuggetItemTypeBuilder(IRON_NUGGET, "silicon_nugget", MetalsPlugin.get(), SILICON_INGOT).customName("Silicon Nugget", 0xEBFFFE).buildAndRegister();

    public static final ModItemType BORON_PIECE = new ModItemTypeBuilder(FLINT, "boron_piece", MetalsPlugin.get()).customName("Boron Piece", 0x404050).buildAndRegister();

    public static final MetalNuggetItemType COPPER_NUGGET = new MetalNuggetItemTypeBuilder(GOLD_NUGGET, "copper_nugget", MetalsPlugin.get(), COPPER_INGOT).customName("Copper Nugget", 0xE8BF82).buildAndRegister();

    public static final ModItemType SULFUR_POWDER = new ModItemTypeBuilder(GLOWSTONE_DUST, "sulfur_powder", MetalsPlugin.get()).customName("Sulfur Powder", 0xFFF200).buildAndRegister();
    public static final ModItemType CARBON_POWDER = new ModItemTypeBuilder(GUNPOWDER, "carbon_powder", MetalsPlugin.get()).customName( "Carbon Powder", 0x202020).buildAndRegister();
    public static final ModItemType PHOSPHORUS_POWDER = new ModItemTypeBuilder(REDSTONE, "phosphorus_powder", MetalsPlugin.get()).customName( "Phosphorus Powder", 0xFF8A7D).buildAndRegister();

    public static final ModItemType URANIUM_NUGGET = new ModItemTypeBuilder(SCUTE, "uranium_nugget", MetalsPlugin.get()).customName( "Uranium Nugget", 0x00FF00).buildAndRegister();
    public static final ModItemType PLUTONIUM_NUGGET = new ModItemTypeBuilder(SCUTE, "plutonium_nugget", MetalsPlugin.get()).customName( "Plutonium Nugget", 0x00FF00).buildAndRegister();

    public static final ModItemType MAGNET = new ModItemTypeBuilder(STONE_BUTTON, "magnet", MetalsPlugin.get()).customName( "Magnet", 0x00EAFF).buildAndRegister();

    public static final ModItemType HARD_CARBON_CHUNK = new ModItemTypeBuilder(FLINT, "hard_carbon_chunk", MetalsPlugin.get()).customName( "Hard Carbon Chunk", 0x202020).buildAndRegister();

    public static final ModItemType ORE_SPONGE = new ModItemTypeBuilder(GLASS, "ore_sponge", MetalsPlugin.get()).customName( "Ore Sponge", 0xFFFEBF).buildAndRegister()
            .shapelessRecipe(24, new ItemStack(COAL_BLOCK), new ItemStack(CLAY), new ItemStack(GLASS));
    //public static final FilledOreSponge BASE_FILLED_ORE_SPONGE = new FilledOreSponge();

    public static final ModItemType SALT = new ModItemTypeBuilder(SUGAR, "salt", MetalsPlugin.get()).customName( "Salt", 0xCCCCCC).buildAndRegister()
            .shapelessRecipe(2, new ModItemStack(SODIUM_NUGGET).create(), new ModItemStack(SODIUM_NUGGET).create(), new ModItemStack(SODIUM_NUGGET).create(), new ItemStack(SUGAR));


    public static final ModItemType BORRO_NEODYMIUM_POWDER = new ModItemTypeBuilder(GUNPOWDER, "borro_neodymium_powder", MetalsPlugin.get()).customName( "Borro-Neodymium Powder", 0xE4E2CF).buildAndRegister()
            .shapelessRecipe(7, TITANIUM_INGOT.create(), BORON_PIECE.create(), BORON_PIECE.create(), NEODYMIUM_INGOT.create(), SILICON_INGOT.create(), SILICON_INGOT.create(), SILICON_INGOT.create());

    public static final ModItemType BORRO_NEODYMIUM_INGOT = new ModItemTypeBuilder(IRON_INGOT, "borro_neodymium_ingot", MetalsPlugin.get()).customName( "Borro-Neodymium Ingot", 0xE4E2CF).buildAndRegister()
            .furnaceRecipe(BORRO_NEODYMIUM_POWDER.create(), 2, 90)
            .blastingRecipe(BORRO_NEODYMIUM_POWDER.create(), 3, 45);
}
