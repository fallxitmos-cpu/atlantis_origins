package com.samplecat.atlantisorigins.common.effect;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, AtlantisOrigins.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> OPPRESSION = MOB_EFFECTS.register(
            "oppression", () -> new AtlantisOriginsEffect(MobEffectCategory.HARMFUL, 0x8B0000));

    public static final DeferredHolder<MobEffect, MobEffect> OVERPRESSURE = MOB_EFFECTS.register(
            "overpressure", () -> new AtlantisOriginsEffect(MobEffectCategory.HARMFUL, 0x4B0082));

    public static final DeferredHolder<MobEffect, MobEffect> HORROR = MOB_EFFECTS.register(
            "horror", () -> new AtlantisOriginsEffect(MobEffectCategory.HARMFUL, 0x000000)
                    .addAttributeModifier(
                            NeoForgeMod.SWIM_SPEED,
                            ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "horror_swim_speed"),
                            0.1,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final DeferredHolder<MobEffect, MobEffect> HYPOTHERMIA = MOB_EFFECTS.register(
            "hypothermia", () -> new AtlantisOriginsEffect(MobEffectCategory.HARMFUL, 0x00CED1));

    public static final DeferredHolder<MobEffect, MobEffect> DECOMPRESSION_SICKNESS = MOB_EFFECTS.register(
            "decompression_sickness", () -> new AtlantisOriginsEffect(MobEffectCategory.HARMFUL, 0x708090)
                    .addAttributeModifier(
                            net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "dcs_slowdown"),
                            -0.3,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final DeferredHolder<MobEffect, MobEffect> ORICHALCUM_SET_BONUS = MOB_EFFECTS.register(
            "orichalcum_set_bonus", () -> new AtlantisOriginsEffect(MobEffectCategory.BENEFICIAL, 0xFFD700));

    public static class AtlantisOriginsEffect extends MobEffect {
        public AtlantisOriginsEffect(MobEffectCategory category, int color) {
            super(category, color);
        }
    }
}
