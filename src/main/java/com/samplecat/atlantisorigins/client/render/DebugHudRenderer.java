package com.samplecat.atlantisorigins.client.render;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.config.ClientConfig;
import com.samplecat.atlantisorigins.common.effect.ModMobEffects;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class DebugHudRenderer {

    private static final ResourceLocation DEBUG_HUD_LAYER = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "debug_hud");

    @SubscribeEvent
    public static void registerLayer(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.CROSSHAIR, DEBUG_HUD_LAYER, new DebugHudLayer());
    }

    public static class DebugHudLayer implements LayeredDraw.Layer {
        @Override
        public void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
            if (!ClientConfig.DEBUG_MODE.get()) {
                return;
            }

            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player == null || minecraft.options.hideGui) {
                return;
            }

            int x = 4;
            int y = 4;
            int color = 0xFFFFFF;
            boolean shadow = true;

            // Current Y value
            graphics.drawString(minecraft.font,
                    Component.literal("Y: " + String.format("%.2f", minecraft.player.getY())),
                    x, y, color, shadow);
            y += minecraft.font.lineHeight + 2;

            // Atlantis effect statuses
            renderEffectStatus(graphics, minecraft, ModMobEffects.OPPRESSION, "Oppression", x, y, color, shadow);
            y += minecraft.font.lineHeight + 1;
            renderEffectStatus(graphics, minecraft, ModMobEffects.OVERPRESSURE, "Overpressure", x, y, color, shadow);
            y += minecraft.font.lineHeight + 1;
            renderEffectStatus(graphics, minecraft, ModMobEffects.HORROR, "Horror", x, y, color, shadow);
            y += minecraft.font.lineHeight + 1;
            renderEffectStatus(graphics, minecraft, ModMobEffects.HYPOTHERMIA, "Hypothermia", x, y, color, shadow);
            y += minecraft.font.lineHeight + 1;
            renderEffectStatus(graphics, minecraft, ModMobEffects.DECOMPRESSION_SICKNESS, "DCS", x, y, color, shadow);
        }

        private static void renderEffectStatus(GuiGraphics graphics, Minecraft minecraft,
                                               net.minecraft.core.Holder<MobEffect> effect,
                                               String name, int x, int y, int color, boolean shadow) {
            MobEffectInstance instance = minecraft.player.getEffect(effect);
            String status;
            if (instance == null) {
                status = "OFF";
            } else {
                int seconds = instance.getDuration() / 20;
                status = String.format("ON (%ds)", seconds);
            }
            graphics.drawString(minecraft.font,
                    Component.literal(name + ": " + status),
                    x, y, color, shadow);
        }
    }
}
