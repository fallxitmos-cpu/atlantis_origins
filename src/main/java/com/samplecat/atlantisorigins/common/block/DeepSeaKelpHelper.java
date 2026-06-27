package com.samplecat.atlantisorigins.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;

import com.samplecat.atlantisorigins.common.item.ModItems;

import net.minecraft.world.level.block.Blocks;

public final class DeepSeaKelpHelper {

    private DeepSeaKelpHelper() {
    }

    // Shared properties used by both the head and body blocks.
    public static final BooleanProperty FRUIT = BooleanProperty.create("fruit");
    public static final IntegerProperty ABUNDANCE = IntegerProperty.create("abundance", 0, 7);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static void tryFruit(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(FRUIT)) {
            int current = state.getValue(ABUNDANCE);
            if (current < 7 && random.nextInt(4) == 0) {
                level.setBlock(pos, state.setValue(ABUNDANCE, current + 1), 3);
            }
        } else if (random.nextInt(7) == 0) {
            level.setBlock(pos, state.setValue(FRUIT, true).setValue(ABUNDANCE, 0), 3);
        }
    }

    public static InteractionResult harvestFruit(BlockState state, Level level, BlockPos pos) {
        if (!state.getValue(FRUIT)) {
            return InteractionResult.PASS;
        }
        int dropCount = state.getValue(ABUNDANCE) + 1;
        BlockState harvested = state.setValue(FRUIT, false).setValue(ABUNDANCE, 0);
        level.setBlock(pos, harvested, 3);
        if (level instanceof ServerLevel serverLevel) {
            ItemStack drop = new ItemStack(ModItems.GLOWING_SEED_CLUSTER.get(), dropCount);
            Player nearest = serverLevel.getNearestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8.0, false);
            if (nearest != null) {
                nearest.getInventory().add(drop);
            } else {
                ItemEntity entity = new ItemEntity(serverLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
                serverLevel.addFreshEntity(entity);
            }
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(harvested));
        }
        return InteractionResult.SUCCESS;
    }

    public static void spawnFruitDrops(BlockState state, Level level, BlockPos pos) {
        if (!state.getValue(FRUIT)) {
            return;
        }
        int dropCount = state.getValue(ABUNDANCE) + 1;
        ItemStack drop = new ItemStack(ModItems.GLOWING_SEED_CLUSTER.get(), dropCount);
        ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
        entity.setDefaultPickUpDelay();
        level.addFreshEntity(entity);
    }

    /**
     * Randomly converts the block below this kelp segment into moss. Taller kelp
     * converts slower, using the same height-based multiplier as growth.
     */
    public static void tryMossConversion(ServerLevel level, BlockPos pos, RandomSource random) {
        int height = DeepSeaKelpBlock.getHeight(level, pos);
        double multiplier = DeepSeaKelpBlock.getGrowthMultiplier(height);
        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);
        if (!belowState.is(Blocks.MOSS_BLOCK) && belowState.isSolid()
                && random.nextDouble() < 0.05D * multiplier) {
            level.setBlock(below, Blocks.MOSS_BLOCK.defaultBlockState(), 3);
        }
    }
}
