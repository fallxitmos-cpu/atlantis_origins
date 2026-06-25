package com.samplecat.atlantisorigins.common.fluid;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, AtlantisOrigins.MOD_ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AtlantisOrigins.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AtlantisOrigins.MOD_ID);

    // Rust Poison
    public static final DeferredHolder<Fluid, BaseFlowingFluid> RUST_POISON = FLUIDS.register(
            "rust_poison", () -> new BaseFlowingFluid.Source(rustPoisonProperties()));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_RUST_POISON = FLUIDS.register(
            "flowing_rust_poison", () -> new BaseFlowingFluid.Flowing(rustPoisonProperties()));
    public static final DeferredBlock<LiquidBlock> RUST_POISON_BLOCK = BLOCKS.registerBlock(
            "rust_poison", props -> new LiquidBlock(RUST_POISON.get(), props), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER));
    public static final DeferredItem<Item> RUST_POISON_BUCKET = ITEMS.register(
            "rust_poison_bucket", () -> new BucketItem(RUST_POISON.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

    private static BaseFlowingFluid.Properties rustPoisonProperties() {
        return new BaseFlowingFluid.Properties(ModFluidTypes.RUST_POISON, RUST_POISON, FLOWING_RUST_POISON)
                .bucket(RUST_POISON_BUCKET)
                .block(RUST_POISON_BLOCK)
                .tickRate(15);
    }
}
