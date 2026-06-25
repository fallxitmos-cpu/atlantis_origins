package com.samplecat.atlantisorigins.common.blockentity;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.block.ModBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
            Registries.BLOCK_ENTITY_TYPE, AtlantisOrigins.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlchemicalReactorBlockEntity>> ALCHEMICAL_REACTOR = BLOCK_ENTITY_TYPES.register(
            "alchemical_reactor",
            () -> BlockEntityType.Builder.of(AlchemicalReactorBlockEntity::new, ModBlocks.ALCHEMICAL_REACTOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LiquidInjectionChamberBlockEntity>> LIQUID_INJECTION_CHAMBER = BLOCK_ENTITY_TYPES.register(
            "liquid_injection_chamber",
            () -> BlockEntityType.Builder.of(LiquidInjectionChamberBlockEntity::new, ModBlocks.LIQUID_INJECTION_CHAMBER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SeparationTowerBlockEntity>> SEPARATION_TOWER = BLOCK_ENTITY_TYPES.register(
            "separation_tower",
            () -> BlockEntityType.Builder.of(SeparationTowerBlockEntity::new, ModBlocks.SEPARATION_TOWER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CatalyticReactorBlockEntity>> CATALYTIC_REACTOR = BLOCK_ENTITY_TYPES.register(
            "catalytic_reactor",
            () -> BlockEntityType.Builder.of(CatalyticReactorBlockEntity::new, ModBlocks.CATALYTIC_REACTOR.get()).build(null));


}
