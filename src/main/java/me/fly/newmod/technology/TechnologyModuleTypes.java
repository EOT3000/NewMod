package me.fly.newmod.technology;

import me.fly.newmod.api.item.ModItemType;
import me.fly.newmod.api.item.builders.ModItemTypeBuilder;
import me.fly.newmod.metals.MetalsModuleTypes;
import me.fly.newmod.technology.producer.SolarGeneratorItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class TechnologyModuleTypes {
    public void loadRecipes() {
        //TODO: shaped recipe method
        ShapedRecipe czbattery = new ShapedRecipe(COPPER_ZINC_BATTERY.getId(), COPPER_ZINC_BATTERY.create());

        czbattery.shape(
                "R R",
                "CSZ",
                " P ");

        czbattery.setIngredient('R', new ItemStack(Material.REDSTONE));
        czbattery.setIngredient('C', MetalsModuleTypes.COPPER_NUGGET.create());
        czbattery.setIngredient('S', MetalsModuleTypes.SALT.create());
        czbattery.setIngredient('Z', MetalsModuleTypes.ZINC_NUGGET.create());
        czbattery.setIngredient('P', new ItemStack(Material.FLOWER_POT));

        ShapedRecipe solar1 = new ShapedRecipe(SOLAR_GENERATOR_I.getId(), SOLAR_GENERATOR_I.create());

        solar1.shape();
    }

    public static final ModItemType PLASTIC_SHEET = new ModItemTypeBuilder(Material.PAPER, "plastic_sheet", null)
            .customName("Plastic Sheet", 0xffffff).buildAndRegister();

    public static final ModItemType COPPER_ZINC_BATTERY = new ModItemTypeBuilder(Material.FLOWER_POT, "copper_zinc_battery", null)
            .customName("Copper Zinc Battery", 0x693f2a).buildAndRegister();

    public static final SolarGeneratorItemType SOLAR_GENERATOR_I = new SolarGeneratorItemType(36, 4, 2, 96, "solar_generator_1", "Solar Generator I", 0x732929);
    public static final SolarGeneratorItemType SOLAR_GENERATOR_II = new SolarGeneratorItemType(48, 8, 4, 144, "solar_generator_2", "Solar Generator II", 0x736129);
    public static final SolarGeneratorItemType SOLAR_GENERATOR_III = new SolarGeneratorItemType(60, 16, 6, 200, "solar_generator_3", "Solar Generator III", 0x90b53a);

}
