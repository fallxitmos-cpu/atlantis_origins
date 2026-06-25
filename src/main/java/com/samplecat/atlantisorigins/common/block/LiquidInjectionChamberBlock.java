package com.samplecat.atlantisorigins.common.block;

import java.util.List;

import javax.annotation.Nullable;

import com.samplecat.atlantisorigins.common.blockentity.LiquidInjectionChamberBlockEntity;

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

public class LiquidInjectionChamberBlock extends Block implements EntityBlock {

    private static final Component CONTAINER_TITLE = Component.translatable("container.atlantis_origins.liquid_injection_chamber");

    public LiquidInjectionChamberBlock(Properties properties) {
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
        if (blockEntity instanceof LiquidInjectionChamberBlockEntity entity) {
            return new SimpleMenuProvider(
                    (containerId, playerInventory, player) ->
                            entity.createMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos)),
                    CONTAINER_TITLE);
        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LiquidInjectionChamberBlockEntity(pos, state);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, net.minecraft.world.level.block.entity.BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : blockEntityType == com.samplecat.atlantisorigins.common.blockentity.ModBlockEntityTypes.LIQUID_INJECTION_CHAMBER.get()
                ? (BlockEntityTicker<T>) (BlockEntityTicker<LiquidInjectionChamberBlockEntity>) LiquidInjectionChamberBlockEntity::tick
                : null;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);
        BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof LiquidInjectionChamberBlockEntity entity) {
            for (int i = 0; i < entity.getItemHandler().getSlots(); i++) {
                ItemStack stack = entity.getItemHandler().getStackInSlot(i);
                if (!stack.isEmpty()) {
                    drops.add(stack.copy());
                }
            }
        }
        return drops;
    }
}
