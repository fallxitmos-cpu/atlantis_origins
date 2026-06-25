package com.samplecat.atlantisorigins.common.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

/**
 * Orichalcum tool tier.
 * <p>
 * Stat-wise it mirrors a Netherite tool enchanted with Efficiency II and Unbreaking II:
 * <ul>
 *   <li>Speed: Netherite base 9 + Efficiency II bonus (2*2+1) = 14</li>
 *   <li>Durability: Netherite base 2031 * (Unbreaking II factor of 3) = 6093</li>
 *   <li>Highest harvest tier (incorrect for netherite tool tag)</li>
 * </ul>
 */
public class OrichalcumTier implements Tier {

    public static final int USES = 6093;
    public static final float SPEED = 14.0F;
    public static final float ATTACK_DAMAGE_BONUS = 5.0F;
    public static final int ENCHANTMENT_VALUE = 15;

    public static final Tier INSTANCE = new OrichalcumTier();

    @Override
    public int getUses() {
        return USES;
    }

    @Override
    public float getSpeed() {
        return SPEED;
    }

    @Override
    public float getAttackDamageBonus() {
        return ATTACK_DAMAGE_BONUS;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        // Treats everything that netherite can mine as correct, and blocks requiring
        // a higher tier as incorrect (vanilla has no higher tier).
        return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
    }

    @Override
    public int getEnchantmentValue() {
        return ENCHANTMENT_VALUE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.ORICHALCUM_INGOT.get());
    }

    public static boolean isOrichalcumTool(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.getItem() instanceof OrichalcumPickaxeItem
                || stack.getItem() instanceof OrichalcumAxeItem
                || stack.getItem() instanceof OrichalcumShovelItem
                || stack.getItem() instanceof OrichalcumHoeItem;
    }
}
