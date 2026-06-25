package com.samplecat.atlantisorigins.client.render;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.ClientNetworkCache;
import com.samplecat.atlantisorigins.client.config.ClientConfig;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class ScreenEffectsRenderer {

    private static final ResourceLocation VIGNETTE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/misc/vignette.png");
    private static final ResourceLocation BLOOD_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "textures/misc/blood_overlay.png");

    @SubscribeEvent
    public static void registerLayers(RegisterGuiLayersEvent event) {
        // Render below the crosshair so pressure/blood overlays never cover vanilla HUD elements.
        event.registerBelow(VanillaGuiLayers.CROSSHAIR, ResourceLocation.fromNamespaceAndPath(
                AtlantisOrigins.MOD_ID, "screen_effects"), new ScreenEffectsLayer());
    }

    public static class ScreenEffectsLayer implements LayeredDraw.Layer {
        @Override
        public void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
            if (!ClientConfig.CLIENT_RENDERING_ENABLED.get()) {
                return;
            }

            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player == null || minecraft.options.hideGui) {
                return;
            }

            int stage = ClientNetworkCache.pressureStage;
            float intensity = Mth.clamp(ClientNetworkCache.effectIntensity, 0.0F, 1.0F);
            if (stage <= 0 || intensity <= 0.01F) {
                return;
            }

            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();

            switch (stage) {
                case 1 -> renderVignette(graphics, screenWidth, screenHeight, intensity);
                case 2 -> {
                    renderVignette(graphics, screenWidth, screenHeight, intensity);
                    renderBloodOverlay(graphics, screenWidth, screenHeight, intensity);
                }
                case 3 -> {
                    renderGrayscale(graphics, screenWidth, screenHeight, intensity);
                    renderSaturation(graphics, screenWidth, screenHeight, intensity);
                    renderBlindnessPulse(graphics, screenWidth, screenHeight, intensity, minecraft.level.getGameTime() + deltaTracker.getGameTimeDeltaPartialTick(false));
                }
            }
        }
    }

    private static void renderVignette(GuiGraphics graphics, int width, int height, float intensity) {
        float alpha = intensity * 0.7F;
        if (alpha <= 0.01F) {
            return;
        }
        // Scale the 256x256 texture to cover the whole screen without tiling.
        graphics.pose().pushPose();
        graphics.pose().scale(width / 256.0F, height / 256.0F, 1.0F);
        graphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        graphics.blit(VIGNETTE_TEXTURE, 0, 0, 0, 0, 256, 256);
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.pose().popPose();
    }

    private static void renderBloodOverlay(GuiGraphics graphics, int width, int height, float intensity) {
        ClientConfig.BloodOverlayMode mode = ClientConfig.BLOOD_OVERLAY_MODE.get();
        if (mode == ClientConfig.BloodOverlayMode.OFF) {
            return;
        }

        float alpha = intensity * 0.5F;
        int color = switch (mode) {
            case RED -> argb(alpha, 0.6F, 0.0F, 0.0F);
            case HARMONIZED -> argb(alpha * 0.7F, 0.5F, 0.1F, 0.0F);
            case BLACK_WHITE -> argb(alpha * 0.6F, 0.2F, 0.2F, 0.2F);
            default -> argb(alpha, 0.6F, 0.0F, 0.0F);
        };

        graphics.fill(0, 0, width, height, color);
    }

    private static void renderGrayscale(GuiGraphics graphics, int width, int height, float intensity) {
        // Approximate grayscale by overlaying a neutral gray layer
        int color = argb(intensity * 0.45F, 0.5F, 0.5F, 0.5F);
        graphics.fill(0, 0, width, height, color);
    }

    private static void renderSaturation(GuiGraphics graphics, int width, int height, float intensity) {
        // Approximate saturation boost with a faint warm overlay
        int color = argb(intensity * 0.15F, 1.0F, 0.7F, 0.3F);
        graphics.fill(0, 0, width, height, color);
    }

    private static void renderBlindnessPulse(GuiGraphics graphics, int width, int height, float intensity, float time) {
        float pulse = (Mth.sin(time * 0.15F) + 1.0F) * 0.5F;
        float alpha = intensity * 0.55F * pulse;
        if (alpha <= 0.01F) {
            return;
        }
        int color = argb(alpha, 0.0F, 0.0F, 0.0F);
        graphics.fill(0, 0, width, height, color);
    }

    private static int argb(float alpha, float red, float green, float blue) {
        int a = Mth.clamp((int) (alpha * 255.0F), 0, 255);
        int r = Mth.clamp((int) (red * 255.0F), 0, 255);
        int g = Mth.clamp((int) (green * 255.0F), 0, 255);
        int b = Mth.clamp((int) (blue * 255.0F), 0, 255);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
