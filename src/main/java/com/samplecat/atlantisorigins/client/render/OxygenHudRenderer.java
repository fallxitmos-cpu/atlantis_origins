package com.samplecat.atlantisorigins.client.render;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.ClientNetworkCache;
import com.samplecat.atlantisorigins.client.config.ClientConfig;
import com.samplecat.atlantisorigins.common.config.Config;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class OxygenHudRenderer {

    private static final ResourceLocation OXYGEN_HUD_LAYER = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "oxygen_hud");

    @SubscribeEvent
    public static void registerLayer(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, OXYGEN_HUD_LAYER, new OxygenHudLayer());
    }

    public static class OxygenHudLayer implements LayeredDraw.Layer {
        @Override
        public void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
            if (!ClientConfig.CLIENT_RENDERING_ENABLED.get()) {
                return;
            }

            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player == null || minecraft.options.hideGui) {
                return;
            }

            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();

            int x = screenWidth / 2 + 91;
            int y = screenHeight - 49;

            float maxOxygen = Config.OXYGEN_TANK_MAX_OXYGEN.get().floatValue();
            float oxygen = Math.min(ClientNetworkCache.oxygen, maxOxygen);
            int percentage = Math.round((oxygen / maxOxygen) * 100.0F);

            graphics.drawString(minecraft.font, percentage + "%", x, y, 0xFFFFFF, true);
        }
    }
}
