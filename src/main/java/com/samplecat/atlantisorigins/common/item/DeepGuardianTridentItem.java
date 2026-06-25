package com.samplecat.atlantisorigins.common.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Unobtainable trident used only by Deep Guardians.
 * Looks like an orichalcum trident but never gains enchantment glint.
 */
public class DeepGuardianTridentItem extends OrichalcumTridentItem {

    public DeepGuardianTridentItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        // Intentionally skip the auto-enchant logic from OrichalcumTridentItem.
    }
}
