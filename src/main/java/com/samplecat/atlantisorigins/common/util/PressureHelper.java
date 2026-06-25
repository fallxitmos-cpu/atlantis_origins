package com.samplecat.atlantisorigins.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PressureHelper {

    /**
     * Scans upward from the starting position to find the local water surface.
     * Returns the Y coordinate of the top water block. If no surface is found,
     * returns the starting Y as a fallback.
     */
    public static int getWaterSurfaceY(Level level, BlockPos startPos) {
        int maxY = level.getMaxBuildHeight();
        BlockPos.MutableBlockPos mutable = startPos.mutable();
        int prevY = startPos.getY();

        for (int y = startPos.getY(); y < maxY; y++) {
            mutable.setY(y);
            BlockState state = level.getBlockState(mutable);
            if (!state.getFluidState().is(FluidTags.WATER)) {
                return prevY;
            }
            prevY = y;
        }

        return prevY;
    }
}
