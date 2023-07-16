package me.fly.newmod.technology;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.builders.ModItemTypeBuilder;
import me.fly.newmod.metals.MetalsModuleTypes;
import me.fly.newmod.technology.consumer.PressureChamberBlockType;
import me.fly.newmod.technology.consumer.PressureChamberItemType;
import me.fly.newmod.technology.link.SolarLinkBlockType;
import me.fly.newmod.technology.producer.SolarGeneratorBlockType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class TechnologyModuleTypes {

    public static void init() {
        loadRecipes();
    }

    public static void loadRecipes() {
        //ShapedRecipe batcase = shaped(BATTERY_CASE);
        ShapedRecipe czbattery = shaped(COPPER_ZINC_BATTERY);
        //TODO: lead
        ShapedRecipe litbattery = shaped(LITHIUM_BATTERY);
        ShapedRecipe solar1 = shaped(SOLAR_GENERATOR_I);
        ShapedRecipe solar2 = shaped(SOLAR_GENERATOR_II);
        ShapedRecipe solar3 = shaped(SOLAR_GENERATOR_III);

        {
            //batcase.shape(
            //        "R R",
            //        "P P",
            //        "PPP");

            //batcase.setIngredient('R', new ItemStack(Material.REDSTONE));
            //batcase.setIngredient('P', PLASTIC_SHEET.create());
        }

        {
            czbattery.shape(
                    "RUR",
                    "CAZ",
                    " P ");

            czbattery.setIngredient('R', new ItemStack(Material.REDSTONE));
            czbattery.setIngredient('U', MetalsModuleTypes.SULFUR_POWDER.create());
            czbattery.setIngredient('C', MetalsModuleTypes.COPPER_NUGGET.create());
            czbattery.setIngredient('A', MetalsModuleTypes.SALT.create());
            czbattery.setIngredient('Z', MetalsModuleTypes.ZINC_NUGGET.create());
            czbattery.setIngredient('P', new ItemStack(Material.FLOWER_POT));
        }

        {
            litbattery.shape(
                    "LLL",
                    "LCL",
                    "BAB");

            litbattery.setIngredient('L', MetalsModuleTypes.LITHIUM_NUGGET.create());
            litbattery.setIngredient('C', MetalsModuleTypes.SULFUR_POWDER.create());
            litbattery.setIngredient('B', new ItemStack(Material.GREEN_DYE));
            litbattery.setIngredient('A', new ItemStack(Material.DIAMOND));
        }

        {
            solar1.shape(
                    "NGN",
                    "CCC",
                    "BCB");

            solar1.setIngredient('N', MetalsModuleTypes.COPPER_NUGGET.create());
            solar1.setIngredient('G', new ItemStack(Material.GLASS_PANE));
            solar1.setIngredient('C', SOLAR_CELL_I.create());
            solar1.setIngredient('B', COPPER_ZINC_BATTERY.create());
        }

        {

        }
    }

    private static ShapedRecipe shaped(ModItemType type) {
        return new ShapedRecipe(type.getId(), type.create());
    }

    public static final ModItemType CUBIC_ZIRCONIA = new ModItemTypeBuilder(Material.DIAMOND, "cubic_zirconia", TechnologyPlugin.get())
            .customName("Cubic Zirconia", 0xF0F0F8).buildAndRegister();

    public static final ModItemType GAS_CANISTER = null;

    public static final ModItemType PLASTIC_SHEET = new ModItemTypeBuilder(Material.PAPER, "plastic_sheet", TechnologyPlugin.get())
            .customName("Plastic Sheet", 0xffffff).buildAndRegister();

    public static final ModItemType BATTERY_CASE = null;

    //TODO: battery heads
    public static final ModItemType COPPER_ZINC_BATTERY = new ModItemTypeBuilder(Material.FLOWER_POT, "copper_zinc_battery", TechnologyPlugin.get()) // 384
            .customName("Copper Zinc Battery", 0x693f2a).buildAndRegister();
    public static final ModItemType LEAD_BATTERY = new ModItemTypeBuilder(Material.PLAYER_HEAD, "lead_battery", TechnologyPlugin.get()) // 576
            .customName("Lead Battery", 0x693f2a).buildAndRegister();
    public static final ModItemType LITHIUM_BATTERY = new ModItemTypeBuilder(Material.PLAYER_HEAD, "lithium_battery", TechnologyPlugin.get()) // 800
            .customName("Lithium Battery", 0x693f2a).buildAndRegister();

    public static final ModItemType SOLAR_CELL_I = new ModItemTypeBuilder(Material.FIREWORK_STAR, "solar_cell_1", TechnologyPlugin.get())
            .customName("Solar Cell I", 0xFFA080).buildAndRegister();
    public static final ModItemType SOLAR_CELL_II = new ModItemTypeBuilder(Material.FIREWORK_STAR, "solar_cell_2", TechnologyPlugin.get())
            .customName("Solar Cell II", 0xFFA080).buildAndRegister();

    public static final ModItemType SOLAR_GENERATOR_I = new ModItemTypeBuilder(Material.DAYLIGHT_DETECTOR, "solar_generator_1", TechnologyPlugin.get()).block(new SolarGeneratorBlockType(36, 4, 2, 768, TechnologyPlugin.get(), "solar_generator_1"))
            .customName("Solar Generator I", 0x732929).buildAndRegister();
    public static final ModItemType SOLAR_GENERATOR_II = new ModItemTypeBuilder(Material.DAYLIGHT_DETECTOR, "solar_generator_2", TechnologyPlugin.get()).block(new SolarGeneratorBlockType(48, 8, 4, 1152, TechnologyPlugin.get(), "solar_generator_2"))
            .customName("Solar Generator II", 0x736129).buildAndRegister();
    public static final ModItemType SOLAR_GENERATOR_III = new ModItemTypeBuilder(Material.DAYLIGHT_DETECTOR, "solar_generator_3", TechnologyPlugin.get()).block(new SolarGeneratorBlockType(60, 16, 6, 1600, TechnologyPlugin.get(), "solar_generator_3"))
            .customName("Solar Generator III", 0x90b53a).buildAndRegister();

    public static final ModItemType ENERGY_MANAGER_I = new ModItemTypeBuilder(Material.REDSTONE_LAMP, "energy_manager_1", TechnologyPlugin.get()).block(new EnergyManagerBlockType(Material.REDSTONE_LAMP, 1920, TechnologyPlugin.get(), "energy_manager_1"))
            .customName("Energy Manager I", 0xA0A000).buildAndRegister();
    public static final ModItemType ENERGY_MANAGER_II = new ModItemTypeBuilder(Material.REDSTONE_LAMP, "energy_manager_2", TechnologyPlugin.get()).block(new EnergyManagerBlockType(Material.REDSTONE_LAMP, 2880, TechnologyPlugin.get(), "energy_manager_2"))
            .customName("Energy Manager II", 0xA0A000).buildAndRegister();
    public static final ModItemType ENERGY_MANAGER_III = new ModItemTypeBuilder(Material.REDSTONE_LAMP, "energy_manager_3", TechnologyPlugin.get()).block(new EnergyManagerBlockType(Material.REDSTONE_LAMP, 4000, TechnologyPlugin.get(), "energy_manager_3"))
            .customName("Energy Manager III", 0xA0A000).buildAndRegister();

    public static final ModItemType PRESSURE_CHAMBER = new ModItemTypeBuilder(Material.IRON_BLOCK, "pressure_chamber", TechnologyPlugin.get()).block(new PressureChamberBlockType())
            .customName("Pressure Chamber", 0xdbd4ba).buildAndRegister();

    public static final ModItemType SPRUCE_SOLAR_LINK = new ModItemTypeBuilder(Material.SPRUCE_TRAPDOOR, "spruce_solar_link", TechnologyPlugin.get()).block(new SolarLinkBlockType(Material.SPRUCE_TRAPDOOR, 1000, TechnologyPlugin.get(), "spruce_solar_link"))
            .customName("Spruce Solar Link", 0x6d9c7a).buildAndRegister();

    public static <T extends ModItemType> T register(T t) {
        NewMod.get().getItemManager().registerItem(t);

        if(t.getBlock() != null) {
            NewMod.get().getBlockManager().registerBlock(t.getBlock());
        }

        return t;
    }
}
