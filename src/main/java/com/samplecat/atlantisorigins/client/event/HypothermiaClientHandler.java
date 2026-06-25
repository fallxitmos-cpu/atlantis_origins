package com.samplecat.atlantisorigins.client.event;

import java.util.Random;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.effect.ModMobEffects;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = AtlantisOrigins.MOD_ID, value = Dist.CLIENT)
public class HypothermiaClientHandler {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null) {
            return;
        }

        MobEffectInstance hypothermia = minecraft.player.getEffect(ModMobEffects.HYPOTHERMIA);
        if (hypothermia == null) {
            return;
        }

        float fade = Mth.clamp(hypothermia.getDuration() / 120.0F, 0.0F, 1.0F);
        float magnitude = 0.4F * fade;
        if (magnitude <= 0.01F) {
            return;
        }

        float time = minecraft.level.getGameTime() + (float) event.getPartialTick();
        float yawShake = Mth.sin(time * 0.8F) * magnitude * 0.6F
                + (RANDOM.nextFloat() - 0.5F) * magnitude;
        float pitchShake = Mth.cos(time * 0.7F) * magnitude * 0.6F
                + (RANDOM.nextFloat() - 0.5F) * magnitude * 0.8F;

        event.setYaw(event.getYaw() + yawShake);
        event.setPitch(event.getPitch() + pitchShake);
    }
}
