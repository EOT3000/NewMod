 package fly.newmod.impl.magic;

import fly.newmod.NewMod;
import fly.newmod.bases.magic.Rune;
import fly.newmod.bases.magic.Runes;
import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

 public class LevelRune extends Rune {
    private int level;

    private static Enchantment GENERIC_LEVEL = new Enchantment(new NamespacedKey(NewMod.get(), "generic_leveling_enchant_lol")) {
        @Override
        public String getName() {
            return "not needed";
        }

        @Override
        public int getMaxLevel() {
            return 1000;
        }

        @Override
        public int getStartLevel() {
            return 1;
        }

        @Override
        public EnchantmentTarget getItemTarget() {
            return EnchantmentTarget.ALL;
        }

        @Override
        public boolean isTreasure() {
            return false;
        }

        @Override
        public boolean isCursed() {
            return false;
        }

        @Override
        public boolean conflictsWith(Enchantment enchantment) {
            return false;
        }

        @Override
        public boolean canEnchantItem(ItemStack itemStack) {
            return false;
        }

        @Override
        public @NotNull Set<EquipmentSlot> getActiveSlots() {
            return new HashSet<>();
        }

        @Override
        public @NotNull Component displayName(int i) {
            return Component.empty();
        }

        @Override
        public boolean isTradeable() {
            return false;
        }

        @Override
        public boolean isDiscoverable() {
            return false;
        }

        @Override
        public @NotNull EnchantmentRarity getRarity() {
            return EnchantmentRarity.COMMON;
        }

        @Override
        public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
            return 0;
        }

        @Override
        public @NotNull String translationKey() {
            return "lol";
        }
    };

    public LevelRune(String name, String id, Color color, int level) {
        super(name, id, color, RuneType.ENCHANTMENT_LEVEL);

        this.level = level;

        Runes.setRune(GENERIC_LEVEL, level, this);

        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(NewMod.get(), id), this);

        if(level == 1) {
            return;
        }
        if(level == 2) {
            recipe.addIngredient(Runes.getRune(GENERIC_LEVEL, 1));
            recipe.addIngredient(Runes.getRune(GENERIC_LEVEL, 1));
        } else {
            recipe.addIngredient(Runes.getRune(GENERIC_LEVEL, level-1));
            recipe.addIngredient(Runes.getRune(GENERIC_LEVEL, level-2));
        }

        addRecipe(recipe);
    }

    public int getLevel() {
        return level;
    }
}