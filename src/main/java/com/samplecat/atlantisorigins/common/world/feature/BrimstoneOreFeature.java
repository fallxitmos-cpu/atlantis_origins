package com.samplecat.atlantisorigins.common.world.feature;

import com.mojang.serialization.Codec;
import com.samplecat.atlantisorigins.common.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BrimstoneOreFeature extends Feature<NoneFeatureConfiguration> {

    private static final int MAX_CLUSTER_SIZE = 50;
    private static final int SEARCH_RADIUS = 5;

    public BrimstoneOreFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int originChunkX = SectionPos.blockToSectionCoord(origin.getX());
        int originChunkZ = SectionPos.blockToSectionCoord(origin.getZ());

        // Find lava near the origin and start the cluster right next to it,
        // so that brimstone is guaranteed to generate adjacent to lava pools.
        BlockPos lavaPos = findLava(level, origin, originChunkX, originChunkZ);
        if (lavaPos == null) {
            return false;
        }

        BlockPos current = lavaPos.relative(Direction.getRandom(random));
        int placed = 0;
        Direction direction = Direction.getRandom(random);

        while (placed < MAX_CLUSTER_SIZE) {
            if (isInOriginChunk(current, originChunkX, originChunkZ) && canReplace(level, current)) {
                level.setBlock(current, ModBlocks.BRIMSTONE_ORE.get().defaultBlockState(), 2);
                placed++;
            }

            if (random.nextFloat() < 0.25F) {
                direction = Direction.getRandom(random);
            }

            current = current.relative(direction);

            // Keep the cluster compact around the lava source
            if (current.distManhattan(lavaPos) > MAX_CLUSTER_SIZE / 2) {
                break;
            }
        }

        return placed > 0;
    }

    private static boolean isInOriginChunk(BlockPos pos, int originChunkX, int originChunkZ) {
        return SectionPos.blockToSectionCoord(pos.getX()) == originChunkX
                && SectionPos.blockToSectionCoord(pos.getZ()) == originChunkZ;
    }

    private static BlockPos findLava(WorldGenLevel level, BlockPos pos, int originChunkX, int originChunkZ) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int x = -SEARCH_RADIUS; x <= SEARCH_RADIUS; x++) {
            for (int y = -SEARCH_RADIUS; y <= SEARCH_RADIUS; y++) {
                for (int z = -SEARCH_RADIUS; z <= SEARCH_RADIUS; z++) {
                    mutable.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                    if (isInOriginChunk(mutable, originChunkX, originChunkZ)
                            && level.getBlockState(mutable).is(Blocks.LAVA)) {
                        return mutable.immutable();
                    }
                }
            }
        }
        return null;
    }

    private static boolean canReplace(WorldGenLevel level, BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.STONE)
                || level.getBlockState(pos).is(Blocks.DEEPSLATE)
                || level.getBlockState(pos).is(Blocks.ANDESITE)
                || level.getBlockState(pos).is(Blocks.DIORITE)
                || level.getBlockState(pos).is(Blocks.GRANITE)
                || level.getBlockState(pos).is(Blocks.TUFF);
    }
}
