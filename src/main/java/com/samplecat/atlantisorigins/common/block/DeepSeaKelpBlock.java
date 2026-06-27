package com.samplecat.atlantisorigins.common.block;

import static com.samplecat.atlantisorigins.common.block.DeepSeaKelpHelper.ABUNDANCE;
import static com.samplecat.atlantisorigins.common.block.DeepSeaKelpHelper.FRUIT;
import static com.samplecat.atlantisorigins.common.block.DeepSeaKelpHelper.WATERLOGGED;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;

/**
 * Head/top segment of the Deep Sea Kelp plant. Extends {@link GrowingPlantHeadBlock}
 * for reliable upward growth, while keeping custom fruit/abundance logic.
 */
public class DeepSeaKelpBlock extends GrowingPlantHeadBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<DeepSeaKelpBlock> CODEC = simpleCodec(DeepSeaKelpBlock::new);

    // AGE is inherited from GrowingPlantHeadBlock.
    private static final VoxelShape SHAPE = Shapes.box(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    private static final VoxelShape EMPTY_COLLISION = Shapes.empty();
    private static final double GROWTH_CHANCE = 0.14D;

    static final ThreadLocal<Boolean> REMOVING = ThreadLocal.withInitial(() -> false);

    public DeepSeaKelpBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, true, GROWTH_CHANCE);
        registerDefaultState(stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(FRUIT, false)
                .setValue(ABUNDANCE, 0)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public MapCodec<DeepSeaKelpBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FRUIT, ABUNDANCE, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        if (!fluidState.is(FluidTags.WATER) || fluidState.getAmount() != 8) {
            return null;
        }
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(WATERLOGGED, true);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        BlockState updated = super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        if (updated.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return updated;
    }

    @Override
    protected BlockState updateBodyAfterConvertedFromHead(BlockState head, BlockState body) {
        return body.setValue(WATERLOGGED, head.getValue(WATERLOGGED))
                .setValue(FRUIT, head.getValue(FRUIT))
                .setValue(ABUNDANCE, head.getValue(ABUNDANCE));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return EMPTY_COLLISION;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int height = getHeight(level, pos);
        double multiplier = getGrowthMultiplier(height);

        BlockPos above = pos.above();
        if (state.getValue(AGE) < MAX_AGE
                && CommonHooks.canCropGrow(level, above, state, random.nextDouble() < GROWTH_CHANCE * multiplier)) {
            BlockState aboveState = level.getBlockState(above);
            if (canGrowInto(aboveState)) {
                boolean waterlogged = aboveState.getFluidState().is(FluidTags.WATER) && aboveState.getFluidState().getAmount() == 8;
                BlockState grown = getGrowIntoState(state, random).setValue(WATERLOGGED, waterlogged);
                level.setBlockAndUpdate(above, grown);
                CommonHooks.fireCropGrowPost(level, above, level.getBlockState(above));
            }
        }

        DeepSeaKelpHelper.tryMossConversion(level, pos, random);
        DeepSeaKelpHelper.tryFruit(state, level, pos, random);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }
        InteractionResult result = DeepSeaKelpHelper.harvestFruit(state, level, pos);
        return result == InteractionResult.SUCCESS
                ? ItemInteractionResult.SUCCESS
                : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);
        if (level.isClientSide || !newState.isAir() || REMOVING.get()) {
            return;
        }
        if (state.getValue(FRUIT)) {
            DeepSeaKelpHelper.spawnFruitDrops(state, level, pos);
        }
        BlockPos bottom = findBottom(level, pos);
        removePlant(level, bottom);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        FluidState fluid = state.getFluidState();
        return fluid.is(FluidTags.WATER) && fluid.getAmount() == 8;
    }

    @Override
    protected GrowingPlantBodyBlock getBodyBlock() {
        return ModBlocks.DEEP_SEA_KELP_PLANT.get();
    }

    @Override
    protected boolean canAttachTo(BlockState state) {
        return state.isSolid()
                || state.is(ModBlocks.DEEP_SEA_KELP.get())
                || state.is(ModBlocks.DEEP_SEA_KELP_PLANT.get());
    }

    /**
     * Checks whether the given block can be replaced by a kelp block during
     * feature placement or growth.
     */
    public static boolean canReplaceForKelp(BlockState state) {
        FluidState fluid = state.getFluidState();
        return fluid.is(FluidTags.WATER) && fluid.getAmount() == 8;
    }

    /**
     * Places a kelp head. Used by natural growth and world generation.
     */
    public static void placeKelp(LevelAccessor level, BlockPos pos, RandomSource random) {
        boolean waterlogged = level.getFluidState(pos).is(FluidTags.WATER) && level.getFluidState(pos).getAmount() == 8;
        BlockState state = ModBlocks.DEEP_SEA_KELP.get().defaultBlockState()
                .setValue(AGE, random.nextInt(MAX_AGE))
                .setValue(WATERLOGGED, waterlogged);
        level.setBlock(pos, state, 2);
    }

    /**
     * Returns how many blocks tall this kelp column is, including this block.
     */
    public static int getHeight(Level level, BlockPos pos) {
        BlockPos bottom = findBottom(level, pos);
        int height = 1;
        BlockPos current = bottom.above();
        while (level.getBlockState(current).is(ModBlocks.DEEP_SEA_KELP.get())
                || level.getBlockState(current).is(ModBlocks.DEEP_SEA_KELP_PLANT.get())) {
            height++;
            current = current.above();
        }
        return height;
    }

    /**
     * Random-tick multiplier based on plant height. Taller kelp receives a
     * smaller multiplier and therefore grows/converts moss more slowly.
     *
     * <p>Formula: y = 2 * 0.3 * e^(0.15 * (15 - x)), capped at 1.0.
     */
    public static double getGrowthMultiplier(int height) {
        return Math.min(1.0D, 0.6D * Math.exp(0.15D * (15 - height)));
    }

    /**
     * Finds the bottom-most kelp block (head or body) in this column.
     */
    public static BlockPos findBottom(Level level, BlockPos pos) {
        BlockPos current = pos;
        while (level.getBlockState(current.below()).is(ModBlocks.DEEP_SEA_KELP.get())
                || level.getBlockState(current.below()).is(ModBlocks.DEEP_SEA_KELP_PLANT.get())) {
            current = current.below();
        }
        return current;
    }

    /**
     * Removes the whole kelp column starting from the bottom, restoring water
     * for waterlogged blocks.
     */
    public static void removePlant(Level level, BlockPos bottom) {
        if (level.isClientSide) {
            return;
        }
        REMOVING.set(true);
        try {
            BlockPos current = bottom;
            while (current.getY() < level.getMaxBuildHeight()) {
                BlockState state = level.getBlockState(current);
                if (!state.is(ModBlocks.DEEP_SEA_KELP.get()) && !state.is(ModBlocks.DEEP_SEA_KELP_PLANT.get())) {
                    break;
                }
                BlockState replacement = state.getValue(WATERLOGGED)
                        ? Blocks.WATER.defaultBlockState()
                        : Blocks.AIR.defaultBlockState();
                level.setBlock(current, replacement, 35);
                current = current.above();
            }
        } finally {
            REMOVING.set(false);
        }
    }
}
