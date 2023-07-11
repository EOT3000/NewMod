package me.fly.newmod.technology;

import me.fly.newmod.NewMod;
import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.builders.ModItemTypeBuilder;
import me.fly.newmod.metals.MetalsModuleTypes;
import me.fly.newmod.technology.consumer.PressureChamberItemType;
import me.fly.newmod.technology.link.SolarLinkItemType;
import me.fly.newmod.technology.producer.SolarGeneratorItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class TechnologyModuleTypes {

    public static void init() {
        loadRecipes();
    }

    public static void loadRecipes() {
        ShapedRecipe batcase = shaped(BATTERY_CASE);
        ShapedRecipe czbattery = shaped(COPPER_ZINC_BATTERY);
        //TODO: lead
        ShapedRecipe litbattery = shaped(LITHIUM_BATTERY);
        ShapedRecipe solar1 = shaped(SOLAR_GENERATOR_I);
        ShapedRecipe solar2 = shaped(SOLAR_GENERATOR_II);
        ShapedRecipe solar3 = shaped(SOLAR_GENERATOR_III);

        {
            batcase.shape(
                    "R R",
                    "P P",
                    "PPP");

            batcase.setIngredient('R', new ItemStack(Material.REDSTONE));
            batcase.setIngredient('P', PLASTIC_SHEET.create());
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
            litbattery.setIngredient('A', BATTERY_CASE.create());
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

    public static final SolarGeneratorItemType SOLAR_GENERATOR_I = register(new SolarGeneratorItemType(36, 4, 2, 768, "solar_generator_1", "Solar Generator I", 0x732929));
    public static final SolarGeneratorItemType SOLAR_GENERATOR_II = register(new SolarGeneratorItemType(48, 8, 4, 1152, "solar_generator_2", "Solar Generator II", 0x736129));
    public static final SolarGeneratorItemType SOLAR_GENERATOR_III = register(new SolarGeneratorItemType(60, 16, 6, 1600, "solar_generator_3", "Solar Generator III", 0x90b53a));

    public static final EnergyManagerItemType ENERGY_MANAGER_I = register(new EnergyManagerItemType(Material.REDSTONE_LAMP, 1920, "energy_manager_1", "Energy Manager I", 0xA0A000));
    public static final EnergyManagerItemType ENERGY_MANAGER_II = register(new EnergyManagerItemType(Material.REDSTONE_LAMP, 2880, "energy_manager_2", "Energy Manager II", 0xA0A000));
    public static final EnergyManagerItemType ENERGY_MANAGER_III = register(new EnergyManagerItemType(Material.REDSTONE_LAMP, 4000, "energy_manager_3", "Energy Manager III", 0xA0A000));

    public static final PressureChamberItemType PRESSURE_CHAMBER_ITEM_TYPE = register(new PressureChamberItemType());

    public static final SolarLinkItemType SPRUCE_SOLAR_LINK = register(new SolarLinkItemType(Material.SPRUCE_TRAPDOOR, 1000, "spruce_solar_link", "Spruce Solar Link", 0x8068040));

    public static <T extends ModItemType> T register(T t) {
        NewMod.get().getItemManager().registerItem(t);

        if(t.getBlock() != null) {
            NewMod.get().getBlockManager().registerBlock(t.getBlock());
        }

        return t;
    }
}
