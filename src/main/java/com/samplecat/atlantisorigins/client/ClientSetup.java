package com.samplecat.atlantisorigins.client;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.event.GogglesClientHandler;
import com.samplecat.atlantisorigins.client.model.AbyssalJellyfishModel;
import com.samplecat.atlantisorigins.client.model.BioluminescentFishModel;
import com.samplecat.atlantisorigins.client.model.DeepEelModel;
import com.samplecat.atlantisorigins.client.model.DeepGuardianModel;
import com.samplecat.atlantisorigins.client.model.OrichalcumTridentModel;
import com.samplecat.atlantisorigins.client.model.PoseidonsBladeModel;
import com.samplecat.atlantisorigins.client.render.AbyssalCrabRenderer;
import com.samplecat.atlantisorigins.client.render.AbyssalJellyfishRenderer;
import com.samplecat.atlantisorigins.client.render.BioluminescentFishRenderer;
import com.samplecat.atlantisorigins.client.render.DeepEelRenderer;
import com.samplecat.atlantisorigins.client.render.DebugHudRenderer;
import com.samplecat.atlantisorigins.client.render.DeepGuardianRenderer;
import com.samplecat.atlantisorigins.client.render.GazingJellyfishRenderer;
import com.samplecat.atlantisorigins.client.render.GulperRenderer;
import com.samplecat.atlantisorigins.client.render.KrillSwarmRenderer;
import com.samplecat.atlantisorigins.client.render.OrichalcumTridentBEWLR;
import com.samplecat.atlantisorigins.client.render.OrichalcumTridentRenderer;
import com.samplecat.atlantisorigins.client.render.OxygenHudRenderer;
import com.samplecat.atlantisorigins.client.render.PoseidonsBladeRenderer;
import com.samplecat.atlantisorigins.client.render.StingingJellyfishRenderer;
import com.samplecat.atlantisorigins.client.render.ScreenEffectsRenderer;
import com.samplecat.atlantisorigins.client.screen.AlchemicalReactorScreen;
import com.samplecat.atlantisorigins.client.screen.CatalyticReactorScreen;
import com.samplecat.atlantisorigins.client.screen.CupriteWeaponStationScreen;
import com.samplecat.atlantisorigins.client.screen.LiquidInjectionChamberScreen;
import com.samplecat.atlantisorigins.client.screen.SeparationTowerScreen;
import com.samplecat.atlantisorigins.common.fluid.ModFluidTypes;
import com.samplecat.atlantisorigins.common.item.ModItems;
import com.samplecat.atlantisorigins.common.menu.ModMenus;
import com.samplecat.atlantisorigins.common.network.ModNetwork;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = AtlantisOrigins.MOD_ID, dist = Dist.CLIENT)
public class ClientSetup {

    public ClientSetup(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        modEventBus.addListener(ClientSetup::onClientSetup);
        modEventBus.addListener(ClientSetup::registerRenderers);
        modEventBus.addListener(ClientSetup::registerLayerDefinitions);
        modEventBus.addListener(ClientSetup::registerScreens);
        modEventBus.addListener(ClientSetup::registerClientExtensions);
        modEventBus.register(OxygenHudRenderer.class);
        modEventBus.register(ScreenEffectsRenderer.class);
        modEventBus.register(DebugHudRenderer.class);
        NeoForge.EVENT_BUS.register(GogglesClientHandler.class);
        NeoForge.EVENT_BUS.addListener(ClientSetup::onMouseButton);
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        AtlantisOrigins.LOGGER.info("Atlantis Origins client setup");

        ModNetwork.oxygenHandler = packet -> ClientNetworkCache.oxygen = packet.oxygen();
        ModNetwork.pressureHandler = packet -> {
            ClientNetworkCache.pressureStage = packet.stage();
            ClientNetworkCache.effectIntensity = packet.intensity();
        };
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(com.samplecat.atlantisorigins.common.entity.ModEntities.ABYSSAL_JELLYFISH.get(), AbyssalJellyfishRenderer::new);
        event.registerEntityRenderer(com.samplecat.atlantisorigins.common.entity.ModEntities.BIOLUMINESCENT_FISH.get(), BioluminescentFishRenderer::new);
        event.registerEntityRenderer(com.samplecat.atlantisorigins.common.entity.ModEntities.DEEP_EEL.get(), DeepEelRenderer::new);
        event.registerEntityRenderer(com.samplecat.atlantisorigins.common.entity.ModEntities.ABYSSAL_CRAB.get(), AbyssalCrabRenderer::new);
        event.registerEntityRenderer(com.samplecat.atlantisorigins.common.entity.ModEntities.GULPER.get(), GulperRenderer::new);
        event.registerEntityRenderer(com.samplecat.atlantisorigins.common.entity.ModEntities.KRILL_SWARM.get(), KrillSwarmRenderer::new);
        event.registerEntityRenderer(com.samplecat.atlantisorigins.common.entity.ModEntities.STINGING_JELLYFISH.get(), StingingJellyfishRenderer::new);
        event.registerEntityRenderer(com.samplecat.atlantisorigins.common.entity.ModEntities.GAZING_JELLYFISH.get(), GazingJellyfishRenderer::new);
        event.registerEntityRenderer(com.samplecat.atlantisorigins.common.entity.ModEntities.DEEP_GUARDIAN.get(), DeepGuardianRenderer::new);
        event.registerEntityRenderer(com.samplecat.atlantisorigins.common.entity.ModEntities.POSEIDONS_BLADE.get(), PoseidonsBladeRenderer::new);
        // Replace the vanilla thrown trident renderer so our tridents use the custom model.
        event.registerEntityRenderer(net.minecraft.world.entity.EntityType.TRIDENT, OrichalcumTridentRenderer::new);
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AbyssalJellyfishModel.LAYER_LOCATION, AbyssalJellyfishModel::createBodyLayer);
        event.registerLayerDefinition(BioluminescentFishModel.LAYER_LOCATION, BioluminescentFishModel::createBodyLayer);
        event.registerLayerDefinition(DeepEelModel.LAYER_LOCATION, DeepEelModel::createBodyLayer);
        event.registerLayerDefinition(DeepGuardianModel.LAYER_LOCATION, DeepGuardianModel::createBodyLayer);
        event.registerLayerDefinition(OrichalcumTridentModel.LAYER_LOCATION, OrichalcumTridentModel::createBodyLayer);
    }

    private static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        OrichalcumTridentBEWLR bewlr = new OrichalcumTridentBEWLR();
        IClientItemExtensions tridentExtensions = new IClientItemExtensions() {
            @Override
            public net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return bewlr;
            }
        };
        event.registerItem(tridentExtensions, ModItems.ORICHALCUM_TRIDENT.get(), ModItems.DEEP_GUARDIAN_TRIDENT.get());

        // Orichalcum armor is rendered via GeckoLib's GeoRenderProvider, registered in OrichalcumArmorItem.

        event.registerFluidType(makeFluidExtensions("rust_poison"), ModFluidTypes.RUST_POISON.get());
    }

    private static IClientFluidTypeExtensions makeFluidExtensions(String name) {
        ResourceLocation still = ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "block/" + name + "_still");
        ResourceLocation flow = ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "block/" + name + "_flow");
        return new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return still;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flow;
            }
        };
    }

    private static void onMouseButton(InputEvent.MouseButton.Pre event) {
        if (event.getButton() != GLFW.GLFW_MOUSE_BUTTON_RIGHT || event.getAction() != GLFW.GLFW_PRESS) {
            return;
        }
        if (!Screen.hasAltDown()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null || mc.player == null) {
            return;
        }
        boolean holdingTrident = mc.player.getMainHandItem().is(ModItems.ORICHALCUM_TRIDENT.get())
                || mc.player.getOffhandItem().is(ModItems.ORICHALCUM_TRIDENT.get());
        if (!holdingTrident) {
            return;
        }
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = mc.player.getItemInHand(hand);
            if (stack.is(ModItems.ORICHALCUM_TRIDENT.get())) {
                event.setCanceled(true);
                PacketDistributor.sendToServer(new ModNetwork.ToggleRiptidePacket());
                return;
            }
        }
    }

    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.CUPRITE_WEAPON_STATION.get(), CupriteWeaponStationScreen::new);
        event.register(ModMenus.ALCHEMICAL_REACTOR.get(), AlchemicalReactorScreen::new);
        event.register(ModMenus.LIQUID_INJECTION_CHAMBER.get(), LiquidInjectionChamberScreen::new);
        event.register(ModMenus.SEPARATION_TOWER.get(), SeparationTowerScreen::new);
        event.register(ModMenus.CATALYTIC_REACTOR.get(), CatalyticReactorScreen::new);
    }
}
