package com.samplecat.atlantisorigins.common.world.feature;

import com.mojang.serialization.Codec;
import com.samplecat.atlantisorigins.common.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * Silver amalgam block feature.
 * <p>
 * Generates a radial vein of silver amalgam block in cold biomes. The vein spreads
 * outward from the origin in multiple random directions, forming a spoke-like
 * cluster of 20 to 50 blocks.
 */
public class QuicksilverOreFeature extends Feature<NoneFeatureConfiguration> {

    private static final int MIN_SIZE = 20;
    private static final int MAX_SIZE = 50;
    private static final int MAX_ATTEMPTS = MAX_SIZE * 10;

    public QuicksilverOreFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int targetSize = random.nextIntBetweenInclusive(MIN_SIZE, MAX_SIZE);
        int placed = 0;
        int attempts = 0;

        // Start with the origin block
        if (canReplace(level, origin)) {
            level.setBlock(origin, ModBlocks.SILVER_AMALGAM_BLOCK.get().defaultBlockState(), 2);
            placed++;
        }

        // Spread outward in random directions to form a radial vein.
        // Limit attempts so that an unlucky starting position cannot loop forever.
        while (placed < targetSize && attempts < MAX_ATTEMPTS) {
            BlockPos current = origin;
            Direction direction = Direction.getRandom(random);
            int spokeLength = random.nextIntBetweenInclusive(2, 6);

            for (int i = 0; i < spokeLength && placed < targetSize && attempts < MAX_ATTEMPTS; i++) {
                current = current.relative(direction);
                attempts++;

                // Occasionally branch off perpendicular to the main spoke
                if (random.nextFloat() < 0.3F) {
                    Direction branch = getPerpendicular(direction, random);
                    BlockPos branchPos = current.relative(branch);
                    if (canReplace(level, branchPos)) {
                        level.setBlock(branchPos, ModBlocks.SILVER_AMALGAM_BLOCK.get().defaultBlockState(), 2);
                        placed++;
                    }
                }

                if (canReplace(level, current)) {
                    level.setBlock(current, ModBlocks.SILVER_AMALGAM_BLOCK.get().defaultBlockState(), 2);
                    placed++;
                }

                // Keep the cluster reasonably compact
                if (current.distManhattan(origin) > MAX_SIZE / 3) {
                    break;
                }
            }
        }

        return placed >= MIN_SIZE;
    }

    private static Direction getPerpendicular(Direction direction, RandomSource random) {
        Direction[] perpendiculars = switch (direction) {
            case UP, DOWN -> new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
            case NORTH, SOUTH -> new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST};
            case EAST, WEST -> new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH};
        };
        return perpendiculars[random.nextInt(perpendiculars.length)];
    }

    private static boolean canReplace(WorldGenLevel level, BlockPos pos) {
        Block block = level.getBlockState(pos).getBlock();
        return block == Blocks.STONE
                || block == Blocks.DEEPSLATE
                || block == Blocks.ANDESITE
                || block == Blocks.DIORITE
                || block == Blocks.GRANITE
                || block == Blocks.TUFF
                || block == Blocks.DIRT
                || block == Blocks.GRAVEL;
    }
}
