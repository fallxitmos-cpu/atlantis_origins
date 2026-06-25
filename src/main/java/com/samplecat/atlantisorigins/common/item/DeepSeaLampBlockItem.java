package com.samplecat.atlantisorigins.common.item;

import com.samplecat.atlantisorigins.common.block.DivingLightSourceBlock;
import com.samplecat.atlantisorigins.common.block.ModBlocks;
import com.samplecat.atlantisorigins.common.config.Config;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.fml.ModList;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeepSeaLampBlockItem extends BlockItem {

    private static final String LAMB_DYN_LIGHTS_MOD_ID = "lambdynlights";
    private static final Map<UUID, BlockPos> LAST_LIGHT_POS = new HashMap<>();

    public DeepSeaLampBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide() || !(entity instanceof Player player)) {
            return;
        }

        // Only run logic for the item currently in the main hand (selected) or offhand (slot 150).
        if (!isSelected && slotId != 150) {
            return;
        }

        UUID id = player.getUUID();

        if (!player.isEyeInFluid(FluidTags.WATER)) {
            removePlayerLight(level, id);
            return;
        }

        // If LambDynamicLights is present, let it handle the dynamic light.
        if (isLambDynamicLightsLoaded() || !Config.USE_BUILTIN_DYNAMIC_LIGHT.get()) {
            removePlayerLight(level, id);
            return;
        }

        BlockPos currentPos = BlockPos.containing(player.getEyePosition());
        BlockPos lastPos = LAST_LIGHT_POS.get(id);
        if (lastPos != null && !lastPos.equals(currentPos)) {
            removeLight(level, lastPos);
        }

        placeLight(level, currentPos);
        LAST_LIGHT_POS.put(id, currentPos);
    }

    private static boolean isLambDynamicLightsLoaded() {
        return ModList.get().isLoaded(LAMB_DYN_LIGHTS_MOD_ID);
    }

    private static void removePlayerLight(Level level, UUID playerId) {
        BlockPos lastPos = LAST_LIGHT_POS.remove(playerId);
        if (lastPos != null) {
            removeLight(level, lastPos);
        }
    }

    private static void removeLight(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.is(ModBlocks.DIVING_LIGHT_SOURCE.get())) {
            boolean waterlogged = state.getValue(BlockStateProperties.WATERLOGGED);
            BlockState replacement = waterlogged ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            level.setBlock(pos, replacement, 2 | 16);
        }
    }

    private static void placeLight(Level level, BlockPos pos) {
        BlockState existing = level.getBlockState(pos);
        if (!canReplace(existing)) {
            return;
        }

        boolean waterlogged = existing.getFluidState().is(FluidTags.WATER);
        BlockState lightState = ModBlocks.DIVING_LIGHT_SOURCE.get().defaultBlockState()
                .setValue(BlockStateProperties.WATERLOGGED, waterlogged);

        level.setBlock(pos, lightState, 2 | 16);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.scheduleTick(pos, ModBlocks.DIVING_LIGHT_SOURCE.get(), DivingLightSourceBlock.CHECK_INTERVAL);
        }
    }

    private static boolean canReplace(BlockState state) {
        return state.isAir()
                || state.is(Blocks.WATER)
                || state.is(ModBlocks.DIVING_LIGHT_SOURCE.get())
                || state.canBeReplaced();
    }
}
