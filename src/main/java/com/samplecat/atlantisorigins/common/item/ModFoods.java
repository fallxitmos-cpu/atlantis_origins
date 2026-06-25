package com.samplecat.atlantisorigins.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties RAW_EEL = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.1f)
            .build();

    public static final FoodProperties COOKED_EEL = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.4f)
            .build();

    public static final FoodProperties RAW_CRAB_MEAT = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.2f)
            .build();

    public static final FoodProperties CRAB_MEAT_STEW = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.36f)
            .build();

    public static final FoodProperties RAW_GULPER_FLESH = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.2f)
            .build();

    public static final FoodProperties COOKED_GULPER = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.33f)
            .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 0), 1.0f)
            .build();

    public static final FoodProperties KRILL = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.1f)
            .build();

    public static final FoodProperties KRILL_PASTE = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.3f)
            .build();

    public static final FoodProperties KRILL_PASTE_BREAD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.36f)
            .effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 600, 0), 1.0f)
            .build();

    public static final FoodProperties DEEP_SEA_KELP = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.1f)
            .build();

    public static final FoodProperties KELP_ROLL = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 600, 0), 1.0f)
            .build();

    public static final FoodProperties SALTED_FISH = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.42f)
            .build();

    public static final FoodProperties DEEP_SEA_STEW = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.375f)
            .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 1200, 0), 1.0f)
            .build();

    public static final FoodProperties POSEIDONS_FLESH = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.1f)
            .build();
}
