package com.samplecat.atlantisorigins.common.attachment;

import net.minecraft.world.entity.LivingEntity;

/**
 * Helper accessors for entity data attachments. Most of these are used by the
 * underwater debuff systems and therefore accept any {@link LivingEntity};
 * oxygen is still player-only in practice but also works with LivingEntity.
 */
public class PlayerAttachments {

    public static float getOxygen(LivingEntity entity) {
        return entity.getData(ModAttachments.PLAYER_OXYGEN);
    }

    public static void setOxygen(LivingEntity entity, float oxygen) {
        entity.setData(ModAttachments.PLAYER_OXYGEN, oxygen);
    }

    public static int getHypothermiaTicks(LivingEntity entity) {
        return entity.getData(ModAttachments.HYPOTHERMIA_TICKS);
    }

    public static void setHypothermiaTicks(LivingEntity entity, int ticks) {
        entity.setData(ModAttachments.HYPOTHERMIA_TICKS, ticks);
    }

    public static int getPressureStage(LivingEntity entity) {
        return entity.getData(ModAttachments.PRESSURE_STAGE);
    }

    public static void setPressureStage(LivingEntity entity, int stage) {
        entity.setData(ModAttachments.PRESSURE_STAGE, stage);
    }

    public static float getEffectIntensity(LivingEntity entity) {
        return entity.getData(ModAttachments.EFFECT_INTENSITY);
    }

    public static void setEffectIntensity(LivingEntity entity, float intensity) {
        entity.setData(ModAttachments.EFFECT_INTENSITY, intensity);
    }

    public static int getDcsStage(LivingEntity entity) {
        return entity.getData(ModAttachments.DCS_STAGE);
    }

    public static void setDcsStage(LivingEntity entity, int stage) {
        entity.setData(ModAttachments.DCS_STAGE, stage);
    }

    public static int getDcsTimer(LivingEntity entity) {
        return entity.getData(ModAttachments.DCS_TIMER);
    }

    public static void setDcsTimer(LivingEntity entity, int timer) {
        entity.setData(ModAttachments.DCS_TIMER, timer);
    }

    public static float getDcsDamageWindow(LivingEntity entity) {
        return entity.getData(ModAttachments.DCS_DAMAGE_WINDOW);
    }

    public static void setDcsDamageWindow(LivingEntity entity, float damage) {
        entity.setData(ModAttachments.DCS_DAMAGE_WINDOW, damage);
    }

    public static boolean isDcsPendingMild(LivingEntity entity) {
        return entity.getData(ModAttachments.DCS_PENDING_MILD);
    }

    public static void setDcsPendingMild(LivingEntity entity, boolean pending) {
        entity.setData(ModAttachments.DCS_PENDING_MILD, pending);
    }

    public static double getDcsLastY(LivingEntity entity) {
        return entity.getData(ModAttachments.DCS_LAST_Y);
    }

    public static void setDcsLastY(LivingEntity entity, double y) {
        entity.setData(ModAttachments.DCS_LAST_Y, y);
    }

    public static long getDcsLastYTick(LivingEntity entity) {
        return entity.getData(ModAttachments.DCS_LAST_Y_TICK);
    }

    public static void setDcsLastYTick(LivingEntity entity, long tick) {
        entity.setData(ModAttachments.DCS_LAST_Y_TICK, tick);
    }

    public static boolean isEvolved(LivingEntity entity) {
        return entity.getData(ModAttachments.EVOLVED);
    }

    public static void setEvolved(LivingEntity entity, boolean evolved) {
        entity.setData(ModAttachments.EVOLVED, evolved);
    }
}
