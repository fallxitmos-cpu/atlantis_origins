package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.advancement.AdvancementHelper;
import com.samplecat.atlantisorigins.common.attachment.PlayerAttachments;
import com.samplecat.atlantisorigins.common.config.Config;
import com.samplecat.atlantisorigins.common.damage.ModDamageTypes;
import com.samplecat.atlantisorigins.common.effect.ModMobEffects;

import com.samplecat.atlantisorigins.common.network.ModNetwork;
import com.samplecat.atlantisorigins.common.util.BiomeHelper;
import com.samplecat.atlantisorigins.common.util.PressureHelper;
import com.samplecat.atlantisorigins.common.util.WaterCheckHelper;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = AtlantisOrigins.MOD_ID)
public class PressureEventHandler {

    private static final int EFFECT_DURATION = 120; // 6 seconds

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        if (entity.level().isClientSide()) {
            return;
        }

        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;
        if (player.isSpectator() || player.isCreative()) {
            return;
        }

        if (!Config.PRESSURE_ENABLED.get()) {
            return;
        }

        int currentStage = PlayerAttachments.getPressureStage(entity);
        float intensity = PlayerAttachments.getEffectIntensity(entity);
        boolean inValidEnvironment = BiomeHelper.isOceanBiome(entity.level(), entity.blockPosition())
                && WaterCheckHelper.isFullySubmerged(entity);

        // Nothing to do if the entity is out of pressure and has no fading intensity.
        if (!inValidEnvironment && currentStage == 0 && intensity <= 0.0F) {
            return;
        }

        int newStage = inValidEnvironment ? calculatePressureStage(entity) : 0;

        // Evolution from Poseidon's flesh grants complete immunity to pressure.
        if (PlayerAttachments.isEvolved(entity)) {
            newStage = 0;
            intensity = 0.0F;
        }

        if (newStage > 0) {
            // Increase intensity when inside pressure zone
            intensity = Math.min(1.0F, intensity + 0.05F);
        } else {
            // Fade out over 5 seconds (100 ticks)
            float fadeStep = 1.0F / Math.max(1, Config.EFFECT_FADE_OUT_TICKS.get());
            intensity = Math.max(0.0F, intensity - fadeStep);
        }

        PlayerAttachments.setPressureStage(entity, newStage);
        PlayerAttachments.setEffectIntensity(entity, intensity);

        // Apply effects based on current stage
        applyPressureEffects(entity, newStage);

        // Apply damage
        applyPressureDamage(entity, newStage);

        // Sync to client and grant advancements only for players
        if (entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.tickCount % 5 == 0 || newStage != currentStage) {
                ModNetwork.sendPressureToClient(serverPlayer, newStage, intensity);
            }

            // Grant horror advancement when first reaching stage 3
            if (newStage == 3 && currentStage != 3) {
                AdvancementHelper.grantSurviveHorror(serverPlayer);
            }
        }
    }

    private static int calculatePressureStage(LivingEntity entity) {
        if (!BiomeHelper.isOceanBiome(entity.level(), entity.blockPosition())) {
            return 0;
        }

        if (!WaterCheckHelper.isFullySubmerged(entity)) {
            return 0;
        }

        if (WaterCheckHelper.isInBubbleColumn(entity)) {
            return 0;
        }

        // Use relative depth below the local water surface so pressure works
        // regardless of world-gen sea level modifications.
        int surfaceY = PressureHelper.getWaterSurfaceY(entity.level(), entity.blockPosition());
        double depth = surfaceY - entity.getEyeY();

        if (depth < 15) {
            return 0;
        } else if (depth < 30) {
            return 1; // oppression
        } else if (depth < 50) {
            return 2; // overpressure
        } else {
            return 3; // superpressure
        }
    }

    private static void applyPressureEffects(LivingEntity entity, int stage) {
        if (stage >= 1) {
            entity.addEffect(new MobEffectInstance(
                    ModMobEffects.OPPRESSION, EFFECT_DURATION, 0, false, false, false));
        }
        if (stage >= 2) {
            entity.addEffect(new MobEffectInstance(
                    ModMobEffects.OVERPRESSURE, EFFECT_DURATION, 0, false, false, false));
        }
        if (stage >= 3) {
            entity.addEffect(new MobEffectInstance(
                    ModMobEffects.HORROR, EFFECT_DURATION, 0, false, false, false));

            // Approximate 1.2x hunger consumption (players only)
            if (entity instanceof Player player) {
                player.causeFoodExhaustion(0.005F);
            }
        }
    }

    private static void applyPressureDamage(LivingEntity entity, int stage) {
        if (stage == 2 && entity.tickCount % 40 == 0) {
            entity.hurt(entity.damageSources().source(ModDamageTypes.TRUE_DAMAGE), 1.0F);
        } else if (stage == 3 && entity.tickCount % 10 == 0) {
            entity.hurt(entity.damageSources().source(ModDamageTypes.TRUE_DAMAGE), 2.5F);
        }
    }
}
