package com.samplecat.atlantisorigins;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.samplecat.atlantisorigins.common.attachment.ModAttachments;
import com.samplecat.atlantisorigins.common.block.ModBlocks;
import com.samplecat.atlantisorigins.common.blockentity.ModBlockEntityTypes;
import com.samplecat.atlantisorigins.client.config.ClientConfig;
import com.samplecat.atlantisorigins.common.config.Config;
import com.samplecat.atlantisorigins.common.curios.ModCurios;
import com.samplecat.atlantisorigins.common.damage.ModDamageTypes;
import com.samplecat.atlantisorigins.common.effect.ModMobEffects;
import com.samplecat.atlantisorigins.common.entity.ModEntities;
import com.samplecat.atlantisorigins.common.ai.SquadEvents;
import com.samplecat.atlantisorigins.common.event.CapabilityEventHandler;
import com.samplecat.atlantisorigins.common.event.TridentEventHandler;
import com.samplecat.atlantisorigins.common.fluid.ModFluidTypes;
import com.samplecat.atlantisorigins.common.fluid.ModFluids;
import com.samplecat.atlantisorigins.common.item.ModItems;
import com.samplecat.atlantisorigins.common.menu.ModMenus;
import com.samplecat.atlantisorigins.common.menu.recipe.ModRecipes;
import com.samplecat.atlantisorigins.common.network.ModNetwork;
import com.samplecat.atlantisorigins.common.world.OverworldBiomeInjector;
import com.samplecat.atlantisorigins.common.world.feature.ModFeatures;
import com.samplecat.atlantisorigins.data.ModCreativeTabs;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod(AtlantisOrigins.MOD_ID)
public class AtlantisOrigins {
    public static final String MOD_ID = "atlantis_origins";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AtlantisOrigins(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ModEntities::registerSpawnPlacements);
        modEventBus.addListener(this::registerEntityAttributes);
        modEventBus.addListener(CapabilityEventHandler::onRegisterCapabilities);

        NeoForge.EVENT_BUS.register(TridentEventHandler.class);
        NeoForge.EVENT_BUS.register(SquadEvents.class);
        NeoForge.EVENT_BUS.register(OverworldBiomeInjector.class);

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.BLOCK_ITEMS.register(modEventBus);
        ModFluidTypes.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModFluids.BLOCKS.register(modEventBus);
        ModFluids.ITEMS.register(modEventBus);
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModMobEffects.MOB_EFFECTS.register(modEventBus);
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        ModRecipes.RECIPE_TYPES.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        ModNetwork.register(modEventBus);
        ModCurios.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Atlantis Origins common setup complete");
    }

    private void registerEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(ModEntities.ABYSSAL_JELLYFISH.get(),
                net.minecraft.world.entity.animal.Squid.createAttributes().build());
        event.put(ModEntities.BIOLUMINESCENT_FISH.get(),
                net.minecraft.world.entity.animal.AbstractFish.createAttributes().build());
        event.put(ModEntities.DEEP_EEL.get(),
                net.minecraft.world.entity.animal.AbstractFish.createAttributes().build());
        event.put(ModEntities.ABYSSAL_CRAB.get(),
                net.minecraft.world.entity.animal.axolotl.Axolotl.createAttributes().build());
        event.put(ModEntities.GULPER.get(),
                net.minecraft.world.entity.animal.AbstractFish.createAttributes().build());
        event.put(ModEntities.KRILL_SWARM.get(),
                net.minecraft.world.entity.animal.AbstractFish.createAttributes().build());
        event.put(ModEntities.STINGING_JELLYFISH.get(),
                net.minecraft.world.entity.animal.Squid.createAttributes().build());
        event.put(ModEntities.GAZING_JELLYFISH.get(),
                net.minecraft.world.entity.animal.Squid.createAttributes().build());
        event.put(ModEntities.DEEP_GUARDIAN.get(),
                net.minecraft.world.entity.monster.Guardian.createAttributes()
                        .add(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH, 60.0)
                        .add(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, 0.75)
                        .build());
        event.put(ModEntities.POSEIDONS_BLADE.get(),
                com.samplecat.atlantisorigins.common.entity.PoseidonsBlade.createAttributes().build());
    }

}
