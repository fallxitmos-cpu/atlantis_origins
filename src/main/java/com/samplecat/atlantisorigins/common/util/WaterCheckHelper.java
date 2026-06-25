package com.samplecat.atlantisorigins.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class WaterCheckHelper {

    public static boolean isFullySubmerged(LivingEntity entity) {
        Level level = entity.level();
        BlockPos pos = entity.blockPosition();
        return entity.isInWater()
                && level.getFluidState(pos.above()).is(FluidTags.WATER);
    }

    public static boolean isInBubbleColumn(LivingEntity entity) {
        return entity.level().getBlockState(entity.blockPosition()).is(Blocks.BUBBLE_COLUMN);
    }
}
