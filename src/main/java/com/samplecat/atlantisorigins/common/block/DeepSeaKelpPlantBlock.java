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

/**
 * Body segment of the Deep Sea Kelp plant. Uses {@link GrowingPlantBodyBlock}
 * logic; its head is {@link DeepSeaKelpBlock}.
 */
public class DeepSeaKelpPlantBlock extends GrowingPlantBodyBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<DeepSeaKelpPlantBlock> CODEC = simpleCodec(DeepSeaKelpPlantBlock::new);

    private static final VoxelShape SHAPE = Shapes.box(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    private static final VoxelShape EMPTY_COLLISION = Shapes.empty();

    public DeepSeaKelpPlantBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, true);
        registerDefaultState(stateDefinition.any()
                .setValue(FRUIT, false)
                .setValue(ABUNDANCE, 0)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public MapCodec<DeepSeaKelpPlantBlock> codec() {
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
    protected BlockState updateHeadAfterConvertedFromBody(BlockState body, BlockState head) {
        if (!head.hasProperty(FRUIT)) {
            return head.setValue(WATERLOGGED, body.getValue(WATERLOGGED));
        }
        return head.setValue(WATERLOGGED, body.getValue(WATERLOGGED))
                .setValue(FRUIT, body.getValue(FRUIT))
                .setValue(ABUNDANCE, body.getValue(ABUNDANCE));
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
        if (level.isClientSide || !newState.isAir() || DeepSeaKelpBlock.REMOVING.get()) {
            return;
        }
        if (state.getValue(FRUIT)) {
            DeepSeaKelpHelper.spawnFruitDrops(state, level, pos);
        }
        BlockPos bottom = DeepSeaKelpBlock.findBottom(level, pos);
        DeepSeaKelpBlock.removePlant(level, bottom);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return ModBlocks.DEEP_SEA_KELP.get();
    }

    /**
     * Places a kelp body segment. Used by world generation.
     */
    public static void placeKelpPlant(LevelAccessor level, BlockPos pos, RandomSource random) {
        boolean waterlogged = level.getFluidState(pos).is(FluidTags.WATER) && level.getFluidState(pos).getAmount() == 8;
        BlockState state = ModBlocks.DEEP_SEA_KELP_PLANT.get().defaultBlockState()
                .setValue(WATERLOGGED, waterlogged);
        level.setBlock(pos, state, 2);
    }
}
