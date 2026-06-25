package com.samplecat.atlantisorigins.common.block;

import javax.annotation.Nullable;

import com.samplecat.atlantisorigins.common.blockentity.AlchemicalReactorBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class AlchemicalReactorBlock extends Block implements EntityBlock {

    private static final Component CONTAINER_TITLE = Component.translatable("container.atlantis_origins.alchemical_reactor");

    public AlchemicalReactorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        player.openMenu(state.getMenuProvider(level, pos));
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AlchemicalReactorBlockEntity reactor) {
            return new SimpleMenuProvider(
                    (containerId, playerInventory, player) ->
                            reactor.createMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos)),
                    CONTAINER_TITLE);
        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlchemicalReactorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> net.minecraft.world.level.block.entity.BlockEntityTicker<T> getTicker(Level level, BlockState state, net.minecraft.world.level.block.entity.BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : blockEntityType == com.samplecat.atlantisorigins.common.blockentity.ModBlockEntityTypes.ALCHEMICAL_REACTOR.get()
                ? (BlockEntityTicker<T>) (BlockEntityTicker<AlchemicalReactorBlockEntity>) AlchemicalReactorBlockEntity::tick
                : null;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);
        BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof AlchemicalReactorBlockEntity reactor) {
            for (int i = 0; i < reactor.getItemHandler().getSlots(); i++) {
                ItemStack stack = reactor.getItemHandler().getStackInSlot(i);
                if (!stack.isEmpty()) {
                    drops.add(stack.copy());
                }
            }
        }
        return drops;
    }
}
