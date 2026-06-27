package com.samplecat.atlantisorigins.data;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.block.ModBlocks;
import com.samplecat.atlantisorigins.common.fluid.ModFluids;
import com.samplecat.atlantisorigins.common.item.ModItems;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AtlantisOrigins.MOD_ID);

    private static final ResourceLocation EQUIPMENT_TAB_ID = ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "atlantis_origins_equipment");
    private static final ResourceLocation RAW_MATERIALS_TAB_ID = ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "atlantis_origins_raw_materials");
    private static final ResourceLocation BLOCKS_TAB_ID = ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "atlantis_origins_blocks");
    private static final ResourceLocation LIFE_TAB_ID = ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "atlantis_origins_life");

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATLANTIS_ORIGINS_EQUIPMENT = CREATIVE_MODE_TABS.register("atlantis_origins_equipment",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.atlantis_origins_equipment"))
                    .withTabsBefore(CreativeModeTabs.BUILDING_BLOCKS)
                    .icon(() -> ModItems.OXYGEN_TANK.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.OXYGEN_TANK.get());
                        output.accept(ModItems.FLIPPER.get());
                        output.accept(ModItems.GOGGLES.get());
                        output.accept(ModItems.BEGINNER_DIVING_SUIT.get());
                        output.accept(ModItems.BEGINNER_DIVING_PANTS.get());
                        output.accept(ModBlocks.DEEP_SEA_LAMP_ITEM.get());
                        output.accept(ModItems.ORICHALCUM_PICKAXE.get());
                        output.accept(ModItems.ORICHALCUM_AXE.get());
                        output.accept(ModItems.ORICHALCUM_SHOVEL.get());
                        output.accept(ModItems.ORICHALCUM_HOE.get());
                        output.accept(ModItems.ORICHALCUM_TRIDENT.get());
                        output.accept(ModItems.ORICHALCUM_HELMET.get());
                        output.accept(ModItems.ORICHALCUM_CHESTPLATE.get());
                        output.accept(ModItems.ORICHALCUM_LEGGINGS.get());
                        output.accept(ModItems.ORICHALCUM_BOOTS.get());
                        output.accept(ModItems.POSEIDONS_SPEAR.get());
                        output.accept(ModItems.HYPERBARIC_CHAMBER.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATLANTIS_ORIGINS_RAW_MATERIALS = CREATIVE_MODE_TABS.register("atlantis_origins_raw_materials",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.atlantis_origins_raw_materials"))
                    .withTabsAfter(EQUIPMENT_TAB_ID)
                    .icon(() -> ModItems.CUPRITE_INGOT.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.RAW_CUPRITE.get());
                        output.accept(ModItems.CUPRITE_INGOT.get());
                        output.accept(ModItems.CUPRITE_NUGGET.get());
                        output.accept(ModItems.RAW_SILVER.get());
                        output.accept(ModItems.SILVER_NUGGET.get());
                        output.accept(ModItems.SILVER_INGOT.get());
                        output.accept(ModItems.RAW_ORICHALCUM.get());
                        output.accept(ModItems.ORICHALCUM_SHARD.get());
                        output.accept(ModItems.ORICHALCUM_INGOT.get());
                        output.accept(ModItems.POSEIDONS_TOKEN.get());
                        output.accept(ModItems.SILVER_AMALGAM.get());
                        output.accept(ModItems.BRIMSTONE_DUST.get());
                        output.accept(ModItems.RAW_SALT.get());
                        output.accept(ModItems.RUBBER.get());
                        output.accept(ModItems.CARBON_BLACK.get());
                        output.accept(ModItems.GLOWING_SEED_CLUSTER.get());
                        output.accept(ModItems.RAW_SILICONE.get());
                        output.accept(ModItems.SILICONE.get());
                        output.accept(ModFluids.RUST_POISON_BUCKET.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATLANTIS_ORIGINS_BLOCKS = CREATIVE_MODE_TABS.register("atlantis_origins_blocks",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.atlantis_origins_blocks"))
                    .withTabsAfter(RAW_MATERIALS_TAB_ID)
                    .icon(() -> ModBlocks.DEEP_SEA_LAMP_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.DEEP_SEA_LAMP_ITEM.get());
                        output.accept(ModBlocks.CUPRITE_ORE_ITEM.get());
                        output.accept(ModBlocks.ORICHALCUM_ORE_ITEM.get());
                        output.accept(ModBlocks.BRIMSTONE_ORE_ITEM.get());
                        output.accept(ModBlocks.SALT_ORE_ITEM.get());
                        output.accept(ModBlocks.SILVER_AMALGAM_BLOCK_ITEM.get());
                        output.accept(ModBlocks.SILVER_ORE_ITEM.get());
                        output.accept(ModBlocks.DEEPSLATE_SILVER_ORE_ITEM.get());
                        output.accept(ModBlocks.CUPRITE_WEAPON_STATION_ITEM.get());
                        output.accept(ModBlocks.ALCHEMICAL_REACTOR_ITEM.get());
                        output.accept(ModBlocks.LIQUID_INJECTION_CHAMBER_ITEM.get());
                        output.accept(ModBlocks.SEPARATION_TOWER_ITEM.get());
                        output.accept(ModBlocks.CATALYTIC_REACTOR_ITEM.get());
                        output.accept(ModBlocks.POSEIDON_RELIEF_ITEM.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATLANTIS_ORIGINS_LIFE = CREATIVE_MODE_TABS.register("atlantis_origins_life",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.atlantis_origins_life"))
                    .withTabsAfter(BLOCKS_TAB_ID)
                    .icon(() -> ModItems.ABYSSAL_JELLYFISH_SPAWN_EGG.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.ABYSSAL_JELLYFISH_SPAWN_EGG.get());
                        output.accept(ModItems.BIOLUMINESCENT_FISH_SPAWN_EGG.get());
                        output.accept(ModItems.DEEP_EEL_SPAWN_EGG.get());
                        output.accept(ModItems.ABYSSAL_CRAB_SPAWN_EGG.get());
                        output.accept(ModItems.GULPER_SPAWN_EGG.get());
                        output.accept(ModItems.KRILL_SWARM_SPAWN_EGG.get());
                        output.accept(ModItems.STINGING_JELLYFISH_SPAWN_EGG.get());
                        output.accept(ModItems.GAZING_JELLYFISH_SPAWN_EGG.get());
                        output.accept(ModItems.DEEP_GUARDIAN_SPAWN_EGG.get());
                        output.accept(ModItems.POSEIDONS_BLADE_SPAWN_EGG.get());
                        output.accept(ModItems.RAW_EEL.get());
                        output.accept(ModItems.COOKED_EEL.get());
                        output.accept(ModItems.RAW_CRAB_MEAT.get());
                        output.accept(ModItems.CRAB_MEAT_STEW.get());
                        output.accept(ModItems.RAW_GULPER_FLESH.get());
                        output.accept(ModItems.COOKED_GULPER.get());
                        output.accept(ModItems.KRILL.get());
                        output.accept(ModItems.KRILL_PASTE.get());
                        output.accept(ModItems.KRILL_PASTE_BREAD.get());
                        output.accept(ModBlocks.DEEP_SEA_KELP_ITEM.get());
                        output.accept(ModItems.GLOWING_SEED_CLUSTER.get());
                        output.accept(ModItems.KELP_ROLL.get());
                        output.accept(ModItems.SALTED_FISH.get());
                        output.accept(ModItems.DEEP_SEA_STEW.get());
                        output.accept(ModItems.ABYSSAL_TENTACLE.get());
                        output.accept(ModItems.MEMORY_FRAGMENT.get());
                        output.accept(ModItems.PAIN_CRYSTAL.get());
                        output.accept(ModItems.GAZING_CORE.get());
                        output.accept(ModItems.FUSED_SCALE.get());
                        output.accept(ModItems.DEEP_SEA_WATER_BOTTLE.get());
                        output.accept(ModItems.ABYSSAL_EYE.get());
                        output.accept(ModItems.BROKEN_LOG.get());
                        output.accept(ModItems.TEMPLE_INSCRIPTION.get());
                        output.accept(ModItems.RECOLLECTION_BOTTLE.get());
                        output.accept(ModItems.EYE_OF_REVELATION.get());
                        output.accept(ModItems.MORPH_STABLE_POTION.get());
                        output.accept(ModItems.PAIN_POTION.get());
                        output.accept(ModItems.POSEIDONS_FLESH.get());
                    }).build());
}
