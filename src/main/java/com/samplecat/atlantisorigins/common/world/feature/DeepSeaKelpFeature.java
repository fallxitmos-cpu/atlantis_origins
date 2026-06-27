package com.samplecat.atlantisorigins.common.world.feature;

import com.mojang.serialization.Codec;
import com.samplecat.atlantisorigins.common.block.DeepSeaKelpBlock;
import com.samplecat.atlantisorigins.common.block.DeepSeaKelpPlantBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * Places a single-column Deep Sea Kelp plant on the ocean floor, only when
 * Y < 35. The feature searches downward for a solid floor and then grows the
 * kelp 1–3 blocks tall; random ticks will continue growth up to sea level.
 */
public class DeepSeaKelpFeature extends Feature<NoneFeatureConfiguration> {

    private static final int MIN_HEIGHT = 1;
    private static final int MAX_HEIGHT = 3;

    public DeepSeaKelpFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        if (origin.getY() >= 35) {
            return false;
        }

        BlockPos floor = findFloor(level, origin);
        if (floor == null || floor.getY() >= 35) {
            return false;
        }

        BlockPos mainPos = floor.above();
        if (!DeepSeaKelpBlock.canReplaceForKelp(level.getBlockState(mainPos))) {
            return false;
        }

        int height = MIN_HEIGHT + random.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1);
        BlockPos current = mainPos;
        for (int i = 0; i < height; i++) {
            if (!DeepSeaKelpBlock.canReplaceForKelp(level.getBlockState(current))) {
                break;
            }
            if (i == height - 1) {
                DeepSeaKelpBlock.placeKelp(level, current, random);
            } else {
                DeepSeaKelpPlantBlock.placeKelpPlant(level, current, random);
            }
            current = current.above();
        }

        return true;
    }

    private static BlockPos findFloor(WorldGenLevel level, BlockPos origin) {
        BlockPos.MutableBlockPos mutable = origin.mutable();
        int minY = level.getMinBuildHeight();
        while (mutable.getY() > minY && !level.getBlockState(mutable).isSolid()) {
            mutable.move(Direction.DOWN);
        }
        if (mutable.getY() <= minY) {
            return null;
        }
        return mutable.immutable();
    }
}
