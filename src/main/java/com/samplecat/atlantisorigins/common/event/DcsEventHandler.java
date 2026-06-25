package com.samplecat.atlantisorigins.common.event;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.attachment.PlayerAttachments;
import com.samplecat.atlantisorigins.common.config.Config;
import com.samplecat.atlantisorigins.common.damage.ModDamageTypes;
import com.samplecat.atlantisorigins.common.effect.ModMobEffects;
import com.samplecat.atlantisorigins.common.util.WaterCheckHelper;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = AtlantisOrigins.MOD_ID)
public class DcsEventHandler {

    private static final int WINDOW_TICKS = 50;
    private static final int CHECK_INTERVAL = 20;
    private static final int MILD_DURATION = 1000; // 50 seconds

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

        if (!Config.DECOMPRESSION_SICKNESS_ENABLED.get()) {
            return;
        }

        boolean inWater = entity.isInWater();
        int stage = PlayerAttachments.getDcsStage(entity);
        boolean pending = PlayerAttachments.isDcsPendingMild(entity);

        // Only track decompression for entities that are currently in water or already suffering DCS.
        if (!inWater && stage == 0 && !pending) {
            return;
        }

        updateDcsState(entity);
        applyDcsDamage(entity);
    }

    private static void updateDcsState(LivingEntity entity) {
        long currentTick = entity.level().getGameTime();
        double currentY = entity.getY();
        long lastTick = PlayerAttachments.getDcsLastYTick(entity);
        double lastY = PlayerAttachments.getDcsLastY(entity);

        if (entity.isInWater() && currentTick - lastTick >= CHECK_INTERVAL) {
            double deltaY = currentY - lastY;
            double seconds = (currentTick - lastTick) / 20.0;
            double speed = seconds > 0 ? deltaY / seconds : 0;

            if (speed >= 12.0 && deltaY > 0) {
                triggerDcs(entity, lastY < 0.0);
            }

            PlayerAttachments.setDcsLastY(entity, currentY);
            PlayerAttachments.setDcsLastYTick(entity, currentTick);
        }

        // Handle pending mild DCS
        if (PlayerAttachments.isDcsPendingMild(entity) && !WaterCheckHelper.isFullySubmerged(entity)) {
            PlayerAttachments.setDcsPendingMild(entity, false);
            PlayerAttachments.setDcsStage(entity, 1);
            PlayerAttachments.setDcsTimer(entity, MILD_DURATION);
        }

        // Decrease DCS timer
        int stage = PlayerAttachments.getDcsStage(entity);
        int timer = PlayerAttachments.getDcsTimer(entity);
        if (stage == 1 && timer > 0) {
            timer--;
            PlayerAttachments.setDcsTimer(entity, timer);
            if (timer <= 0) {
                PlayerAttachments.setDcsStage(entity, 0);
            }
        }

        // Apply slowness effect while DCS is active
        if (stage > 0) {
            entity.addEffect(new MobEffectInstance(
                    ModMobEffects.DECOMPRESSION_SICKNESS, 120, 0, false, false, false));
        }
    }

    private static void triggerDcs(LivingEntity entity, boolean severe) {
        if (severe) {
            PlayerAttachments.setDcsStage(entity, 2);
            PlayerAttachments.setDcsTimer(entity, -1);
            PlayerAttachments.setDcsPendingMild(entity, false);
        } else {
            if (WaterCheckHelper.isFullySubmerged(entity)) {
                PlayerAttachments.setDcsPendingMild(entity, true);
            } else {
                PlayerAttachments.setDcsStage(entity, 1);
                PlayerAttachments.setDcsTimer(entity, MILD_DURATION);
            }
        }
    }

    private static void applyDcsDamage(LivingEntity entity) {
        int stage = PlayerAttachments.getDcsStage(entity);
        if (stage == 0) {
            return;
        }

        // Reset damage window every 50 ticks
        if (entity.tickCount % WINDOW_TICKS == 0) {
            PlayerAttachments.setDcsDamageWindow(entity, 0.0F);
        }

        if (entity.tickCount % 40 == 0) {
            float movementSpeed = (float) entity.getAttributeValue(Attributes.MOVEMENT_SPEED);
            float window = PlayerAttachments.getDcsDamageWindow(entity);

            float newDamage = Math.min(movementSpeed, 5.5F - window);
            if (newDamage > 0) {
                entity.hurt(entity.damageSources().source(ModDamageTypes.DCS_DAMAGE), newDamage);
                PlayerAttachments.setDcsDamageWindow(entity, window + newDamage);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (!Config.DECOMPRESSION_SICKNESS_ENABLED.get()) {
            return;
        }

        int stage = PlayerAttachments.getDcsStage(entity);
        if (stage == 0) {
            return;
        }

        // Skip multiplier for DCS damage itself
        if (event.getSource().is(ModDamageTypes.DCS_DAMAGE)) {
            return;
        }

        float movementSpeed = (float) entity.getAttributeValue(Attributes.MOVEMENT_SPEED);
        float multiplier = 1.0F + (movementSpeed * 0.5F);

        float newAmount = event.getOriginalDamage() * multiplier;
        event.setNewDamage(newAmount);
    }
}
