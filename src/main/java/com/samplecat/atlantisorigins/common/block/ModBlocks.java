package com.samplecat.atlantisorigins.common.block;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.item.DeepSeaLampBlockItem;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AtlantisOrigins.MOD_ID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(AtlantisOrigins.MOD_ID);

    public static final DeferredBlock<Block> DEEP_SEA_LAMP = BLOCKS.registerBlock(
            "deep_sea_lamp",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE)
                    .lightLevel(state -> 15)
                    .strength(2.0F));

    public static final DeferredItem<BlockItem> DEEP_SEA_LAMP_ITEM = BLOCK_ITEMS.register(
            "deep_sea_lamp", () -> new DeepSeaLampBlockItem(DEEP_SEA_LAMP.get(), new BlockItem.Properties()));

    public static final DeferredBlock<Block> DIVING_LIGHT_SOURCE = BLOCKS.registerBlock(
            "diving_light_source",
            DivingLightSourceBlock::new,
            BlockBehaviour.Properties.of()
                    .replaceable()
                    .noCollission()
                    .noOcclusion()
                    .noLootTable()
                    .lightLevel(state -> 15)
                    .pushReaction(PushReaction.DESTROY)
                    .instabreak());

    public static final DeferredBlock<Block> CUPRITE_ORE = BLOCKS.registerBlock(
            "cuprite_ore",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE));

    public static final DeferredItem<BlockItem> CUPRITE_ORE_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "cuprite_ore", CUPRITE_ORE);

    public static final DeferredBlock<Block> ORICHALCUM_ORE = BLOCKS.registerBlock(
            "orichalcum_ore",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE));

    public static final DeferredItem<BlockItem> ORICHALCUM_ORE_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "orichalcum_ore", ORICHALCUM_ORE);

    public static final DeferredBlock<Block> BRIMSTONE_ORE = BLOCKS.registerBlock(
            "brimstone_ore",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE)
                    .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> BRIMSTONE_ORE_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "brimstone_ore", BRIMSTONE_ORE);

    public static final DeferredBlock<Block> SALT_ORE = BLOCKS.registerBlock(
            "salt_ore",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE)
                    .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> SALT_ORE_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "salt_ore", SALT_ORE);

    public static final DeferredBlock<Block> SILVER_AMALGAM_BLOCK = BLOCKS.registerBlock(
            "silver_amalgam_block",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
                    .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> SILVER_AMALGAM_BLOCK_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "silver_amalgam_block", SILVER_AMALGAM_BLOCK);

    public static final DeferredBlock<CupriteWeaponStationBlock> CUPRITE_WEAPON_STATION = BLOCKS.registerBlock(
            "cuprite_weapon_station",
            CupriteWeaponStationBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
                    .strength(2.5F));

    public static final DeferredItem<BlockItem> CUPRITE_WEAPON_STATION_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "cuprite_weapon_station", CUPRITE_WEAPON_STATION);

    public static final DeferredBlock<AlchemicalReactorBlock> ALCHEMICAL_REACTOR = BLOCKS.registerBlock(
            "alchemical_reactor",
            AlchemicalReactorBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .strength(3.5F)
                    .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> ALCHEMICAL_REACTOR_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "alchemical_reactor", ALCHEMICAL_REACTOR);

    public static final DeferredBlock<LiquidInjectionChamberBlock> LIQUID_INJECTION_CHAMBER = BLOCKS.registerBlock(
            "liquid_injection_chamber",
            LiquidInjectionChamberBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .strength(3.5F)
                    .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> LIQUID_INJECTION_CHAMBER_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "liquid_injection_chamber", LIQUID_INJECTION_CHAMBER);

    public static final DeferredBlock<SeparationTowerBlock> SEPARATION_TOWER = BLOCKS.registerBlock(
            "separation_tower",
            SeparationTowerBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .strength(3.5F)
                    .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> SEPARATION_TOWER_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "separation_tower", SEPARATION_TOWER);

    public static final DeferredBlock<CatalyticReactorBlock> CATALYTIC_REACTOR = BLOCKS.registerBlock(
            "catalytic_reactor",
            CatalyticReactorBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .strength(3.5F)
                    .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> CATALYTIC_REACTOR_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "catalytic_reactor", CATALYTIC_REACTOR);

    public static final DeferredBlock<Block> POSEIDON_RELIEF = BLOCKS.registerBlock(
            "poseidon_relief",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> POSEIDON_RELIEF_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "poseidon_relief", POSEIDON_RELIEF);

    public static final DeferredBlock<Block> SILVER_ORE = BLOCKS.registerBlock(
            "silver_ore",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)
                    .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> SILVER_ORE_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "silver_ore", SILVER_ORE);

    public static final DeferredBlock<Block> DEEPSLATE_SILVER_ORE = BLOCKS.registerBlock(
            "deepslate_silver_ore",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
                    .requiresCorrectToolForDrops());

    public static final DeferredItem<BlockItem> DEEPSLATE_SILVER_ORE_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(
            "deepslate_silver_ore", DEEPSLATE_SILVER_ORE);
}
